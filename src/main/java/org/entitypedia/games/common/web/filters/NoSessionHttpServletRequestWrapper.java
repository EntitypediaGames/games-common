package org.entitypedia.games.common.web.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class NoSessionHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request request
     * @throws IllegalArgumentException if the request is null
     */
    public NoSessionHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public HttpSession getSession(boolean create) {
        if (create) {
            throw new UnsupportedOperationException();
        } else {
            return null;
        }
    }

    @Override
    public HttpSession getSession() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        throw new UnsupportedOperationException();
    }
}
