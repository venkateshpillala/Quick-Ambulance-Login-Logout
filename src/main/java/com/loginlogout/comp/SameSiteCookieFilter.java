package com.loginlogout.comp;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SameSiteCookieFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		chain.doFilter(request, response);
        if (response instanceof HttpServletResponse) {
            HttpServletResponse res = (HttpServletResponse) response;
            for (String header : res.getHeaders("Set-Cookie")) {
                if (header.startsWith("JSESSIONID")) {
                    res.setHeader("Set-Cookie", header + "; SameSite=None; Secure");
                }
            }
        }

	}

}
