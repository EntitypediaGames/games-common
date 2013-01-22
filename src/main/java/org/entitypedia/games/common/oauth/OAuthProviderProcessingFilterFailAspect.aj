package org.entitypedia.games.common.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Replaces OAuthProviderProcessingFilter.fail method with one that supports exception resolver.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public privileged aspect OAuthProviderProcessingFilterFailAspect {

    private static final Logger log = LoggerFactory.getLogger(OAuthProviderProcessingFilterFailAspect.class);

    private HandlerExceptionResolver exceptionResolver;

    public HandlerExceptionResolver getExceptionResolver() {
        return exceptionResolver;
    }

    public void setExceptionResolver(HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    pointcut OAuthProviderProcessingFilterFailExecution(HttpServletRequest request, HttpServletResponse response, AuthenticationException failure):
            execution(protected void org.springframework.security.oauth.provider.filter.OAuthProviderProcessingFilter.fail(HttpServletRequest, HttpServletResponse, AuthenticationException))
                    && args(request, response, failure);

    void around(HttpServletRequest request, HttpServletResponse response, AuthenticationException failure): OAuthProviderProcessingFilterFailExecution(request, response, failure) {
        SecurityContextHolder.getContext().setAuthentication(null);

        if (log.isDebugEnabled()) {
            log.debug(failure.getMessage(), failure);
        }

        exceptionResolver.resolveException(request, response, null, failure);
    }
}
