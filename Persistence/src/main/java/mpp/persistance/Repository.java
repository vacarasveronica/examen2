package mpp.persistance;



import mpp.model.Entitate;

/**
 * CRUD operations repository interface
 * @param <ID> - type E must have an attribute of type ID
 * @param <E> - type of entities saved in repository
 */
public interface Repository<ID, E extends Entitate<ID>> {
    /**
     *
     * @param id -the id of the entity to be returned
     * id must not be null
     * @return an {@code Optional} encapsulating the entity with the given id
     * @throws IllegalArgumentException
     * if id is null.
     */
    E findOne(ID id);
    /**
     *
     * @return all entities
     */
    Iterable<E> findAll() ;
    /**
     *
     * @param entity
     * entity must be not null
     * @return an {@code Optional} - null if the entity was saved,
     * - the entity (id already exists)
     * if the entity is not valid
     * @throws IllegalArgumentException
     * if the given entity is null. *
     */
    E save(E entity);
    /**
     * removes the entity with the specified id
     * @param id
     * id must be not null
     * @return an {@code Optional}
     * - null if there is no entity with the given id,
     * - the removed entity, otherwise
     * @throws IllegalArgumentException
     * if the given id is null.
     */
    E delete(ID id);
    /**
     *
     * @param entity
     * entity must not be null
     * @return an {@code Optional}
     * - null if the entity was updated
     * - otherwise (e.g. id does not exist) returns the entity.
     * @throws IllegalArgumentException
     * if the given entity is null.
     * if the entity is not valid.
     */
    E update(E entity);
}
