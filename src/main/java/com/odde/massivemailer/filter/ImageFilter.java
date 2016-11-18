package com.odde.massivemailer.filter;

import com.odde.massivemailer.service.TrackingService;
import com.odde.massivemailer.service.impl.SqliteTracking;

import javax.servlet.*;


public class ImageFilter implements Filter {
    static final String TOKEN = "token";

    private TrackingService sqliteTracking;


    public void init(FilterConfig config)
            throws ServletException {

    }

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws java.io.IOException, ServletException {
        String token = request.getParameter(TOKEN);

        if (token != null) {
            getSqliteTracking().updateViewCount(Long.parseLong(token));
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public TrackingService getSqliteTracking() {
        return (sqliteTracking == null) ? sqliteTracking = new SqliteTracking() : sqliteTracking;
    }

    public void setTrackingService(final TrackingService trackingService) {
        this.sqliteTracking = trackingService;
    }
}
