package com.odde.massivemailer.filter;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.web.ActiveJdbcFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class DBConnectionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try{
            Base.open("org.sqlite.JDBC", "jdbc:sqlite:oddemail.db", "", "");
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

    @Override
    public void destroy() {

    }
}
