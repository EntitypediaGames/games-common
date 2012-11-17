package org.entitypedia.games.common.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.OAuthSecurityContextHolder;
import org.springframework.security.oauth.consumer.OAuthSecurityContextImpl;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.security.oauth.consumer.token.OAuthConsumerTokenServices;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;

import java.net.URI;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class GameFrameworkRESTTemplate extends OAuthRestTemplate {

    private static final Logger log = LoggerFactory.getLogger(GameFrameworkRESTTemplate.class);

    private URI apiBase;

    private OAuthConsumerTokenServices tokenServices;
    private ProtectedResourceDetails resource;

    public GameFrameworkRESTTemplate(ProtectedResourceDetails resource) {
        super(resource);
        this.resource = resource;
    }

    public URI getApiBase() {
        return apiBase;
    }

    public void setApiBase(URI apiBase) {
        this.apiBase = apiBase;
    }

    private void loadOAuthSecurityContext() {
        log.debug("Loading OAuthSecurityContext for resource {}", resource.getId());
        OAuthConsumerToken token = getTokenServices().getToken(resource.getId());
        if (null == token) {
            log.debug("Token not found. Clearing OAuthSecurityContext for resource {}", resource.getId());
            OAuthSecurityContextHolder.setContext(null);
        } else {
            OAuthSecurityContextImpl context = new OAuthSecurityContextImpl();
            Map<String, OAuthConsumerToken> accessTokens = new TreeMap<String, OAuthConsumerToken>();

            accessTokens.put(resource.getId(), token);
            context.setAccessTokens(accessTokens);
            OAuthSecurityContextHolder.setContext(context);
            log.debug("Loaded OAuthSecurityContext for resource {} with token {}", resource.getId(), token.getValue());
        }
    }

    private void clearOAuthSecurityContext() {
        log.debug("Clearing OAuthSecurityContext for resource {}", resource.getId());
        OAuthSecurityContextHolder.setContext(null);
    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        try {
            loadOAuthSecurityContext();
            return super.doExecute(getApiBase().resolve(url), method, requestCallback, responseExtractor);
        } finally {
            clearOAuthSecurityContext();
        }

    }

    public OAuthConsumerTokenServices getTokenServices() {
        return tokenServices;
    }

    public void setTokenServices(OAuthConsumerTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }
}
