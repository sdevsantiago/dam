package repository;
import java.util.List;
import java.util.Optional;

public interface IRepositoryExtend<T, ID> extends IRepository<T, ID> {

	/**
	 * Finds an entity by its id.
	 *
	 * @param id entity identifier, must not be null
	 *
	 * @return an Optional containing the entity with the given id,
	 *         or {@link Optional#empty()} if no such entity exists
	 *
	 * @throws IllegalArgumentException if id is null
	 */
	Optional<T> findByIdOptional(ID id);

	/**
	 * Retrieves all entities managed by the repository as a List.
	 *
	 * @return all entities as a List
	 */
	List<T> findAllToList();
}
