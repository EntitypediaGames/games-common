package org.entitypedia.games.common.service.impl;

import org.entitypedia.games.common.repository.IOAuthTokenDAO;
import org.entitypedia.games.common.service.IOAuthTokenService;
import org.entitypedia.games.common.model.OAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
@Service
public class OAuthTokenService implements IOAuthTokenService {

    @Autowired
    private IOAuthTokenDAO tokenDAO;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public OAuthConsumerToken getTokenByUIDAndResource(String uid, String resourceId) {
        OAuthConsumerToken result = null;
        OAuthToken token = tokenDAO.getTokenByUIDAndResource(uid, resourceId);
        if (null != token) {
            result = new OAuthConsumerToken();
            result.setAccessToken(true);
            result.setResourceId(token.getResourceId());
            result.setSecret(token.getSecret());
            result.setValue(token.getValue());
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void storeToken(String uid, OAuthConsumerToken token) {
        OAuthToken t = tokenDAO.getTokenByUIDAndResource(uid, token.getResourceId());
        if (null == t) {
            t = new OAuthToken();
            t.setUid(uid);
            t.setResourceId(token.getResourceId());
        }
        t.setIssueTime(System.currentTimeMillis());
        t.setSecret(token.getSecret());
        t.setValue(token.getValue());
        tokenDAO.update(t);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void removeToken(String uid, OAuthConsumerToken token) {
        OAuthToken t = tokenDAO.getTokenByUIDAndResource(uid, token.getResourceId());
        if (null != t) {
            tokenDAO.delete(t);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void removeTokenByUIDAndResourceId(String uid, String resourceId) {
        OAuthToken token = tokenDAO.getTokenByUIDAndResource(uid, resourceId);
        if (null != token) {
            tokenDAO.delete(token);
        }
    }
}