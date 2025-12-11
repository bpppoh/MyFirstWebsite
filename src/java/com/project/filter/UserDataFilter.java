/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package com.project.filter;

import com.project.model.CookieManager;
import com.project.model.UserInfoDAO;
import com.project.model.SessionManager;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ponlawatchangto
 */
@WebFilter(filterName = "UserDataFilter", urlPatterns = { "*.jsp" })
public class UserDataFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with. If
    // this value is null, this filter instance is not currently
    // configured.
    private FilterConfig filterConfig = null;

    public UserDataFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("UserDataFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
         * for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
         * String name = (String)en.nextElement();
         * String values[] = request.getParameterValues(name);
         * int n = values.length;
         * StringBuffer buf = new StringBuffer();
         * buf.append(name);
         * buf.append("=");
         * for(int i=0; i < n; i++) {
         * buf.append(values[i]);
         * if (i < n-1)
         * buf.append(",");
         * }
         * log(buf.toString());
         * }
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("UserDataFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed.
        /*
         * for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
         * String name = (String)en.nextElement();
         * Object value = request.getAttribute(name);
         * log("attribute: " + name + "=" + value.toString());
         * 
         * }
         */
        // For example, a filter might append something to the response.
        /*
         * PrintWriter respOut = new PrintWriter(response.getWriter());
         * respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request  The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain    The filter chain we are processing
     *
     * @exception IOException      if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        System.out.println("Entering UserDataFilter");
        // Create Essential Objects (HttpServletRequest, HttpServletResponse,
        // HttpSession)
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        try {
            // Check existing of session's id
            if (SessionManager.isSessionEmpty(session) || SessionManager.isAttributeEmpty(session, "id")) {
                System.out.println(" UserDataFilter: Session is not exist or Session attribute id is not exist");
                // If session is not exist, check existing of cookie "username"
                if (CookieManager.checkCookieExist(httpRequest.getCookies(), "username")) {
                    System.out.println(" UserDataFilter: Cookie username exists, but session ID does not. Clearing state.");
                    // Invalidate the session if it exists (even without the 'id' attribute)
                    if (session != null) {
                        session.invalidate();
                    }
                    // Clear all cookies
                    CookieManager.deleteAllCookies(httpResponse, httpRequest.getCookies());
                    // Redirect to the home page to enforce a clean state
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp");
                    return; // Stop the filter chain
                }
            } else {
                // If session is exist, check existing of attribute "username" in cookie
                if (!CookieManager.checkCookieExist(httpRequest.getCookies(), "username")) {
                    System.out.println(" UserDataFilter: Cookie username is not exist");
                    // If attribute "username" is not exist, get username from database and set
                    // cookie
                    int id = (int) session.getAttribute("id");
                    String username = UserInfoDAO.getUsernameByID(id);
                    CookieManager.setCookie(httpResponse, "username", username);
                    System.out.println(" UserDataFilter: Set cookie username");
                }
            }
        } catch (Exception e) {
            System.out.println("UserDataFilter: Error occurred sendRedirect to errorPage.jsp");
            e.printStackTrace();
            httpResponse
                    .sendRedirect(httpRequest.getContextPath() + "/errorPage.jsp?error=" + "Error in UserDataFilter");
        }

        // Manage Tier of User        
        if(session != null && session.getAttribute("id") != null) {
            String tier = UserInfoDAO.getTierByID((int) session.getAttribute("id"));
            if (tier.equals("Admin")) {
                request.setAttribute("isAdmin", true);
                request.setAttribute("isMember",true) ;
            } else if (tier.equals("Member")) {
                request.setAttribute("isAdmin", false);
                request.setAttribute("isMember",true) ;
            } else {
                request.setAttribute("isAdmin", false);
                request.setAttribute("isMember",false) ;
            }
        }
        System.out.println("Exiting UserDataFilter : chain");
        chain.doFilter(request, response);
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("UserDataFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("UserDataFilter()");
        }
        StringBuffer sb = new StringBuffer("UserDataFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); // NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); // NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
