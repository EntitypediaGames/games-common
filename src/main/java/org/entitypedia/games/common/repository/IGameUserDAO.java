package org.entitypedia.games.common.repository;

import org.entitypedia.games.common.model.GameUser;

/**
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public interface IGameUserDAO<T extends GameUser> extends ITypedDAO<T, Long> {

    T getUserByUID(String uid);
}