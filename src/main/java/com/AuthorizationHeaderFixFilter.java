package com;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.*;

/**
 * @author Evgeny Borisov
 */

//@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthorizationHeaderFixFilter implements Filter {

    @Value("${authorization-header-name:x-epam-authorization}")
    private String authorizationHeader;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        System.out.println(req.getRequestURI());
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(req);
        String authorization = mutableRequest.getHeader(authorizationHeader);
        if (authorization != null) {
            authorization = authorization.replaceAll("JWT", "Bearer");
            mutableRequest.putHeader("Authorization", authorization);
            filterChain.doFilter(mutableRequest, servletResponse);
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    final class MutableHttpServletRequest extends HttpServletRequestWrapper {
        // holds custom header and value mapping
        private final Map<String, String> customHeaders;

        MutableHttpServletRequest(HttpServletRequest request) {
            super(request);
            this.customHeaders = new HashMap<String, String>();
        }

        void putHeader(String name, String value) {
            this.customHeaders.put(name, value);
        }


        public String getHeader(String name) {
            // check the custom headers first
            String headerValue = customHeaders.get(name);

            if (headerValue != null) {
                return headerValue;
            }
            // else return from into the original wrapped object
            return ((HttpServletRequest) getRequest()).getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            Enumeration<String> headers = super.getHeaders(name);
            String newHeader = customHeaders.get(name);
            if (newHeader != null) {
                List<String> l = new ArrayList<>();
                l.add(newHeader);
                while (headers.hasMoreElements()) {
                    l.add(headers.nextElement());
                }
                headers = Collections.enumeration(l);
            }
            return headers;
        }

        public Enumeration<String> getHeaderNames() {
            // create a set of the custom header names
            Set<String> set = new HashSet<String>(customHeaders.keySet());

            // now add the headers from the wrapped request object
            @SuppressWarnings("unchecked")
            Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
            while (e.hasMoreElements()) {
                // add the names of the request headers into the list
                String n = e.nextElement();
                set.add(n);
            }

            // create an enumeration from the set and return
            return Collections.enumeration(set);
        }
    }
}





