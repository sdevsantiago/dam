package clients.parameters;

public enum ClientStatus {
	ACTIVE(1),
	INACTIVE(0);

	private final int value;

	ClientStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static ClientStatus fromValue(int value) {
		for (ClientStatus status : ClientStatus.values()) {
			if (status.value == value) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid status value: " + value);
	}
}
