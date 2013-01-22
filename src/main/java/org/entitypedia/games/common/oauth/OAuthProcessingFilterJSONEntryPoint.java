package org.entitypedia.games.common.oauth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.provider.OAuthProcessingFilterEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class OAuthProcessingFilterJSONEntryPoint extends OAuthProcessingFilterEntryPoint {

    private HandlerExceptionResolver exceptionResolver;

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        StringBuilder headerValue = new StringBuilder("OAuth");
        if (null != getRealmName()) {
            headerValue.append(" realm=\"").append(getRealmName()).append('"');
        }
        response.addHeader("WWW-Authenticate", headerValue.toString());
        exceptionResolver.resolveException(request, response, null, authException);
    }

    public HandlerExceptionResolver getExceptionResolver() {
        return exceptionResolver;
    }

    public void setExceptionResolver(HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }
}