package org.entitypedia.games.common.service.impl;

import org.entitypedia.games.common.model.GameUser;
import org.entitypedia.games.common.repository.IGameUserDAO;
import org.entitypedia.games.common.repository.util.UIDGenerator;
import org.entitypedia.games.common.service.IGameUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public abstract class GameUserService<T extends GameUser> implements IGameUserService<T> {

    private static final Logger log = LoggerFactory.getLogger(GameUserService.class);

    private final Class<T> targetType;

    @Autowired
    private IGameUserDAO<T> gameUserDAO;

    public GameUserService(Class<T> targetType) {
        this.targetType = targetType;
    }

    protected Class<T> getTargetType() {
        return targetType;
    }

    //TODO AA filter by IP to avoid flooding
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public T importUser(String uid) {
        T result = gameUserDAO.getUserByUID(uid);
        if (null == result && null != uid) {
            // create new one
            Constructor<T> constructor;
            try {
                constructor = targetType.getConstructor(String.class);
                result = constructor.newInstance(uid);
                result.setCreationTime(new Date());
                result.setPassword(UIDGenerator.getUID(10));
                result = gameUserDAO.update(result);
                log.debug("Imported new user from framework: {}, {}", result.getId(), result);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                log.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public T findUserByUID(String uid) {
        return gameUserDAO.getUserByUID(uid);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public void login() {
        //nop
    }
}