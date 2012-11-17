package org.entitypedia.games.common.service;

import org.entitypedia.games.common.model.WordGameUser;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public interface IWordGameUserService<T extends WordGameUser> {

    /**
     * Imports user from the framework or returns existing one with updated password.
     *
     * @param uid user uid
     * @return read or newly created WordGameUser with updated password
     */
    T importUser(String uid);

    /**
     * Login (just to check credentials).
     */
    void login();
}