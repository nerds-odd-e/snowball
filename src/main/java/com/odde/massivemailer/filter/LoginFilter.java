package com.odde.massivemailer.filter;

import javax.servlet.*;
import java.io.IOException;

public class LoginFilter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
}
