package com.odde.massivemailer.filter;

import com.odde.massivemailer.service.impl.SqliteTracking;

import javax.servlet.*;


public class ImageFilter implements Filter{

    private SqliteTracking sqliteTracking;


    public void  init(FilterConfig config)
            throws ServletException{

    }

    public void  doFilter(ServletRequest request,
                          ServletResponse response,
                          FilterChain chain)
            throws java.io.IOException, ServletException {

        String messageId = request.getParameter("messageId");
        String userId = request.getParameter("userId");

        if(messageId != null && userId != null)
            getSqliteTracking().updateViewCount(Integer.parseInt(messageId), Integer.parseInt(userId));

        chain.doFilter(request,response);
    }

    public void destroy( ){
    }

    public SqliteTracking getSqliteTracking() {
        return (sqliteTracking == null) ? sqliteTracking = new SqliteTracking() : sqliteTracking;
    }
}
