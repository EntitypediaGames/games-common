package org.entitypedia.games.common.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.filter.OAuthConsumerContextFilter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * OAuthCallbackConsumerContextFilter always adds oauth_callback.
 *
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class OAuthCallbackConsumerContextFilter extends OAuthConsumerContextFilter {

    private static final Logger log = LoggerFactory.getLogger(OAuthCallbackConsumerContextFilter.class);

    @Override
    protected String getUserAuthorizationRedirectURL(ProtectedResourceDetails details, OAuthConsumerToken requestToken, String callbackURL) {
        try {
            String result = super.getUserAuthorizationRedirectURL(details, requestToken, callbackURL);
            if (!result.contains("oauth_callback=")) {
                log.debug("Adding callback to user authorization redirect URL: {}", callbackURL);
                result = result + "&oauth_callback=" + URLEncoder.encode(callbackURL, "UTF-8");
            }
            if (!result.contains("uid=")) {
                log.debug("Trying to add uid to user authorization redirect URL");
                String uid = AuthTools.getUidFromRequest();
                if (null != uid) {
                    log.debug("Adding uid to user authorization redirect URL: ", uid);
                    result = result + "&uid=" + URLEncoder.encode(uid, "UTF-8");
                }
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
}
