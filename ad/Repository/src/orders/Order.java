package orders;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import clients.Client;
import products.Product;

public class Order {

	private static final String INVALID_ID_EXCEPTION_MESSAGE = "Order ID must be a positive non-null value";
	private static final String NULL_ORDER_EXCEPTION_MESSAGE = "Order must not be null";
	private static final String NULL_CLIENT_EXCEPTION_MESSAGE = "Client must not be null";
	private static final String NULL_PRODUCT_EXCEPTION_MESSAGE = "Product must not be null";

	private Long orderId;
	private Client client;
	private List<Product> products;

	/**
	 * Constructor for Order class.
	 */
	public Order(
		Long orderId,
		Client client,
		List<Product> products
	) {
		this.orderId = orderId;
		this.client = client;
		this.products = products;
	}

	/**
	 * Constructor for Order class without ID, price and date.
	 * ID, price and date are assigned on save in repository.
	 */
	public Order(
		Client client,
		List<Product> products
	) {
		this(null, client, products);
	}

	/**
	 * Constructor for Order class without ID, price and date, and no products.
	 * Product list is initialized as empty and must be filled later.
	 * ID, price and date are assigned on save in repository.
	 */
	public Order(
		Client client
	) {
		this(null, client, new ArrayList<>());
	}

	public Long getId() throws IllegalArgumentException {
		return this.getOrderId();
	}

	public void setId(
		Long id
	) throws IllegalArgumentException {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException(INVALID_ID_EXCEPTION_MESSAGE);
		}

		this.setOrderId(id);
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(
		Long orderId
	) {
		if (orderId == null || orderId <= 0) {
			throw new IllegalArgumentException(INVALID_ID_EXCEPTION_MESSAGE);
		}
		this.orderId = orderId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(
		Client client
	) {
		if (client == null) {
			throw new IllegalArgumentException(NULL_CLIENT_EXCEPTION_MESSAGE);
		}
		this.client = client;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(
		List<Product> products
	) {
		if (products == null) {
			throw new IllegalArgumentException(NULL_PRODUCT_EXCEPTION_MESSAGE);
		}
		this.products = products;
	}
}
