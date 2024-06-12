package openlibraryhub.interfaces;

public interface CRUDRepository<T> {
    T getById(int id);
    T save(T entity);
    T update(T entity);
    void delete(T entity);
}
