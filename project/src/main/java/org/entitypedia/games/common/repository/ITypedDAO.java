package org.entitypedia.games.common.repository;

import org.entitypedia.games.common.model.Page;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public interface ITypedDAO<T, PK extends Serializable> extends IGenericDAO {

    /**
     * Loads the object of the given type and with the given identifier.
     *
     * @param id The identifier of the object
     * @return The object with the given identifier if it exists, null otherwise
     */
    public T read(PK id);

    /**
     * Returns the number of objects of type T.
     *
     * @return Returns the number of objects of type T
     */
    public long count();

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
    public List<T> find(Collection<Criterion> criteria, Page page, Order... sortCriteria);
}