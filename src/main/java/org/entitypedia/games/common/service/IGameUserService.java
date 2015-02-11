package org.entitypedia.games.common.service;

import org.entitypedia.games.common.model.GameUser;

/**
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public interface IGameUserService<T extends GameUser> {

    /**
     * Imports user from the framework or returns existing one with updated password.
     *
     * @param uid user uid
     * @return read or newly created WordGameUser with updated password
     */
    T importUser(String uid);

    /**
     * Searches user by uid.
     * @param uid uid of the user to use in the search
     * @return user by its uid
     */
    T findUserByUID(String uid);

    /**
     * Login (just to check credentials).
     */
    void login();
}