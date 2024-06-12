package openlibraryhub.interfaces;

import java.util.List;

public interface CRUDRepository<T> {
    T save(T entity);
    T update(T entity);
    void delete(T entity);
    T getById(int id);
    List<T> getAll();
}
