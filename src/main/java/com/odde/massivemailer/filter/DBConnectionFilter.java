package com.odde.massivemailer.filter;

import org.javalite.activejdbc.Base;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class DBConnectionFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        ServletContext application = filterConfig.getServletContext();
        try{
            Base.open("com.mysql.jdbc.Driver", getDBLink(application), "root", "");
            Base.openTransaction();
            chain.doFilter(req, resp);
            Base.commitTransaction();
        }
        catch (IOException e){
            Base.rollbackTransaction();
            throw e;
        }
        catch (ServletException e){
            Base.rollbackTransaction();
            throw e;
        }
        finally{
            Base.close();
        }
    }

    private String getDBLink(ServletContext application) {
        String linkStr;
        linkStr = "jdbc:mysql://localhost:3306/oddemail";
        Object dblink = application.getAttribute("dbLink");
        if (dblink != null)
            linkStr = (String) dblink;
        return linkStr;
    }

    @Override
    public void destroy() { }
}
