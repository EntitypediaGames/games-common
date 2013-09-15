package org.entitypedia.games.common.repository;

import org.entitypedia.games.common.model.WordGameUser;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public interface IWordGameUserDAO<T extends WordGameUser> extends ITypedDAO<T, Long> {

    T getUserByUID(String uid);
}