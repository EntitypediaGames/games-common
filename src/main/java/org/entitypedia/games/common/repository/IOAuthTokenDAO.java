package org.entitypedia.games.common.repository;

import org.entitypedia.games.common.repository.ITypedDAO;
import org.entitypedia.games.common.model.OAuthToken;

/**
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public interface IOAuthTokenDAO extends ITypedDAO<OAuthToken, Long> {

    OAuthToken getTokenByUIDAndResource(String uid, String resourceId);
}
