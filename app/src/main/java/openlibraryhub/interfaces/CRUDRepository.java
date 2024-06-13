package openlibraryhub.interfaces;

import java.util.List;

public interface CRUDRepository<T> {
    /**
     * Saves the given entity to the database.
     * 
     * @param entity the entity to be saved
     * @return the saved entity
     */
    T save(T entity);

    /**
     * Updates the given entity in the database.
     * 
     * @param entity the entity to be updated
     * @return the updated entity
     */
    T update(T entity);

    /**
     * Deletes the given entity from the database.
     * 
     * @param entity the entity to be deleted
     */
    void delete(T entity);

    /** 
     * Retrieves an entity by its id from the database.
     * 
     * @param id the id of the entity to be retrieved
     * @return the entity with the specified id, or null if not found
     */
    T getById(int id);

    /**
     * Retrieves all entities from the database.
     * 
     * @return a list of all entities
     */
    List<T> getAll();
}
