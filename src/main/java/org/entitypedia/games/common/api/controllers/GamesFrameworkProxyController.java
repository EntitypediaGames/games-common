package org.entitypedia.games.common.api.controllers;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.InputStreamEntity;
import org.entitypedia.games.common.util.ChannelTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Forwards requests to framework.
 *
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
@Controller
public class GamesFrameworkProxyController {

    private static final Logger log = LoggerFactory.getLogger(GamesFrameworkProxyController.class);

    private ProtectedResourceDetails protectedResourceDetails;
    private OAuthConsumerTokenServices consumerTokenServices;

    private OAuthConsumerSupport support = new CoreOAuthConsumerSupport();

    private String frameworkAPIRoot;
    private String frameworkSecureAPIRoot;

    // the client to do all the requests
    private HttpClient httpClient;

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
            HttpResponse response = httpClient.execute(requestBase);
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

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
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