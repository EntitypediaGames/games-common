package org.entitypedia.games.common.api.controllers;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Hold connection manager for use with Game Framework client.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class GameFrameworkHttpClient implements HttpClient, InitializingBean, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(GameFrameworkHttpClient.class);

    private String frameworkAPIRoot;
    private String frameworkSecureAPIRoot;

    private HttpClient delegate;

    // connection manager
    private ClientConnectionManager connectionManager;
    // connection eviction - to avoid checking for stale connections on every connect, do it periodically here
    private ScheduledExecutorService scheduler;
    // in seconds
    private final static int connectionCleanupInterval = 600;

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
        cm.setMaxTotal(20);
        // Increase default max connection per route to 10
        cm.setDefaultMaxPerRoute(10);
        // Increase max connections for api host:80 & host:443 to 10
        HttpHost apiHost = new HttpHost(apiURL.getHost(), apiURL.getPort());
        cm.setMaxPerRoute(new HttpRoute(apiHost), 10);
        HttpHost apiSecureHost = new HttpHost(apiSecureURL.getHost(), apiSecureURL.getPort());
        cm.setMaxPerRoute(new HttpRoute(apiSecureHost), 10);

        this.connectionManager = cm;

        // schedule stale connections clean up
        scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable cleanupLogic = new Runnable() {
            public void run() {
                ClientConnectionManager cm = getConnectionManager();
                if (null != cm) {
                    log.debug("Cleaning up stale connections...");
                    cm.closeExpiredConnections();
                    log.debug("Cleaned up stale connections");
                }
            }
        };
        scheduler.scheduleAtFixedRate(cleanupLogic, connectionCleanupInterval, connectionCleanupInterval, TimeUnit.SECONDS);

        HttpParams params = new SyncBasicHttpParams();
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        params.setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true);
        params.setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 102400);
        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false);

        delegate = new DefaultHttpClient(connectionManager, params);
    }

    @Override
    public void destroy() throws Exception {
        if (null != scheduler) {
            scheduler.shutdownNow();
        }
        if (null != connectionManager) {
            connectionManager.shutdown();
        }
    }

    public ClientConnectionManager getConnectionManager() {
        return connectionManager;
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

    @Override
    public HttpParams getParams() {
        return delegate.getParams();
    }

    @Override
    public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
        return delegate.execute(request);
    }

    @Override
    public HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException, ClientProtocolException {
        return delegate.execute(request, context);
    }

    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException, ClientProtocolException {
        return delegate.execute(target, request);
    }

    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) throws IOException, ClientProtocolException {
        return delegate.execute(target, request, context);
    }

    @Override
    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return delegate.execute(request, responseHandler);
    }

    @Override
    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
        return delegate.execute(request, responseHandler, context);
    }

    @Override
    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return delegate.execute(target, request, responseHandler);
    }

    @Override
    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
        return delegate.execute(target, request, responseHandler, context);
    }
}