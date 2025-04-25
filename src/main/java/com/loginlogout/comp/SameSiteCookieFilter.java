package com.loginlogout.comp;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

@Component
public class SameSiteCookieFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(httpServletResponse) {
            @Override
            public void addHeader(String name, String value) {
                if ("Set-Cookie".equalsIgnoreCase(name)) {
                    value = value + "; SameSite=None";
                }
                super.addHeader(name, value);
            }
        };
        chain.doFilter(request, responseWrapper);
    }
}
