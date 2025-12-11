/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package com.project.filter;
import com.project.model.Car;
import com.project.model.CarDAO;
import com.project.model.UserInfoDAO;
import com.project.model.SessionManager;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
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
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ponlawatchangto
 */
@WebFilter(filterName = "BrowsingFilter", urlPatterns = { "/browse-cars.jsp" })
public class BrowsingFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with. If
    // this value is null, this filter instance is not currently
    // configured.
    private FilterConfig filterConfig = null;

    public BrowsingFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("BrowsingFilter:DoBeforeProcessing");
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
            log("BrowsingFilter:DoAfterProcessing");
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
        System.out.println("Entering Browsing Filter");

        // Create HttpServletRequest object for setting request attributes , HttpServletResponse for redirect
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();

        // Get all cars from the database
        try {
            List<Car> carList = CarDAO.getCarsWithPicture();
            httpRequest.setAttribute("carList", carList);
            if(carList != null) {
                // Car list is available
                System.out.println("BrowsingFilter: carList is not null, size = " + carList.size());
            } else {
                // Car list is null
                System.out.println("BrowsingFilter: carList is null");
                System.out.println("Exiting BrowsingFilter");
                httpResponse.sendRedirect("error.jsp?error=No cars found");
                return; 
            }
            // set request attribute admin to true if user is admin by checking session attribute "id"
            if(!SessionManager.isSessionEmpty(session) && !SessionManager.isAttributeEmpty(session, "id")) {
                int userID = (int) session.getAttribute("id");
                String userTier = UserInfoDAO.getTierByID(userID);
                if(userTier != null && "Admin".equals(userTier)) {
                    httpRequest.setAttribute("isAdmin", true);
                    System.out.println("BrowsingFilter: User is Admin");
                }
            }
        } catch (Exception e) {
            System.out.println("BrowsingFilter: Exception occurred while fetching car list");
            e.printStackTrace();
            System.out.println("Exiting BrowsingFilter by Exception");
            httpResponse.sendRedirect("error.jsp?error=Error fetching car list");
            return; // Stop further processing
        }
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
                log("BrowsingFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("BrowsingFilter()");
        }
        StringBuffer sb = new StringBuffer("BrowsingFilter(");
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
