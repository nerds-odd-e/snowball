package com.odde.massivemailer.filter;

import com.odde.massivemailer.service.impl.SqliteContact;
import com.odde.massivemailer.service.impl.SqliteTracking;

import javax.servlet.*;
import java.util.*;

public class ImageFilter implements Filter{

    private static int count = 0;
    private SqliteTracking sqliteTracking;


    public void  init(FilterConfig config)
            throws ServletException{

    }
    public void  doFilter(ServletRequest request,
                          ServletResponse response,
                          FilterChain chain)
            throws java.io.IOException, ServletException {

        // Get the IP address of client machine.
        //String ipAddress = request.getRemoteAddr();
        String emailId = request.getParameter("emailId");
        String userId = request.getParameter("userId");

        System.out.println("Email ID: " + emailId);
        System.out.println("User ID:" + userId);
        // Log the IP address and current timestamp.
        //System.out.println("IP "+ ipAddress + ", Time "
        //        + new Date().toString());
        //        + new Date().toString());


        getSqliteTracking().updateViewCount(emailId, userId);

         count++;
        System.out.println("Current view: " + count);
        // Pass request back down the filter chain
        chain.doFilter(request,response);
    }
    public void destroy( ){
    }

    public SqliteTracking getSqliteTracking() {
        return (sqliteTracking == null) ? sqliteTracking = new SqliteTracking() : sqliteTracking;
    }
}
