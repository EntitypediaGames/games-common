package org.entitypedia.games.common.oauth;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class AccessDeniedJSONHandler implements InitializingBean, AccessDeniedHandler {

    private HandlerExceptionResolver exceptionResolver;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(exceptionResolver, "An exception resolver is required.");
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        exceptionResolver.resolveException(request, response, null, accessDeniedException);
    }

    public HandlerExceptionResolver getExceptionResolver() {
        return exceptionResolver;
    }

    public void setExceptionResolver(HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }
}
