package repository;

public interface IRepository<T, ID> {
	/**
	 * Counts the number of entities available.
	 *
	 * @return the number of entities
	 */
	long count();

	/**
	 * Deletes the entity with the given id.
	 * If no entity exists for the given id, no operation is performed.
	 *
	 * @param id entity identifier, must not be null
	 *
	 * @throws IllegalArgumentException if id is null
	 */
	void deleteById(ID id) throws IllegalArgumentException;

	/**
	 * Deletes all entities managed by the repository.
	 */
	void deleteAll();

	/**
	 * Checks whether an entity with the given id exists.
	 *
	 * @param id entity identifier, must not be null
	 *
	 * @return true if an entity with the given id exists, false otherwise
	 *
	 * @throws IllegalArgumentException if id is null
	 */
	boolean existsById(ID id) throws IllegalArgumentException;

	/**
	 * Finds an entity by its id.
	 *
	 * @param id entity identifier, must not be null
	 *
	 * @return the entity with the given id, or null if no such entity exists
	 *
	 * @throws IllegalArgumentException if id is null
	 */
	T findById(ID id) throws IllegalArgumentException;

	/**
	 * Retrieves all entities managed by the repository.
	 *
	 * @return all entities
	 */
	Iterable<T> findAll();

	/**
	 * Saves the given entity.
	 * If an entity with the same id already exists, it is replaced.
	 *
	 * Note: S is a subtype of T to allow for more specific entity types,
	 * meaning that S can be any class that inherits T.
	 *
	 * @param entity entity to be saved, must not be null
	 *
	 * @return the saved entity
	 *
	 * @throws IllegalArgumentException if entity is null
	 */
	<S extends T> S save(S entity) throws IllegalArgumentException;

}
