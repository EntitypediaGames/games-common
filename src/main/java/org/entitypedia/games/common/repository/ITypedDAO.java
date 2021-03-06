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
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public interface ITypedDAO<T, PK extends Serializable> extends IGenericDAO {

    /**
     * Loads the object of the given type and with the given identifier.
     *
     * @param id The identifier of the object
     * @return The object with the given identifier if it exists, null otherwise
     */
    T read(PK id);

    /**
     * Returns the number of objects of type T.
     *
     * @return Returns the number of objects of type T
     */
    long count();

    /**
     * Returns the number of objects satisfying the criteria.
     *
     * @param criteria criteria
     * @return the number of objects satisfying the criteria
     */
    long count(Collection<Criterion> criteria);

    /**
     * Returns the number of objects satisfying the criteria.
     *
     * @param criteria criteria
     * @param aliases  table aliases
     * @return the number of objects satisfying the criteria
     */
    long count(Collection<Criterion> criteria, Map<String, String> aliases);

    /**
     * Find all the objects of type T that answer the given collection of {@link org.hibernate.criterion.Criterion}.
     *
     * @param criteria     A collection of {@link org.hibernate.criterion.Criterion} that defines the restrictions on the list
     *                     of objects to be retrieved
     * @param page         If not null defines a specific page to be retrieved
     * @param sortCriteria Optional list of ordering criteria (expressed as a list of property
     *                     names, always ascending)
     * @return The list of objects of type T that answer the given collection of {@link org.hibernate.criterion.Criterion}.
     */
    List<T> find(Collection<Criterion> criteria, Page page, Order... sortCriteria);

    /**
     * Find all the objects that answer the given collection of {@link org.hibernate.criterion.Criterion}.
     *
     * @param criteria     A collection of {@link org.hibernate.criterion.Criterion} that defines the restrictions on the list
     *                     of objects to be retrieved
     * @param page         If not null defines a specific page to be retrieved
     * @param sortCriteria Optional list of ordering criteria (expressed as a list of property
     *                     names, always ascending)
     * @param aliases      list of aliases used in the query
     * @return The list of objects that answer the given collection of {@link org.hibernate.criterion.Criterion}.
     */
    List<T> find(Collection<Criterion> criteria, Page page, Map<String, String> aliases, Order... sortCriteria);

    /**
     * Find a <code>page</code> of objects that satisfy given <code>filter</code>,
     * returning objects in a specified <code>order</code>.
     *
     * @param filter A set of criteria that defines the restrictions on the list
     *               of objects to be retrieved. See {@link org.entitypedia.games.common.repository.hibernateimpl.filter.FilterCriteriaParser} for syntax.
     * @param page   If not null defines a specific page to be retrieved
     * @param order  Optional list of ordering criteria
     * @return The resulting page of objects.
     */
    ResultsPage<T> find(Page page, String filter, String order);
}