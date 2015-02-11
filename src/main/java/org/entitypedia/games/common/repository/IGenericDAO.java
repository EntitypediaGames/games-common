package org.entitypedia.games.common.repository;

import org.entitypedia.games.common.model.Page;
import org.entitypedia.games.common.model.ResultsPage;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Interface for generic DAO implementation.
 *
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public interface IGenericDAO {

    /**
     * Persists the given object in the DB.
     *
     * @param object The object to be saved in the DB
     * @param <T>    type
     * @return The newly created object
     */
    <T> T create(T object);

    /**
     * Updates the given object.
     *
     * @param object The object to be updated
     * @param <T>    type
     * @return The updated object
     */
    <T> T update(T object);

    /**
     * Loads the object of the given type and with the given identifier.
     *
     * @param targetType The object Type
     * @param id         The identifier of the object
     * @param <T>        type
     * @return The object with the given identifier if it exists, null otherwise
     */
    <T> T read(Class<T> targetType, Serializable id);

    /**
     * Returns the number of objects of type T.
     *
     * @param targetType The object Type
     * @param <T>        type
     * @return Returns the number of objects of type T
     */
    <T> long count(Class<T> targetType);

    /**
     * Returns the number of objects of type T satisfying the criteria.
     *
     * @param targetType The object Type
     * @param criteria   criteria
     * @param <T>        type
     * @return Returns the number of objects of type T
     */
    <T> long count(Class<T> targetType, Collection<Criterion> criteria);

    /**
     * Returns the number of objects of type T satisfying the criteria.
     *
     * @param targetType The object Type
     * @param criteria   criteria
     * @param aliases    table aliases
     * @param <T>        type
     * @return Returns the number of objects of type T
     */
    <T> long count(Class<T> targetType, Collection<Criterion> criteria, Map<String, String> aliases);

    /**
     * Deletes the given persistent entity.
     *
     * @param obj The object to be deleted
     * @param <T> type
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
     * @param <T>          type
     * @return The list of objects of type T that answer the given collection of {@link org.hibernate.criterion.Criterion}.
     */
    <T> List<T> find(Class<T> targetType, Collection<Criterion> criteria, Page page, Order... sortCriteria);

    /**
     * Find all the objects of type T that answer the given collection of {@link org.hibernate.criterion.Criterion}.
     *
     * @param targetType   The object Type
     * @param criteria     A collection of {@link org.hibernate.criterion.Criterion} that defines the restrictions on the list
     *                     of objects to be retrieved
     * @param page         If not null defines a specific page to be retrieved
     * @param sortCriteria Optional list of ordering criteria (expressed as a list of property
     *                     names, always ascending)
     * @param aliases      list of aliases used in the query
     * @param <T>          type
     * @return The list of objects of type T that answer the given collection of {@link org.hibernate.criterion.Criterion}.
     */
    <T> List<T> find(Class<T> targetType, Collection<Criterion> criteria, Page page, Map<String, String> aliases, Order... sortCriteria);

    /**
     * Find a <code>page</code> of objects of type T that satisfy given <code>filter</code>,
     * returning objects in a specified <code>order</code>.
     *
     * @param targetType The object Type
     * @param filter     A set of criteria that defines the restrictions on the list
     *                   of objects to be retrieved. See {@link org.entitypedia.games.common.repository.hibernateimpl.filter.FilterCriteriaParser} for syntax.
     * @param page       If not null defines a specific page to be retrieved
     * @param order      Optional list of ordering criteria
     * @param <T>        type
     * @return The resulting page of objects of type T.
     */
    <T> ResultsPage<T> find(Class<T> targetType, Page page, String filter, String order);
}