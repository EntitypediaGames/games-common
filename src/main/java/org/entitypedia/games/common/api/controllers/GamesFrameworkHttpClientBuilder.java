package org.entitypedia.games.common.api.controllers;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Holds connection manager for use with Game Framework client.
 *
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
@Configuration
public class GamesFrameworkHttpClientBuilder {

    private static final Logger log = LoggerFactory.getLogger(GamesFrameworkHttpClientBuilder.class);

    // connection manager
    private HttpClientConnectionManager connectionManager;
    // connection eviction - to avoid checking for stale connections on every connect, do it periodically here
    private ScheduledExecutorService scheduler;
    // in milliseconds
    private final static int connectionCleanupInterval = 60000;

    @Bean
    @Qualifier("gameFrameworkHttpClient")
    public HttpClient gameFrameworkHttpClient() {
        HttpClientBuilder builder = HttpClients.custom();

        builder.setDefaultRequestConfig(
                RequestConfig.custom()
                        .setConnectTimeout(60000)
                        .setSocketTimeout(60000)
                        .setStaleConnectionCheckEnabled(false)
                        .build()
        );

        SocketConfig defaultSocketConfig = SocketConfig.custom()
                .setSoKeepAlive(true)
                .setSoTimeout(60000)
                .setTcpNoDelay(true)
                .build();
        builder.setDefaultSocketConfig(defaultSocketConfig);

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setDefaultSocketConfig(defaultSocketConfig);
        cm.setMaxTotal(20);
        cm.setDefaultMaxPerRoute(10);
        this.connectionManager = cm;
        builder.setConnectionManager(getConnectionManager());

        // schedule stale connections clean up
        scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable cleanupLogic = new Runnable() {
            public void run() {
                HttpClientConnectionManager cm = getConnectionManager();
                if (null != cm) {
                    log.debug("Cleaning up stale connections...");
                    cm.closeExpiredConnections();
                }
            }
        };
        scheduler.scheduleAtFixedRate(cleanupLogic, connectionCleanupInterval, connectionCleanupInterval, TimeUnit.MILLISECONDS);

        return builder.build();
    }

    @PreDestroy
    public void destroy() throws Exception {
        if (null != scheduler) {
            scheduler.shutdownNow();
        }
        if (null != connectionManager) {
            connectionManager.shutdown();
        }
    }

    public HttpClientConnectionManager getConnectionManager() {
        return connectionManager;
    }
}