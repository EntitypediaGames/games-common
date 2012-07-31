package org.entitypedia.games.common.repository.hibernateimpl;

import org.entitypedia.games.common.model.Page;
import org.entitypedia.games.common.repository.IGenericDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Generic DAO which handles basic methods.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public abstract class GenericDAO extends HibernateDaoSupport implements IGenericDAO {

    @Autowired
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public <T> T create(T object) {
        Serializable id = getHibernateTemplate().save(object);
        if (null == id) {
            throw new IllegalStateException("Couldn't save object " + object
                    + ": Hibernate returned id = null");
        }
        return object;
    }

    @Override
    public <T> T update(T object) {
        return getHibernateTemplate().merge(object);
    }

    @Override
    public <T> T read(Class<T> targetType, Serializable id) {
        return getHibernateTemplate().get(targetType, id);
    }

    @Override
    public <T> void delete(T object) {
        getHibernateTemplate().delete(object);
    }

    @Override
    public <T> long count(Class<T> targetType) {
        return (Long) createCriteria(targetType).setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> find(Class<T> targetType, Collection<Criterion> criteria, Page page, Order... sortCriteria) {
        return addPageFilter(addOrder(addCriterion(createCriteria(targetType), criteria.toArray(new Criterion[criteria.size()])), sortCriteria), page).setCacheable(true).list();
    }

    private <T> Criteria createCriteria(Class<T> targetType) {
        return getSessionFactory().getCurrentSession().createCriteria(targetType).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    }

    private Criteria addCriterion(Criteria query, Criterion... criteria) {
        for (Criterion criterion : criteria) {
            query = query.add(criterion);
        }
        return query;
    }

    private Criteria addOrder(Criteria query, Order... sortCriteria) {
        for (Order order : sortCriteria) {
            query = query.addOrder(order);
        }
        return query;
    }

    private Criteria addPageFilter(Criteria query, Page page) {
        if (page != null) {
            query.setFirstResult(page.getPageNo() * page.getPageSize());
            if (page.getPageSize() > 0) {
                query.setMaxResults(page.getPageSize());
            }
        }
        return query;
    }
}