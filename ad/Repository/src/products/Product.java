package products;

import products.parameters.ProductAvailability;

public class Product {
	private Long barcode;
	private String name;
	private Double currentPrice;
	private Double oldPrice;
	private String category;
	private Integer stock;
	private ProductAvailability availability;

	/**
	 * Constructor for Product class.
	 */
	public Product(
		Long barcode,
		String name,
		Double currentPrice,
		Double oldPrice,
		String category,
		Integer stock,
		ProductAvailability availability
	) {
		this.barcode = barcode;
		this.name = name;
		this.currentPrice = currentPrice;
		this.oldPrice = oldPrice;
		this.category = category;
		this.stock = stock;
		this.availability = availability;
	}

	/**
	 * Constructor for Product class with no old price.
	 */
	public Product(
		Long barcode,
		String name,
		Double currentPrice,
		String category,
		Integer stock
	) {
		this(barcode, name, currentPrice, null, category, stock, ProductAvailability.AVAILABLE);
	}

	/**
	 * Constructor for Product class with no stock.
	 */
	public Product(
		Long barcode,
		String name,
		Double currentPrice,
		String category
	) {
		this(barcode, name, currentPrice, null, category, 0, ProductAvailability.AVAILABLE);
	}

	public Long getBarcode() {
		return barcode;
	}

	public void setBarcode(
		Long barcode
	) {
		this.barcode = barcode;
	}

	public String getName() {
		return name;
	}

	public void setName(
		String name
	) {
		this.name = name;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(
		Double currentPrice
	) {
		this.currentPrice = currentPrice;
	}

	public Double getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(
		Double oldPrice
	) {
		this.oldPrice = oldPrice;
	}

	public String getCategory() {
		return category;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(
		Integer stock
	) {
		this.stock = stock;
	}

	public ProductAvailability getAvailability() {
		return availability;
	}

	public void setAvailability(
		ProductAvailability availability
	) {
		this.availability = availability;
	}

	public boolean isAvailable() {
		return this.availability.getValue();
	}
}
