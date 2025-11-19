package clients.parameters;

public enum AccountStatus {
	ACTIVE(1),
	INACTIVE(0);

	private final int value;

	AccountStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static AccountStatus fromValue(int value) {
		for (AccountStatus status : AccountStatus.values()) {
			if (status.value == value) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid status value: " + value);
	}
}
