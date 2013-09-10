package org.entitypedia.games.common.repository.hibernateimpl;

import org.entitypedia.games.common.model.Page;
import org.entitypedia.games.common.model.ResultsPage;
import org.entitypedia.games.common.repository.ITypedDAO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @param <T>  - data type that the DAO serves
 * @param <PK> - type of the primary key
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public abstract class AbstractTypedDAO<T, PK extends Serializable> extends GenericDAO implements ITypedDAO<T, PK> {

    private final Class<T> targetType;

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
    public long count(Collection<Criterion> criteria) {
        return count(targetType, criteria);
    }

    @Override
    public long count(Collection<Criterion> criteria, Map<String, String> aliases) {
        return count(targetType, criteria, aliases);
    }

    @Override
    public List<T> find(Collection<Criterion> criteria, Page page, Map<String, String> aliases, Order... sortCriteria) {
        return find(targetType, criteria, page, aliases, sortCriteria);
    }

    @Override
    public List<T> find(Collection<Criterion> criteria, Page page, Order... sortCriteria) {
        return find(targetType, criteria, page, sortCriteria);
    }

    @Override
    public ResultsPage<T> find(Page page, String filter, String order) {
        return find(targetType, page, filter, order);
    }
}