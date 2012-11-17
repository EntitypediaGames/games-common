package org.entitypedia.games.common.oauth;

import org.entitypedia.games.common.exceptions.AuthenticatedUserRequiredException;
import org.entitypedia.games.common.model.WordGameUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth.consumer.OAuthSecurityContext;
import org.springframework.security.oauth.consumer.OAuthSecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * Authentication tools.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class AuthTools {

//    keep enabled or disabled in sync with <security:global-method-security pre-post-annotations="enabled"/>

    private static final Logger log = LoggerFactory.getLogger(AuthTools.class);

    /**
     * Returns currently authenticated user or null if there isn't one.
     *
     * @return currently authenticated user or null if there isn't one
     */
    @SuppressWarnings("unchecked")
    public static <T extends WordGameUser> T findCurrentUser() {
        T result = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (null != securityContext) {
            Authentication a = securityContext.getAuthentication();
            if (null != a) {
                if (a.getPrincipal() instanceof WordGameUser) {
                    result = (T) a.getPrincipal();
                    log.debug("Read user from securityContext.getAuthentication: {}", result);
                }
            }
        }
        return result;
    }

    /**
     * Returns currently authenticated user or throws an exception if there isn't one.
     *
     * @return currently authenticated user or throws an exception if there isn't one
     */
    public static <T extends WordGameUser> T getCurrentUser() {
        T result = findCurrentUser();
        if (null == result) {
            throw new AuthenticatedUserRequiredException();
        }
        return result;
    }

    public static String getUidFromRequest() {
        String result = null;
        OAuthSecurityContext context = OAuthSecurityContextHolder.getContext();
        if (null != context) {
            if (context.getDetails() instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) context.getDetails();
                result = request.getParameter("uid");
                log.debug("Read user uid from request: {}", result);
            }
        }
        return result;
    }
}
