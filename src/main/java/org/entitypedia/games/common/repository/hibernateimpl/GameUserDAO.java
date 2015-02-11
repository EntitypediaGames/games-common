package org.entitypedia.games.common.repository.hibernateimpl;

import org.entitypedia.games.common.model.GameUser;
import org.entitypedia.games.common.repository.IGameUserDAO;

/**
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public abstract class GameUserDAO<T extends GameUser> extends AbstractTypedDAO<T, Long> implements IGameUserDAO<T> {

    public GameUserDAO(Class<T> targetType) {
        super(targetType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getUserByUID(String uid) {
        return (T) getSessionFactory().getCurrentSession().byNaturalId(getTargetType()).using("uid", uid).load();
    }
}