package repository;

import java.util.List;
import java.util.Optional;

public abstract class RepositoryExtend<T, ID> extends Repository<T, ID> implements IRepositoryExtend<T, ID> {

	public abstract Optional<T> findByIdOptional(ID id);

	public abstract List<T> findAllToList();

}
