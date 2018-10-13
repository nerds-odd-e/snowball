package com.odde.massivemailer.filter;

import com.odde.massivemailer.model.SentMailVisit;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

@WebFilter("/resources/images/*")
public class ImageFilter implements Filter {
    static final String TOKEN = "token";

    public void init(FilterConfig config) {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws java.io.IOException, ServletException {
        String token = request.getParameter(TOKEN);

        if (token != null) {
            SentMailVisit nd = SentMailVisit.findById(Integer.parseInt(token));
            nd.updateViewCount();
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
