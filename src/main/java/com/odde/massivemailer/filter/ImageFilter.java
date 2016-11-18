package com.odde.massivemailer.filter;

import com.odde.massivemailer.service.TrackingService;
import com.odde.massivemailer.service.impl.SqliteTracking;

import javax.servlet.*;

public class ImageFilter implements Filter {
    static final String TOKEN = "token";

    private TrackingService trackingService;

    public void init(FilterConfig config) throws ServletException {
        trackingService = new SqliteTracking();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws java.io.IOException, ServletException {
        String token = request.getParameter(TOKEN);

        if (token != null) {
            trackingService.updateViewCount(Long.parseLong(token));
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void setTrackingService(final TrackingService trackingService) {
        this.trackingService = trackingService;
    }
}
