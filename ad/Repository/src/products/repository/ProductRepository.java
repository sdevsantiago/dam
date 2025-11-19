package products.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import products.Product;
import products.parameters.ProductAvailability;
import repository.IRepositoryExtend;

public class ProductRepository implements IRepositoryExtend<Product, Long> {

	private static final String EMPTY_DATA_SOURCE_FILE_EXCEPTION_MESSAGE = "Product Repository file path must not be null or empty";
	private static final String INVALID_ID_EXCEPTION_MESSAGE = "Barcode must not be null, negative or zero";
	private static final String NULL_PRODUCT_EXCEPTION_MESSAGE = "Product must not be null";

	private static final short DATA_SOURCE_FILE_BARCODE_INDEX = 0;
	private static final short DATA_SOURCE_FILE_NAME_INDEX = 1;
	private static final short DATA_SOURCE_FILE_PRICE_INDEX = 2;
	private static final short DATA_SOURCE_FILE_OLD_PRICE_INDEX = 3;
	private static final short DATA_SOURCE_FILE_CATEGORY_INDEX = 4;
	private static final short DATA_SOURCE_FILE_STOCK_INDEX = 5;
	private static final short DATA_SOURCE_FILE_AVAILABILITY_INDEX = 6;

	/**
	 * Singleton instance of ProductRepository
	 */
	private static ProductRepository instance = null;

	/**
	 * Path to the data source file
	 */
	private final File dataSourceFile;

	/**
	 * In-memory cache for products
	 */
	private HashMap<Long, Product> productsCache;

	/**
	 * Private constructor for singleton pattern
	 *
	 * @param filepath the data source file path
	 *
	 * @throws IllegalArgumentException if the filepath is null or empty, if the file specified is a directory,
	 *                                  or if the file cannot be read from or written to
	 */
	private ProductRepository(
		String filepath
	) throws IllegalArgumentException {
		if (filepath == null || filepath.isEmpty() || filepath.isBlank()) {
			throw new IllegalArgumentException(EMPTY_DATA_SOURCE_FILE_EXCEPTION_MESSAGE);
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
		this.productsCache = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				Product product = this.fromDataSource(line);
				this.productsCache.put(product.getBarcode(), product);
			}
		} catch (FileNotFoundException e) { // thrown by 'new BufferedReader(new FileReader(...))'
			e.printStackTrace();
		} catch (IOException e) { // thrown by 'br.readLine()'
			e.printStackTrace();
		}
	}

	/**
	 * Returns the singleton instance of ClientRepository. If none exists, creates one.
	 *
	 * @param filepath the data source file path
	 *
	 * @return the singleton instance, future calls will ignore the filepath parameter and return the same instance
	 */
	public static ProductRepository getInstance(
		String filepath
	) {
		if (instance == null) {
			// create the instance by calling the private constructor
			instance = new ProductRepository(filepath);
		}
		return instance;
	}

	public long count() {
		return (long)(this.productsCache.size());
	}

	/**
	 * Sets the product with the given barcode as UNAVAILABLE.
	 *
	 * @param barcode product identifier, must not be null
	 *
	 * @throws IllegalArgumentException if barcode is null, negative or zero
	 */
	public void deleteById(
		Long barcode
	) throws IllegalArgumentException {
		Product product;

		if (barcode == null || barcode <= 0) {
			throw new IllegalArgumentException(INVALID_ID_EXCEPTION_MESSAGE);
		}

		if (this.existsById(barcode)) {
			product = this.findById(barcode);
			// check if product is not already inactive
			if (product.isAvailable()) {
				// change status to inactive and write changes if needed
				product.setAvailability(ProductAvailability.UNAVAILABLE);
				// write changes to data source and in-memory cache
				this.save(product);
			}
		}
	}

	/**
	 * Deletes all clients from the in-memory cache and data source.
	 */
	public void deleteAll() {
		this.productsCache.clear();
		this.dataSourceFile.delete();
		try {
			this.dataSourceFile.createNewFile();
		} catch (IOException e) { // thrown by 'dataSourceFile.createNewFile()'
			e.printStackTrace();
		}
	}

	/**
	 * Checks whether a product with the given id exists.
	 * A product whose status is UNAVAILABLE is considered to exist.
	 */
	public boolean existsById(
		Long barcode
	) throws IllegalArgumentException {
		if (barcode == null) {
			throw new IllegalArgumentException(INVALID_ID_EXCEPTION_MESSAGE);
		}

		// check whether the product exists in the in-memory cache
		return this.productsCache.containsKey(barcode);
	}

	public Product findById(
		Long barcode
	) {
		return this.productsCache.get(barcode);
	}

	public <S extends Product> S save(
		S entity
	) {
		boolean replaceProductData;

		if (entity == null) {
			throw new IllegalArgumentException(NULL_PRODUCT_EXCEPTION_MESSAGE);
		}

		replaceProductData = this.existsById(entity.getBarcode());

		// update in-memory cache
		this.productsCache.put(entity.getBarcode(), entity);

		// write changes to data source
		if (replaceProductData) {
			// rewrite the entire data source file
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.dataSourceFile))) {
				for (Product product : this.productsCache.values()) {
					bw.write(this.toDataSource(product));
					bw.newLine();
				}
			} catch (IOException e) { // thrown by 'new BufferedWriter(new FileWriter(...))' or 'bw.write(...)' or 'bw.newLine()'
				e.printStackTrace();
			}
		} else {
			// append to the data source file
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.dataSourceFile, true))) {
				bw.write(this.toDataSource(entity));
				bw.newLine();
			} catch (IOException e) { // thrown by 'new BufferedWriter(new FileWriter(...))' or 'bw.write(...)' or 'bw.newLine()'
				e.printStackTrace();
			}
		}

		return entity;
	}

	public Optional<Product> findByIdOptional(
		Long barcode
	) {
		return Optional.ofNullable(this.findById(barcode));
	}

	public Iterable<Product> findAll() {
		return this.findAllToList();
	}

	public List<Product> findAllToList() {
		return new ArrayList<>(this.productsCache.values());
	}

	public Product fromDataSource(
		String origin
	) {
		return this.fromCSV(origin);
	}

	private Product fromCSV(
		String csvLine
	) {
		String[] values = csvLine.split(",");
		Long barcode = Long.parseLong(values[DATA_SOURCE_FILE_BARCODE_INDEX]);
		String name = values[DATA_SOURCE_FILE_NAME_INDEX];
		Double price = Double.parseDouble(values[DATA_SOURCE_FILE_PRICE_INDEX]);
		Double oldPrice = values[DATA_SOURCE_FILE_OLD_PRICE_INDEX].isEmpty() ? null : Double.parseDouble(values[DATA_SOURCE_FILE_OLD_PRICE_INDEX]);
		String category = values[DATA_SOURCE_FILE_CATEGORY_INDEX];
		Integer stock = Integer.parseInt(values[DATA_SOURCE_FILE_STOCK_INDEX]);
		ProductAvailability availability = ProductAvailability.fromValue(Short.parseShort(values[DATA_SOURCE_FILE_AVAILABILITY_INDEX]));

		return new Product(barcode, name, price, oldPrice, category, stock, availability);
	}

	public String toDataSource(
		Product product
	) {
		return this.toCSV(product); // products are stored in CSV format in the data source
	}

	private String toCSV(
		Product product
	) {
		return String.join(",",
			Long.toString(product.getBarcode()),
			product.getName(),
			Double.toString(product.getCurrentPrice()),
			product.getOldPrice() != null ? Double.toString(product.getOldPrice()) : "",
			product.getCategory(),
			Integer.toString(product.getStock()),
			Short.toString(product.getAvailability().getShortValue())
		);
	}
}
