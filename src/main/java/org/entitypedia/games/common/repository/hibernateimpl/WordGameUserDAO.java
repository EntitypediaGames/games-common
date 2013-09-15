package org.entitypedia.games.common.repository.hibernateimpl;

import org.entitypedia.games.common.model.WordGameUser;
import org.entitypedia.games.common.repository.IWordGameUserDAO;
import org.hibernate.criterion.Restrictions;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public abstract class WordGameUserDAO<T extends WordGameUser> extends AbstractTypedDAO<T, Long> implements IWordGameUserDAO<T> {

    public WordGameUserDAO(Class<T> targetType) {
        super(targetType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getUserByUID(String uid) {
        return (T) getSessionFactory().getCurrentSession().
                createCriteria(getTargetType()).add(Restrictions.naturalId().set("uid", uid)).setCacheable(true).uniqueResult();
    }
}