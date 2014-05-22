package org.entitypedia.games.common.oauth;

import org.entitypedia.games.common.model.WordGameUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.token.OAuthConsumerTokenServices;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Self-cleaning token storage.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class InMemorySelfCleaningConsumerTokenServices implements OAuthConsumerTokenServices {
    // keep in mind tokens depend on currently authenticated principal... SecurityContext.getAuthentication...
    // and we often don't have it here, because /login is not protected
    // therefore we pull it out of request

    private static final Logger log = LoggerFactory.getLogger(InMemorySelfCleaningConsumerTokenServices.class);

    // uid \t resourceId -> token
    private final ConcurrentMap<String, OAuthConsumerTokenHolder> tokens = new ConcurrentHashMap<>();

    private static class OAuthConsumerTokenHolder {
        private long timestamp = System.currentTimeMillis();
        private OAuthConsumerToken token;

        private OAuthConsumerTokenHolder(OAuthConsumerToken token) {
            this.token = token;
        }

        public OAuthConsumerToken getToken() {
            return token;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    private ScheduledExecutorService scheduler;
    private Integer cleanupIntervalSeconds;
    private int requestTokenValiditySeconds = 60 * 10; //default 10 minutes;


    @Override
    public OAuthConsumerToken getToken(String resourceId) throws AuthenticationException {
        OAuthConsumerToken result = null;

        WordGameUser user = AuthTools.findCurrentUser();
        if (null != user) {
            OAuthConsumerTokenHolder t = tokens.get(user.getUid() + "\t" + resourceId);
            if (null != t) {
                result = t.getToken();
            }
        } else {
            String uid = AuthTools.getUidFromRequest();
            if (null != uid) {
                // in the request there might be a new_uid which does not correspond to
                // the uid we started with - if the user logged in under a different uid (new_uid) in the framework
                // therefore the token will not be found, or found access_token instead of request one.
                // however, there is a verifier and oauth_token, which is the original request token, which
                // corresponds to the old uid, so we might look by the oauth_token.
                // or simply make another round for a new request token and authorize it with again

                OAuthConsumerTokenHolder t = tokens.get(uid + "\t" + resourceId);
                if (null != t && !t.getToken().isAccessToken()) {
                    // do not give out access tokens without authentication
                    result = t.getToken();
                }
            }
        }

        return result;
    }

    @Override
    public void storeToken(String resourceId, OAuthConsumerToken token) {
        // on saving OAuthSecurityContextHolder.getContext() -> details -> request -> uid+verifier
        // the tokens are saved from /login where there is no authentication,
        // so we have to get uid from the request
        String uid = AuthTools.getUidFromRequest();
        if (null != uid) {
            tokens.put(uid + "\t" + resourceId, new OAuthConsumerTokenHolder(token));
        }
    }

    @Override
    public void removeToken(String resourceId) {
        // on saving OAuthSecurityContextHolder.getContext() -> details -> request -> uid+verifier
        // request tokens are removed from /login where there is no authentication
        // so we have to get uid from the request
        String uid = AuthTools.getUidFromRequest();
        if (null != uid) {
            tokens.remove(uid + "\t" + resourceId);
        }
    }

    /**
     * The validity (in seconds) of the unauthenticated request token.
     *
     * @return The validity (in seconds) of the unauthenticated request token.
     */
    public int getRequestTokenValiditySeconds() {
        return requestTokenValiditySeconds;
    }

    /**
     * The validity (in seconds) of the unauthenticated request token.
     *
     * @param requestTokenValiditySeconds The validity (in seconds) of the unauthenticated request token.
     */
    public void setRequestTokenValiditySeconds(int requestTokenValiditySeconds) {
        this.requestTokenValiditySeconds = requestTokenValiditySeconds;
    }


    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        if (cleanupIntervalSeconds == null) {
            cleanupIntervalSeconds = 60 * 60;
        }

        if (cleanupIntervalSeconds > 0) {
            scheduler = Executors.newSingleThreadScheduledExecutor();
            Runnable cleanupLogic = new Runnable() {
                public void run() {
                    log.info("Cleaning up stale tokens...");
                    long count = 0;
                    Iterator<Map.Entry<String, OAuthConsumerTokenHolder>> entriesIt = tokens.entrySet().iterator();
                    while (entriesIt.hasNext()) {
                        Map.Entry<String, OAuthConsumerTokenHolder> entry = entriesIt.next();
                        OAuthConsumerTokenHolder tokenImpl = entry.getValue();
                        if (isExpired(tokenImpl)) {
                            //there's a race condition here, but we'll live with it for now.
                            log.info("Removing stale token {}", tokenImpl.getToken().getValue());
                            entriesIt.remove();
                            count++;
                        }
                    }
                    log.info("Removed stale tokens: {}", count);
                }
            };
            scheduler.scheduleAtFixedRate(cleanupLogic, getRequestTokenValiditySeconds(), cleanupIntervalSeconds, TimeUnit.SECONDS);
        }
    }

    private boolean isExpired(OAuthConsumerTokenHolder tokenHolder) {
        return (tokenHolder.getTimestamp() + (getRequestTokenValiditySeconds() * 1000L)) < System.currentTimeMillis();
    }

    @PreDestroy
    public void destroy() throws Exception {
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }
}
