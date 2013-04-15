package org.entitypedia.games.common.oauth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Patches the filter to delegate the error processing to the authentication entry point till the
 * https://jira.springsource.org/browse/SECOAUTH-386 is resolved and the pull request is integrated.
 *
 */
public class ProtectedResourceProcessingFilter386 extends ProtectedResourceProcessingFilter {

    @Override
    protected void fail(HttpServletRequest request, HttpServletResponse response, AuthenticationException failure) throws IOException, ServletException {
        getAuthenticationEntryPoint().commence(request, response, failure);
    }
}
