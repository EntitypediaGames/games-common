package org.entitypedia.games.common.repository.hibernateimpl;

import org.entitypedia.games.common.model.Page;
import org.entitypedia.games.common.repository.ITypedDAO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @param <T>  - data type that the DAO serves
 * @param <PK> - type of the primary key
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public abstract class AbstractTypedDAO<T, PK extends Serializable> extends GenericDAO implements ITypedDAO<T, PK> {

    private Class<T> targetType;

    public AbstractTypedDAO(Class<T> targetType) {
        this.targetType = targetType;
    }

    protected Class<T> getTargetType() {
        return targetType;
    }

    @Override
    public T read(PK id) {
        return read(targetType, id);
    }

    @Override
    public long count() {
        return count(targetType);
    }

    @Override
    public List<T> find(Collection<Criterion> criteria, Page page, Order... sortCriteria) {
        return find(targetType, criteria, page, sortCriteria);
    }
}
