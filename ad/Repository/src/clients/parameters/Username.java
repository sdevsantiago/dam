package clients.parameters;

public class Username {

	private static final short MIN_LENGTH = 3;
	private static final short MAX_LENGTH = 20;
	private static final String EMPTY_USERNAME_EXCEPTION_MESSAGE = "Username must not be null or empty";
	private static final String INVALID_USERNAME_EXCEPTION_MESSAGE
		= "Username must start with a letter, be between " + MIN_LENGTH + " and " + MAX_LENGTH
		+ " characters long, and can only contain letters, digits, underscores, and hyphens";

	private final String username;

	public Username(String username) throws IllegalArgumentException {
		if (username == null || username.isEmpty() || username.isBlank()) {
			throw new IllegalArgumentException(EMPTY_USERNAME_EXCEPTION_MESSAGE);
		} else if (!this.isValid(username)) {
			throw new IllegalArgumentException(INVALID_USERNAME_EXCEPTION_MESSAGE);
		}
		this.username = username;
	}

	private boolean isValid(String username) {
		final String STARTS_WITH_LETTER_REGEX = "^[a-zA-Z]";
		final String WORD_DOT_OR_HYPHEN_REGEX = "[\\w.-]+";
		final String LENGTH_REGEX = "{" + MIN_LENGTH + "," + MAX_LENGTH + "}$";
		final String USERNAME_RULES_REGEX = STARTS_WITH_LETTER_REGEX + WORD_DOT_OR_HYPHEN_REGEX + LENGTH_REGEX;

		return username.matches(USERNAME_RULES_REGEX);
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public String toString() {
		return this.getUsername();
	}

}
