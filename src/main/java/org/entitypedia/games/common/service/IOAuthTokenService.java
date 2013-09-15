package org.entitypedia.games.common.service;

import org.springframework.security.oauth.consumer.OAuthConsumerToken;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public interface IOAuthTokenService {

    OAuthConsumerToken getTokenByUIDAndResource(String uid, String resourceId);

    void storeToken(String uid, OAuthConsumerToken token);

    void removeToken(String uid, OAuthConsumerToken token);

    void removeTokenByUIDAndResourceId(String uid, String resourceId);
}
