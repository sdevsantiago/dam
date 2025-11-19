package clients;

import clients.parameters.*;

public class Client {
	private Username username;
	private Email email;
	private Password password;
	private AccountStatus status;

	/**
	 * Constructor for Client
	 */
	public Client(
		String name,
		String email,
		String password,
		boolean validatePassword,
		AccountStatus status
	) {
		this.setUsername(name);
		this.setEmail(email);
		this.setPassword(password, validatePassword);
		this.setStatus(status);
	}

	/**
	 * Constructor for Client with hashed password
	 */
	public Client(
		String name,
		String email,
		int passwordHash,
		AccountStatus status
	) {
		this.setUsername(name);
		this.setEmail(email);
		this.setPassword(passwordHash);
		this.setStatus(status);
	}

	public Client(
		String name,
		String email,
		String password,
		AccountStatus status
	) {
		this.setUsername(name);
		this.setEmail(email);
		this.setPassword(password);
		this.setStatus(status);
	}

	public String getUsername() {
		return username.getUsername();
	}

	public void setUsername(String name) {
		this.username = new Username(name);
	}

	public String getEmail() {
		return email.getEmail();
	}

	public void setEmail(String email) {
		this.email = new Email(email);
	}

	public int getPassword() {
		return password.getPassword();
	}

	public void setPassword(String password, boolean validatePassword) {
		this.password = new Password(password, validatePassword);
	}

	public void setPassword(String password) {
		this.password = new Password(password);
	}

	public void setPassword(int passwordHash) {
		this.password = new Password(passwordHash);
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

}
