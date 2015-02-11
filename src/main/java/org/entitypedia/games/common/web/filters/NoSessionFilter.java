package org.entitypedia.games.common.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class NoSessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //nop
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        NoSessionHttpServletRequestWrapper request = new NoSessionHttpServletRequestWrapper((HttpServletRequest) servletRequest);

        chain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {
        //nop
    }
}
