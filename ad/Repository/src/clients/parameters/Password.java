package clients.parameters;

public class Password {

	private static final short MIN_LENGTH = 3;
	private static final String EMPTY_PASSWORD_EXCEPTION_MESSAGE = "Password must not be null or empty";
	private static final String INVALID_PASSWORD_EXCEPTION_MESSAGE
		= "Password must be at least " + MIN_LENGTH + " characters long, and must contain at least one letter, one digit, and one special character";

	private final int passwordHash;

	public Password(int passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Password(String password, boolean validatePassword) throws IllegalArgumentException {
		if (password == null || password.isEmpty() || password.isBlank()) {
			throw new IllegalArgumentException(EMPTY_PASSWORD_EXCEPTION_MESSAGE);
		} else if (validatePassword && !isValid(password)) {
			throw new IllegalArgumentException(INVALID_PASSWORD_EXCEPTION_MESSAGE);
		}
		this.passwordHash = password.hashCode();
	}

	public Password(String password) throws IllegalArgumentException {
		this(password, true);
	}

	private boolean isValid(String password) {
		boolean lengthValid = password.length() >= MIN_LENGTH;
		boolean hasLetter = password.matches(".*[a-zA-Z].*");
		boolean hasDigit = password.matches(".*\\d.*");
		boolean hasSpecialCharacter = password.matches(".*[!@#$%^&*(),.?\":{}|<>_-].*");

		return lengthValid && hasLetter && hasDigit && hasSpecialCharacter;
	}

	/**
	 * Returns a hash code representation of the password. Passwords are not stored nor returned in plain text for security reasons.
	 *
	 * @return the hash code of the password
	 */
	public int getPassword() {
		return this.passwordHash;
	}

	@Override
	public String toString() {
		return Integer.toString(this.getPassword());
	}

}
