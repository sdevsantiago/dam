package orders.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import clients.Client;
import clients.repository.ClientRepository;
import orders.Order;
import products.Product;
import products.repository.ProductRepository;
import repository.IRepositoryExtend;

public class OrderRepository implements IRepositoryExtend<Order, Long> {

	private static final String NULL_ORDER_EXCEPTION_MESSAGE = "Order must not be null";
	private static final String INVALID_ID_EXCEPTION_MESSAGE = "Order ID must not be null";

	/**
	 * Singleton instance of OrderRepository
	 */
	private static OrderRepository instance = null;

	/**
	 * Path to the data source file
	 */
	private final File dataSourceFile;

	/**
	 * In-memory cache for orders
	 */
	private HashMap<Long, Order> ordersCache;

	/**
	 * Private constructor for singleton pattern
	 *
	 * @param filepath the data source file path
	 */
	private OrderRepository(
		String filepath
	) throws IllegalArgumentException {
		if (filepath == null || filepath.isEmpty() || filepath.isBlank()) {
			throw new IllegalArgumentException("Order Repository file path must not be null or empty");
		}

		File file = new File(filepath);
		// check if file exists
		if (!file.exists()) {
			try {
				// attempt to create the file
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace(); // thrown by 'file.createNewFile()'
			}
		// check if the file is a regular file and has permissions
		} else if (!file.isFile() || !file.canRead() || !file.canWrite()) {
			throw new IllegalArgumentException("Invalid file path: " + filepath);
		}

		// assign the file to the dataSourceFile attribute
		this.dataSourceFile = file;

		// initialize the in-memory cache and populate it from the data source
		this.ordersCache = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				Order order = this.fromDataSource(line);
				this.ordersCache.put(order.getId(), order);
			}
		} catch (FileNotFoundException e) { // thrown by 'new BufferedReader(new FileReader(...))'
			e.printStackTrace();
		} catch (IOException e) { // thrown by 'br.readLine()'
			e.printStackTrace();
		}
	}

	/**
	 * Returns the singleton instance of OrderRepository. If none exists, creates one.
	 *
	 * @param filepath the data source file path
	 *
	 * @return the singleton instance, future calls will ignore the filepath parameter and return the same instance
	 */
	public static OrderRepository getInstance(
		String filepath
	) {
		if (instance == null) {
			// create the instance by calling the private constructor
			instance = new OrderRepository(filepath);
		}
		return instance;
	}

	public long count() {
		return (long)(this.ordersCache.size());
	}

	public void add(
		Order order
	) throws IllegalArgumentException {
		if (order == null) {
			throw new IllegalArgumentException(NULL_ORDER_EXCEPTION_MESSAGE);
		}

		if (order.getId() == null) {
			// read cache and assign the next available id
			List<Long> ids = new ArrayList<>(this.ordersCache.keySet());
			order.setId(Collections.max(ids) + 1);
		} else if (this.existsById(order.getId())) {
			throw new IllegalStateException("Order with id " + order.getId() + " already exists");
		}

		// save the new order
		this.save(order);
	}

	public void deleteById(
		Long id
	) throws IllegalArgumentException {
		if (id == null) {
			throw new IllegalArgumentException(INVALID_ID_EXCEPTION_MESSAGE);
		}

		if (!this.existsById(id)) {
			return ;
		}

		// remove from in-memory cache
		this.ordersCache.remove(id);

		// rewrite data source file without the deleted order
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.dataSourceFile, false))) {
			for (Order order : this.ordersCache.values()) {
				bw.write(this.toDataSource(order));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteAll() {
		// clear in-memory cache
		this.ordersCache.clear();
		this.dataSourceFile.delete();
		try {
			this.dataSourceFile.createNewFile();
		} catch (IOException e) { // thrown by 'dataSourceFile.createNewFile()'
			e.printStackTrace();
		}
	}

	public boolean existsById(
		Long id
	) throws IllegalArgumentException {
		if (id == null) {
			throw new IllegalArgumentException(INVALID_ID_EXCEPTION_MESSAGE);
		}

		return this.ordersCache.containsKey(id);
	}

	public Order findById(
		Long id
	) {
		return this.ordersCache.get(id);
	}

	public <S extends Order> S save(
		S entity
	) {
		if (entity == null) {
			throw new IllegalArgumentException(NULL_ORDER_EXCEPTION_MESSAGE);
		}

		// assign transaction ID
		if (entity.getId() == null) {
			// read cache and assign the next available id
			List<Long> ids = new ArrayList<>(this.ordersCache.keySet());
			if (ids.isEmpty()) {
				entity.setId(1L);
			} else {
				entity.setId(Collections.max(ids) + 1);
			}
		}

		// update stock for each product in the order
		for (Product product : entity.getProducts()) {
			Product storedProduct = ProductRepository
				.getInstance("data/products.csv")
				.findById(product.getBarcode());
			if (storedProduct != null) {
				storedProduct.decreaseStock(product.getStock());
				ProductRepository
					.getInstance("data/products.csv")
					.save(storedProduct);
			}
		}

		// update in-memory cache
		this.ordersCache.put(entity.getId(), entity);

		// append changes to data source
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.dataSourceFile, true))) {
			for (Order order : this.ordersCache.values()) {
				bw.write(this.toDataSource(order));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return entity;
	}

	public Optional<Order> findByIdOptional(
		Long id
	) throws IllegalArgumentException {
		if (id == null) {
			throw new IllegalArgumentException(INVALID_ID_EXCEPTION_MESSAGE);
		}

		return Optional.ofNullable(this.findById(id));
	}

	public Iterable<Order> findAll() {
		return this.findAllToList();
	}

	public List<Order> findAllToList() {
		return new ArrayList<>(this.ordersCache.values());
	}

	public Order fromDataSource(
		String origin
	) {
		return this.fromCSV(origin);
	}

	private Order fromCSV(
		String csvLine
	) {
		String[] values = csvLine.split(",");

		Long orderId = Long.parseLong(values[0]);

		Client client = ClientRepository.getInstance("data/clients.csv").findById(values[1]);

		// retrieve products in format <productId1>,<units1>,<productId2>,<units2>,...
		List<Product> products = new ArrayList<>();
		for (int i = 2; i < values.length; i += 2) {
			Product product = ProductRepository
				.getInstance("data/products.csv")
				.findById(Long.parseLong(values[i]));
			// if product exists, set its units from CSV and add to products list
			if (product != null) {
				product.setStock(Integer.parseInt(values[i + 1]));
				products.add(product);
			}
		}

		return new Order(orderId, client, products);
	}

	public String toDataSource(
		Order order
	) {
		return this.toCSV(order); // orders are stored in CSV format in the data source
	}

	private String toCSV(
		Order order
	) {
		String orderId = Long.toString(order.getId());
		String clientId = order.getClient().getUsername();
		String productsData = "";
		for (Product product : order.getProducts()) {
			productsData += product.getBarcode() + "," + product.getStock();
			// check if not the last product to avoid trailing comma
			if (product != order.getProducts().get(order.getProducts().size() - 1)) {
				productsData += ",";
			}
		}

		return String.join(",",
			orderId,
			clientId,
			productsData
		);
	}

}
