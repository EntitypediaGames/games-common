package org.entitypedia.games.common.api.controllers;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.entitypedia.games.common.util.ChannelTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.oauth.consumer.OAuthConsumerSupport;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.CoreOAuthConsumerSupport;
import org.springframework.security.oauth.consumer.token.OAuthConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Forwards requests to framework.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
@Controller
public class ProxyController implements InitializingBean, DisposableBean {
    // TODO AA upgrade to 4.3 branch of httpclient

    private static final Logger log = LoggerFactory.getLogger(ProxyController.class);

    private ProtectedResourceDetails protectedResourceDetails;
    private OAuthConsumerTokenServices consumerTokenServices;

    private OAuthConsumerSupport support = new CoreOAuthConsumerSupport();

    private String frameworkAPIRoot;
    private String frameworkSecureAPIRoot;

    // connection manager
    private ClientConnectionManager cm;
    // connection eviction - to avoid checking for stale connections on every connect, do it periodically here
    private ScheduledExecutorService scheduler;
    // in seconds
    private final static int connectionCleanupInterval = 600;
    // the client to do all the requests
    private HttpClient client;

    @RequestMapping(value = "framework/**")
    public void proxy(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String urlString = req.getRequestURL().toString();
        urlString = urlString.substring(urlString.indexOf("/framework/") + "/framework/".length());
        String queryString = req.getQueryString();

        urlString += queryString == null ? "" : "?" + queryString;
        String url;
        if ("https".equals(req.getScheme().toLowerCase())) {
            url = frameworkSecureAPIRoot + urlString;
        } else {
            url = frameworkAPIRoot + urlString;
        }

        final String methodName = req.getMethod().toUpperCase();
        if ("GET".equals(methodName) || "POST".equals(methodName)) {
            log.debug("{}ting {}", methodName, url);
            HttpRequestBase requestBase;
            if ("GET".equals(methodName)) {
                requestBase = new HttpGet(url);
            } else {
                requestBase = new HttpPost(url);
            }

            // set authorization header
            OAuthConsumerToken token = consumerTokenServices.getToken(protectedResourceDetails.getId());
            String authHeader = support.getAuthorizationHeader(protectedResourceDetails, token, new URL(url), req.getMethod(), null);
            requestBase.addHeader("Authorization", authHeader);
            log.trace("Authorization: {}", authHeader);

            if ("POST".equals(methodName)) {
                log.debug("Copying POST content upstream");
                HttpEntity entity = new InputStreamEntity(req.getInputStream(), -1);
                if (requestBase instanceof HttpPost) {
                    ((HttpPost) requestBase).setEntity(entity);
                }
            }

            log.debug("Executing request...");
            HttpResponse response = client.execute(requestBase);
            log.debug("Response Status: {}", response.getStatusLine());
            res.setStatus(response.getStatusLine().getStatusCode());

            // copy Content-Type, ignore the rest
            Header[] contentType = response.getHeaders("Content-Type");
            if (null != contentType && 0 < contentType.length) {
                res.setHeader("Content-Type", contentType[0].getValue());
            }

            HttpEntity entity = response.getEntity();
            if (null != entity) {
                log.debug("Copying response stream");
                InputStream inputStream = entity.getContent();
                try {
                    ChannelTools.copyStream(inputStream, res.getOutputStream());
                    log.debug("Copied streams");
                    //} catch (IOException e) {
                    // In case of an IOException the connection will be released
                    // back to the connection manager automatically
                    //throw e;
                } catch (RuntimeException ex) {
                    // In case of an unexpected exception you may want to abort
                    // the HTTP request in order to shut down the underlying
                    // connection immediately.
                    requestBase.abort();
                    throw ex;
                } finally {
                    // Closing the input stream will trigger connection release
                    try {
                        inputStream.close();
                    } catch (Exception ignore) {
                        // ignore exception on closing the stream
                    }
                }
            }

            log.debug("Finished request processing");
        } else {
            throw new UnsupportedOperationException("Unsupported method: " + req.getMethod());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("Registering schemes...");
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        log.debug("Registering " + frameworkAPIRoot);
        URL apiURL = new URL(frameworkAPIRoot);
        schemeRegistry.register(new Scheme("http", -1 == apiURL.getPort() ? 80 : apiURL.getPort(), PlainSocketFactory.getSocketFactory()));
        log.debug("Registering " + frameworkSecureAPIRoot);
        URL apiSecureURL = new URL(frameworkSecureAPIRoot);
        schemeRegistry.register(new Scheme("https", -1 == apiSecureURL.getPort() ? 443 : apiSecureURL.getPort(), SSLSocketFactory.getSocketFactory()));

        log.debug("Starting connections pools...");
        PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
        cm.setMaxTotal(200);
        // Increase default max connection per route to 100
        cm.setDefaultMaxPerRoute(100);
        // Increase max connections for api host:80 & host:443 to 100
        HttpHost apiHost = new HttpHost(apiURL.getHost(), apiURL.getPort());
        cm.setMaxPerRoute(new HttpRoute(apiHost), 100);
        HttpHost apiSecureHost = new HttpHost(apiSecureURL.getHost(), apiSecureURL.getPort());
        cm.setMaxPerRoute(new HttpRoute(apiSecureHost), 100);

        this.cm = cm;

        HttpParams params = new SyncBasicHttpParams();
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        params.setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true);
        params.setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 102400);
        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false);

        client = new DefaultHttpClient(cm, params);

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

    @Override
    public void destroy() throws Exception {
        if (null != scheduler) {
            scheduler.shutdownNow();
        }
        if (null != cm) {
            cm.shutdown();
        }
    }

    public ClientConnectionManager getCm() {
        return cm;
    }

    public ProtectedResourceDetails getProtectedResourceDetails() {
        return protectedResourceDetails;
    }

    public void setProtectedResourceDetails(ProtectedResourceDetails protectedResourceDetails) {
        this.protectedResourceDetails = protectedResourceDetails;
    }

    public OAuthConsumerTokenServices getConsumerTokenServices() {
        return consumerTokenServices;
    }

    public void setConsumerTokenServices(OAuthConsumerTokenServices consumerTokenServices) {
        this.consumerTokenServices = consumerTokenServices;
    }

    public String getFrameworkAPIRoot() {
        return frameworkAPIRoot;
    }

    public void setFrameworkAPIRoot(String frameworkAPIRoot) {
        this.frameworkAPIRoot = frameworkAPIRoot;
    }

    public String getFrameworkSecureAPIRoot() {
        return frameworkSecureAPIRoot;
    }

    public void setFrameworkSecureAPIRoot(String frameworkSecureAPIRoot) {
        this.frameworkSecureAPIRoot = frameworkSecureAPIRoot;
    }
}