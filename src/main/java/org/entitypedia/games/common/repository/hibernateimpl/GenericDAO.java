package org.entitypedia.games.common.repository.hibernateimpl;

import org.entitypedia.games.common.model.Page;
import org.entitypedia.games.common.repository.IGenericDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Generic DAO which handles basic methods.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public abstract class GenericDAO implements IGenericDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public <T> T create(T object) {
        Serializable id = getSessionFactory().getCurrentSession().save(object);
        if (null == id) {
            throw new IllegalStateException("Couldn't save object " + object
                    + ": Hibernate returned id = null");
        }
        return object;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T update(T object) {
        return (T) getSessionFactory().getCurrentSession().merge(object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T read(Class<T> targetType, Serializable id) {
        return (T) getSessionFactory().getCurrentSession().get(targetType, id);
    }

    @Override
    public <T> void delete(T object) {
        getSessionFactory().getCurrentSession().delete(object);
    }

    @Override
    public <T> long count(Class<T> targetType) {
        return (Long) createCriteria(targetType).setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public <T> long count(Class<T> targetType, Collection<Criterion> criteria) {
        return (Long) addCriterion(createCriteria(targetType), criteria).setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> find(Class<T> targetType, Collection<Criterion> criteria, Page page, Order... sortCriteria) {
        return addPageFilter(addOrder(addCriterion(createCriteria(targetType), criteria), sortCriteria), page).setCacheable(true).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> find(Class<T> targetType, Collection<Criterion> criteria, Page page, Map<String, String> aliases, Order... sortCriteria) {
        return addPageFilter(addOrder(addCriterion(addAliases(createCriteria(targetType), aliases), criteria), sortCriteria), page).setCacheable(true).list();
    }

    protected Criteria addAliases(Criteria criteria, Map<String, String> aliases) {
        for (Map.Entry<String, String> alias : aliases.entrySet()) {
            criteria.createAlias(alias.getKey(), alias.getValue());
        }
        if (0 < aliases.size()) {
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        }
        return criteria;
    }

    protected <T> Criteria createCriteria(Class<T> targetType) {
        return getSessionFactory().getCurrentSession().createCriteria(targetType).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    }

    protected Criteria addCriterion(Criteria query, Criterion... criteria) {
        for (Criterion criterion : criteria) {
            query = query.add(criterion);
        }
        return query;
    }

    protected Criteria addCriterion(Criteria query, Collection<Criterion> criteria) {
        for (Criterion criterion : criteria) {
            query = query.add(criterion);
        }
        return query;
    }

    protected Criteria addOrder(Criteria query, Order... sortCriteria) {
        for (Order order : sortCriteria) {
            query = query.addOrder(order);
        }
        return query;
    }

    protected Criteria addOrder(Criteria query, Collection<Order> sortCriteria) {
        for (Order order : sortCriteria) {
            query = query.addOrder(order);
        }
        return query;
    }

    protected Criteria addPageFilter(Criteria query, Page page) {
        if (page != null) {
            query.setFirstResult(page.getPageNo() * page.getPageSize());
            if (page.getPageSize() > 0) {
                query.setMaxResults(page.getPageSize());
            }
        }
        return query;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}