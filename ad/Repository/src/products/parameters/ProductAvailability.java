package products.parameters;

import clients.parameters.ClientStatus;

public enum ProductAvailability {
	AVAILABLE((short) 1),
	UNAVAILABLE((short) 0);

	private final short value;

	ProductAvailability(short value) {
		this.value = value;
	}

	/**
	 * Gets the boolean representation of the availability.
	 *
	 * @return true if AVAILABLE, false if UNAVAILABLE
	 */
	public boolean getValue() {
		return value == AVAILABLE.value;
	}

	/**
	 * Gets the short representation of the availability.
	 *
	 * @return 1 if AVAILABLE, 0 if UNAVAILABLE
	 */
	public short getShortValue() {
		return value;
	}

	/**
	 * Converts a short value to its corresponding ProductAvailability enum.
	 *
	 * @param value the short value to convert (1 for AVAILABLE, 0 for UNAVAILABLE)
	 *
	 * @return the corresponding ProductAvailability enum
	 *
	 * @throws IllegalArgumentException if the value does not correspond to any enum
	 */
	public static ProductAvailability fromValue(short value) {
		for (ProductAvailability availability : ProductAvailability.values()) {
			if (availability.value == value) {
				return availability;
			}
		}
		throw new IllegalArgumentException("Invalid availability value: " + value);
	}
}
