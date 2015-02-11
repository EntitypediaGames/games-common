package org.entitypedia.games.common.oauth;

import org.entitypedia.games.common.model.GameUser;
import org.entitypedia.games.common.service.IOAuthTokenService;
import org.entitypedia.games.common.service.IGameUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;

/**
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class DBConsumerTokenServices extends InMemorySelfCleaningConsumerTokenServices {
    // keep in mind tokens depend on currently authenticated principal... SecurityContext.getAuthentication...
    // and we often don't have it here, because /login is not protected
    // therefore we pull it out of request

    // some code gratuitously lifted from InMemorySelfCleaningProviderTokenServices.java

    // TODO AA cleanup of access tokens in database - on request from Game Framework (e.g. when user revokes permission or deleted)
    // TODO AA cleanup of stale access tokens? properly renew access token if we use mostly RESTTemplate to consume...

    @Autowired
    private IOAuthTokenService tokenService;

    @Autowired
    private IGameUserService userService;

    @Override
    public OAuthConsumerToken getToken(String resourceId) throws AuthenticationException {
        OAuthConsumerToken result = super.getToken(resourceId);

        // give access tokens only to authenticated users
        GameUser user = AuthTools.findCurrentUser();
        if (null == result && null != user) {
            result = tokenService.getTokenByUIDAndResource(user.getUid(), resourceId);
        }
        return result;
    }

    @Override
    public void storeToken(String resourceId, OAuthConsumerToken token) {
        if (!token.isAccessToken()) {
            super.storeToken(resourceId, token);
        } else {
            // on saving OAuthSecurityContextHolder.getContext() -> details -> request -> uid+verifier
            // the tokens are saved from /login where there is no authentication,
            // so we have to get uid from the request
            String uid = AuthTools.getUidFromRequest();
            if (null != uid) {
                // TODO need to do it here to satisfy constraint. but it's not very nice to do it here...
                userService.importUser(uid);
                tokenService.storeToken(uid, token);
            }
        }
    }

    @Override
    public void removeToken(String resourceId) {
        super.removeToken(resourceId);
        // on saving OAuthSecurityContextHolder.getContext() -> details -> request -> uid+verifier
        // request tokens are removed from /login where there is no authentication
        // TODO so we have to get uid from the request
        String uid = AuthTools.getUidFromRequest();
        if (null != uid) {
            tokenService.removeTokenByUIDAndResourceId(uid, resourceId);
        }
    }
}
