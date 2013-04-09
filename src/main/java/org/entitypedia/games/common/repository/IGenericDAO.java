package org.entitypedia.games.common.repository;

import org.entitypedia.games.common.model.Page;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Interface for generic DAO implementation.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public interface IGenericDAO {

    /**
     * Persists the given object in the DB.
     *
     * @param object The object to be saved in the DB
     * @return The newly created object
     */
    <T> T create(T object);

    /**
     * Updates the given object.
     *
     * @param object The object to be updated
     * @return The updated object
     */
    <T> T update(T object);

    /**
     * Loads the object of the given type and with the given identifier.
     *
     * @param targetType The object Type
     * @param id         The identifier of the object
     * @return The object with the given identifier if it exists, null otherwise
     */
    <T> T read(Class<T> targetType, Serializable id);

    /**
     * Returns the number of objects of type T.
     *
     * @param targetType The object Type
     * @return Returns the number of objects of type T
     */
    <T> long count(Class<T> targetType);

    /**
     * Returns the number of objects of type T satisfying the criteria.
     *
     * @param targetType The object Type
     * @return Returns the number of objects of type T
     */
    <T> long count(Class<T> targetType, Collection<Criterion> criteria);

    /**
     * Deletes the given persistent entity.
     *
     * @param obj The object to be deleted
     */
    <T> void delete(T obj);

    /**
     * Find all the objects of type T that answer the given collection of {@link org.hibernate.criterion.Criterion}.
     *
     * @param targetType   The object Type
     * @param criteria     A collection of {@link org.hibernate.criterion.Criterion} that defines the restrictions on the list
     *                     of objects to be retrieved
     * @param page         If not null defines a specific page to be retrieved
     * @param sortCriteria Optional list of ordering criteria (expressed as a list of property
     *                     names, always ascending)
     * @return The list of objects of type T that answer the given collection of {@link org.hibernate.criterion.Criterion}.
     */
    <T> List<T> find(Class<T> targetType, Collection<Criterion> criteria, Page page, Order... sortCriteria);
}