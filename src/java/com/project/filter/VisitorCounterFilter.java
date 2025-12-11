/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */

/*
1. Create HttpServletRequest and HttpSession objects
2. Check if the session attribute "hasVisited" is null or false , If true, need to log visit
2.1 Check if session attribute "id" is null , if null, username = "Guest" else get username from database using id
3. Start visitor_counterDAO.logVisit and check if it is successful , if not redirect to errorPage.jsp
4. Set session attribute "hasVisited" to true
5. Update visitor counts in application scope by getting all visitor counts from database

*/

package com.project.filter;

import com.project.model.SessionManager;
import com.project.model.UserInfo;
import com.project.model.UserInfoDAO;
import com.project.model.visitor_counter;
import com.project.model.visitor_counterDAO;
import com.project.model.CookieManager;

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
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author ponlawatchangto
 */
@WebFilter(filterName = "VisitorCounterFilter", urlPatterns = { "*.jsp" })
public class VisitorCounterFilter implements Filter {

    private static final boolean debug = true;
    private FilterConfig filterConfig = null;


    public VisitorCounterFilter() {
        System.out.println("Initializing VisitorCounterFilter");
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("VisitorCounterFilter:DoBeforeProcessing");
        }
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("VisitorCounterFilter:DoAfterProcessing");
        }

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

        System.out.println("Entering VisitorCounterFilter");
        HttpServletRequest httpRequest = null ;
        HttpServletResponse httpResponse = null ;
        HttpSession session = null ;
        // 1. Create HttpServletRequest and HttpSession objects
        try {
            httpRequest = (HttpServletRequest) request;
            httpResponse = (HttpServletResponse) response;
            session = httpRequest.getSession(true);
        } catch (Exception e) {
            System.out.println("VisitorCounterFilter: Error occurred");
            e.printStackTrace();
        }
        
        // Check hasVisited attribute in session is it null or false
        // 2. Check if the session attribute "hasVisited" is null or false , If true, need to log visit
        System.out.println("VisitorCounterFilter: Checking hasVisited attribute in session");
        try {
            if ( SessionManager.isSessionEmpty(session) || SessionManager.isAttributeEmpty(session, "hasVisited") || session.getAttribute("hasVisited").equals(false)) {
                System.out.println("VisitorCounterFilter: hasVisited is null or false, logging visit");
                // Sure that hasVisited is null or false
                // Start visitor_counterDAO.logVisit 
                // Getting id from session
                // 2.1 Check if session attribute "id" is null , if null, username = "Guest" else get username from database using id
                String username = null ;
                if (SessionManager.isAttributeEmpty(session, "id")) {
                    if(CookieManager.deleteAllCookies(httpResponse, httpRequest.getCookies())) {
                        System.out.println("VisitorCounterFilter: Cookies deleted successfully");
                    }
                    // If session id is null, it means user is guest
                    username = "Guest";
                } else {
                    // If session id is not null, need to get username from database
                    username = UserInfoDAO.getUsernameByID((Integer) session.getAttribute("id"));
                }
                
                // Sure that username is being set
                // 3. Start visitor_counterDAO.logVisit and check if it is successful , if not redirect to errorPage.jsp
                System.out.println("VisitorCounterFilter: Logging visit");
                if (!visitor_counterDAO.logVisit(username)) {
                    System.out.println("VisitorCounterFilter: Cannot log visit, redirecting to errorPage.jsp");
                    // use contextPath so redirect is absolute within the webapp
                    System.out.println("VisitorCounterFilter: Redirecting to errorPage.jsp");
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/errorPage.jsp?error=cannot+operate+login");
                    return;
                }
                // 4. Set session attribute "hasVisited" to true
                System.out.println("VisitorCounterFilter: Setting hasVisited to true");
                session.setAttribute("hasVisited", true);
                // 5. Update visitor counts in application scope by getting all visitor counts from database
                System.out.println("VisitorCounterFilter: Updating visitor counts in application scope");
            }
            List<visitor_counter> vcList = visitor_counterDAO.getAllVisitorCounts();
            ServletContext application = request.getServletContext();
            for (visitor_counter vc : vcList) {
                application.setAttribute(vc.getCounterName(), vc.getVisitor_count());
            }
        } catch (Exception e) {
            System.out.println("VisitorCounterFilter: Error occurred while checking hasVisited attribute in session");
            e.printStackTrace();
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/errorPage.jsp?error=" + e.getMessage());
            return;
        }
        System.out.println("Exiting VisitorCounterFilter");
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
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("VisitorCounterFilter()");
        }
        StringBuffer sb = new StringBuffer("VisitorCounterFilter(");
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
                ex.printStackTrace();
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
                ex.printStackTrace();
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
            ex.printStackTrace();
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
