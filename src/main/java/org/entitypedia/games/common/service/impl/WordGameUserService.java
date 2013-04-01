package org.entitypedia.games.common.service.impl;

import org.entitypedia.games.common.model.WordGameUser;
import org.entitypedia.games.common.repository.IWordGameUserDAO;
import org.entitypedia.games.common.repository.util.UIDGenerator;
import org.entitypedia.games.common.service.IWordGameUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public abstract class WordGameUserService<T extends WordGameUser> implements IWordGameUserService<T> {

    private static final Logger log = LoggerFactory.getLogger(WordGameUserService.class);

    private final Class<T> targetType;

    @Autowired
    private IWordGameUserDAO<T> wordGameUserDAO;

    public WordGameUserService(Class<T> targetType) {
        this.targetType = targetType;
    }

    protected Class<T> getTargetType() {
        return targetType;
    }

    //TODO AA filter by IP to avoid flooding
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public T importUser(String uid) {
        T result = wordGameUserDAO.getUserByUID(uid);
        if (null == result && null != uid) {
            // create new one
            Constructor<T> constructor;
            try {
                constructor = targetType.getConstructor(String.class);
                result = constructor.newInstance(uid);
                result.setPassword(UIDGenerator.getUID(10));
                result = wordGameUserDAO.update(result);
                log.debug("Imported new user from framework: {}, {}", result.getId(), result);
            } catch (NoSuchMethodException e) {
                log.error(e.getMessage(), e);
            } catch (InvocationTargetException e) {
                log.error(e.getMessage(), e);
            } catch (InstantiationException e) {
                log.error(e.getMessage(), e);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public void login() {
        //nop
    }
}