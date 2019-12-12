package com.rohov.tagsoft.filter;

import com.rohov.tagsoft.configuration.db.DBContextHolder;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DbContextFilter implements Filter {

    String DBHeader = "X-Country-Id";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String country = request.getHeader(DBHeader);

        DBContextHolder.setCurrentDB(country);

        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}

