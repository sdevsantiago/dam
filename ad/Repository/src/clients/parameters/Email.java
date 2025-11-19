package clients.parameters;

public class Email {

	private static final String EMPTY_EMAIL_EXCEPTION_MESSAGE = "Email must not be null or empty";
	private static final String INVALID_EMAIL_EXCEPTION_MESSAGE = "Email format is invalid";

	private final String email;

	public Email(String email) throws IllegalArgumentException {
		if (email == null || email.isEmpty() || email.isBlank()) {
			throw new IllegalArgumentException(EMPTY_EMAIL_EXCEPTION_MESSAGE);
		} else if (!this.isValid(email)) {
			throw new IllegalArgumentException(INVALID_EMAIL_EXCEPTION_MESSAGE);
		}
		this.email = email;
	}

	private boolean isValid(String email) {
		final String WORD_DOT_OR_HYPHEN_REGEX = "[\\w.-]+";
		final String EMAIL_RULES_REGEX = "^" + WORD_DOT_OR_HYPHEN_REGEX + "@" + WORD_DOT_OR_HYPHEN_REGEX + "\\.\\w{2,}$";

		return email.matches(EMAIL_RULES_REGEX);
	}

	public String getEmail() {
		return this.email;
	}

	@Override
	public String toString() {
		return this.getEmail();
	}
}
