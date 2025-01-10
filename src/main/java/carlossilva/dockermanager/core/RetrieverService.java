package carlossilva.dockermanager.core;

import java.util.List;
import java.util.Optional;

public interface RetrieverService<T, ID> {
    T create(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    Optional<T> update(T entity, ID id);

    Optional<T> delete(ID id);
}
