package repository;

public abstract class Repository<T, ID> implements IRepository<T, ID> {

	public abstract long count();

	public abstract void deleteById(ID id) throws IllegalArgumentException;

	public abstract void deleteAll();

	public abstract boolean existsById(ID id);

	public abstract T findById(ID id) throws IllegalArgumentException;

	public abstract Iterable<T> findAll();

	public abstract <S extends T> S save(S entity);

}
