/**
 * Repository class for managing Client entities.
 */

package clients.repository;

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

import clients.Client;
import clients.parameters.AccountStatus;
import repository.IRepositoryExtend;

public class ClientRepository implements IRepositoryExtend<Client, String> {

	private static final String INVALID_ID_EXCEPTION_MESSAGE = "ID must not be null or empty";
	private static final String NULL_CLIENT_EXCEPTION_MESSAGE = "Client must not be null";

	private static final short DATA_SOURCE_FILE_USERNAME_INDEX = 0;
	private static final short DATA_SOURCE_FILE_EMAIL_INDEX = 1;
	private static final short DATA_SOURCE_FILE_PASSWORD_INDEX = 2;
	private static final short DATA_SOURCE_FILE_IS_ACTIVE_INDEX = 3;

	/**
	 * Singleton instance of ClientRepository
	 */
	private static ClientRepository instance = null;

	/**
	 * Path to the data source file
	 */
	private final File dataSourceFile;

	/**
	 * In-memory cache for clients
	 */
	private HashMap<String, Client> clientsCache;

	/**
	 * Private constructor for singleton pattern
	 *
	 * @param filepath the data source file path
	 */
	private ClientRepository(
		String filepath
	) throws IllegalArgumentException {
		if (filepath == null || filepath.isEmpty() || filepath.isBlank()) {
			throw new IllegalArgumentException("Client Repository file path must not be null or empty");
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
		this.clientsCache = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				Client client = this.fromDataSource(line);
				this.clientsCache.put(client.getUsername(), client);
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
	public static ClientRepository getInstance(
		String filepath
	) {
		if (instance == null) {
			// create the instance by calling the private constructor
			instance = new ClientRepository(filepath);
		}
		return instance;
	}

	public long count() {
		return (long)(this.clientsCache.size());
	}

	public long countActiveClients() {
		long count = 0;

		for (Client client : this.clientsCache.values()) {
			if (client.getStatus() == AccountStatus.ACTIVE) {
				count++;
			}
		}
		return count;
	}

	public long countInactiveClients() {
		long count = 0;

		for (Client client : this.clientsCache.values()) {
			if (client.getStatus() == AccountStatus.INACTIVE) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Deactivates the Client with the given username.
	 * Clients are not deleted, instead their status is set to INACTIVE.
	 *
	 * @param username client identifier, must not be null
	 *
	 * @throws IllegalArgumentException if username is null
	 */
	public void deleteById(
		String username
	) throws IllegalArgumentException {
		Client client;

		if (username == null || username.isEmpty() || username.isBlank()) {
			throw new IllegalArgumentException(INVALID_ID_EXCEPTION_MESSAGE);

		}

		if (this.existsById(username)) {
			client = this.findById(username);
			// check if client is not already inactive
			if (client.getStatus() != AccountStatus.INACTIVE) {
				// change status to inactive and write changes if needed
				client.setStatus(AccountStatus.INACTIVE);
				// write changes to data source and in-memory cache
				this.save(client);
			}
		}
	}

	/**
	 * Deletes all clients from the in-memory cache and data source.
	 */
	public void deleteAll() {
		this.clientsCache.clear();
		this.dataSourceFile.delete();
		try {
			this.dataSourceFile.createNewFile();
		} catch (IOException e) { // thrown by 'dataSourceFile.createNewFile()'
			e.printStackTrace();
		}
	}

	/**
	 * Checks whether a client with the given id exists. A client whose status is INACTIVE is considered to exist.
	 */
	public boolean existsById(
		String id
	) throws IllegalArgumentException {
		if (id == null) {
			throw new IllegalArgumentException(INVALID_ID_EXCEPTION_MESSAGE);
		}

		// check whether the client exists in the in-memory cache
		return this.clientsCache.containsKey(id);
	}

	public Client findById(
		String id
	) {
		return this.clientsCache.get(id);
	}

	public <S extends Client> S save(
		S entity
	) {
		boolean replaceClientData;

		if (entity == null) {
			throw new IllegalArgumentException(NULL_CLIENT_EXCEPTION_MESSAGE);
		}

		replaceClientData = this.existsById(entity.getUsername());

		// update in-memory cache
		this.clientsCache.put(entity.getUsername(), entity);

		// write changes to data source
		if (replaceClientData) {
			// rewrite the entire data source file
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.dataSourceFile))) {
				for (Client client : this.clientsCache.values()) {
					bw.write(this.toDataSource(client));
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

	public Optional<Client> findByIdOptional(
		String id
	) {
		return Optional.ofNullable(this.findById(id));
	}

	public Iterable<Client> findAll() {
		return this.findAllToList();
	}

	public List<Client> findAllToList() {
		return new ArrayList<>(this.clientsCache.values());
	}

	public Client fromDataSource(
		String origin
	) {
		return this.fromCSV(origin);
	}

	private Client fromCSV(
		String csvLine
	) {
		String[] values = csvLine.split(",");
		String username = values[DATA_SOURCE_FILE_USERNAME_INDEX];
		String email = values[DATA_SOURCE_FILE_EMAIL_INDEX];
		int password = Integer.parseInt(values[DATA_SOURCE_FILE_PASSWORD_INDEX]);
		AccountStatus status = AccountStatus.fromValue(Integer.parseInt(values[DATA_SOURCE_FILE_IS_ACTIVE_INDEX]));

		return new Client(username, email, password, status);
	}

	public String toDataSource(
		Client client
	) {
		return this.toCSV(client); // clients are stored in CSV format in the data source
	}

	private String toCSV(
		Client client
	) {
		return String.join(",",
			client.getUsername(),
			client.getEmail(),
			Integer.toString(client.getPassword()),
			Integer.toString(client.getStatus().getValue())
		);
	}

}
