package org.entitypedia.games.common.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.entitypedia.games.common.client.WordGameClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.OAuthSecurityContextHolder;
import org.springframework.security.oauth.consumer.OAuthSecurityContextImpl;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.security.oauth.consumer.token.OAuthConsumerTokenServices;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Extends OAuthRestTemplate with proper authentication use.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class GameFrameworkRESTTemplate extends OAuthRestTemplate implements InitializingBean, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(GameFrameworkRESTTemplate.class);

    private URI apiBase;

    // connection manager
    private ClientConnectionManager cm;
    // connection eviction - to avoid checking for stale connections on every connect, do it periodically here
    private ScheduledExecutorService scheduler;
    // in seconds
    private final static int connectionCleanupInterval = 600;

    private OAuthConsumerTokenServices tokenServices;
    private ProtectedResourceDetails resource;

    private ResponseErrorHandler responseErrorHandler = new ThrowingResponseErrorHandler();

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

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("Setting error handler...");
        setErrorHandler(responseErrorHandler);

        log.debug("Registering schemes...");
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        log.debug("Registering " + apiBase.toURL().toString());
        schemeRegistry.register(new Scheme("http", -1 == apiBase.getPort() ? 80 : apiBase.getPort(), PlainSocketFactory.getSocketFactory()));

        log.debug("Starting connections pools...");
        PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
        cm.setMaxTotal(10);
        // Increase default max connection per route to 100
        cm.setDefaultMaxPerRoute(10);
        // Increase max connections for api host:80 & host:443 to 100
        HttpHost apiHost = new HttpHost(apiBase.getHost(), apiBase.getPort());
        cm.setMaxPerRoute(new HttpRoute(apiHost), 10);

        this.cm = cm;

        HttpParams params = new SyncBasicHttpParams();
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        params.setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true);
        params.setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 102400);
        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false);

        HttpClient client = new DefaultHttpClient(cm, params);

        setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));

        // schedule stale connections clean up
        scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable cleanupLogic = new Runnable() {
            public void run() {
                ClientConnectionManager cm = getCm();
                if (null != cm) {
                    log.debug("Cleaning up stale connections...");
                    cm.closeExpiredConnections();
                    log.debug("Cleaned up stale connections");
                }
            }
        };
        scheduler.scheduleAtFixedRate(cleanupLogic, connectionCleanupInterval, connectionCleanupInterval, TimeUnit.SECONDS);
    }

    public ClientConnectionManager getCm() {
        return cm;
    }

    @Override
    public void destroy() throws Exception {
        if (null != scheduler) {
            scheduler.shutdownNow();
        }
        if (null != cm) {
            cm.shutdown();
        }
    }

    private class ThrowingResponseErrorHandler implements ResponseErrorHandler {

        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().value() != 200;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            throw WordGameClient.processError(response.getBody(), mapper);
        }
    }
}