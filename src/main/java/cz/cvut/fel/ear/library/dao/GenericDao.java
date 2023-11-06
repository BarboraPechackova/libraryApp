package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.exceptions.PersistenceException;

import java.util.Collection;
import java.util.List;

public interface GenericDao<T> {

    /**
     * Finds entity instance with the specified identifier.
     *
     * @param id Identifier
     * @return Entity instance or {@code null} if no such instance exists
     */
    T find(Integer id);

    /**
     * Finds all instances of the specified class.
     *
     * @return List of instances, possibly empty
     */
    List<T> findAll() throws PersistenceException;

    /**
     * Persists the specified entity.
     *
     * @param entity Entity to persist
     */
    void persist(T entity) throws PersistenceException;

    /**
     * Persists the specified instances.
     *
     * @param entities Entities to persist
     */
    void persist(Collection<T> entities) throws PersistenceException;

    /**
     * Updates the specified entity.
     *
     * @param entity Entity to update
     * @return The updated instance
     */
    T update(T entity) throws PersistenceException;

    /**
     * Removes the specified entity.
     *
     * @param entity Entity to remove
     */
    void remove(T entity) throws PersistenceException;

    /**
     * Checks whether an entity with the specified id exists (and has the type managed by this DAO).
     *
     * @param id Entity identifier
     * @return {@literal true} if entity exists, {@literal false} otherwise
     */
    boolean exists(Integer id);
}
