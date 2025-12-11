package com.project.filter;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
import com.project.model.Car ;
import com.project.model.CarDAO;
import com.project.model.UserInfo;
import com.project.model.UserInfoDAO;
import com.project.model.SidePic ;
import com.project.model.SidePicDAO ;

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
import java.util.List;

/**
 *
 * @author ponlawatchangto
 */
@WebFilter(filterName = "carInfomationFilter", urlPatterns = {"/carInfomation.jsp"})
public class CarInfomationFilter implements Filter {
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured.
    private FilterConfig filterConfig = null;
    
    public CarInfomationFilter() {
    }
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("carInfomationFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("carInfomationFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed.
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        // CarInfomationFilter work
        // Check car id in parameter that send form browse-cars.jsp or else
        System.out.println("Entering CarInfomationFilter (in filter folder)");
        // Create HttpServletRequest and HttpServletResponse objects to access parameter and redirect
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            // Check if id in parameter is exist and not empty
            if (httpRequest.getParameter("id") == null || httpRequest.getParameter("id").isEmpty()) {
                System.out.println("Car ID is missing or empty, redirecting to error page");
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp?error=Car ID is missing or empty");
                return;
            }
            // Sure that id is exist
            int carID = Integer.parseInt(httpRequest.getParameter("id"));
            System.out.println("CarInfomationFilter: Querying data for carID = " + carID);

            // Retrieve car object from CarDAO.getCar(id)
            Car car = CarDAO.getCar(carID);
            // Make sure that car is not null
            if (car == null) {
                System.out.println("CarInfomationFilter: Car not found, redirecting to error page");
                System.out.println("Exiting CarInfomationFilter with error");
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp?error=Car not found");
                return;
            }
            // Sure that car is not null
            // Set car object to request attribute
            request.setAttribute("car", car);

            // Preparing Car side pics
            List<SidePic> sidePics = SidePicDAO.getSidePicsByCarId(carID);
            if(sidePics != null){
                System.out.println("sidePics not null, size is: " + sidePics.size());
            } else {
                System.out.println("sidePics is null") ;
            }
            request.setAttribute("sidePics", sidePics);
            
            System.out.println("CarInfomationFilter: Car is retrieved and set to request attribute");
            System.out.println("Exiting CarInfomationFilter : Process successfully");
            // Set publisher name for displaying
            UserInfo user = UserInfoDAO.getUserByID(car.getCreator_id());
            if (user != null) {
                request.setAttribute("publisher", user);
            } else {
                request.setAttribute("publisher", null);
            }

        } catch (Exception e) { 
            System.out.println("Error occurred in CarInfomationFilter");
            e.printStackTrace();
            System.out.println("Exiting CarInfomationFilter with error");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp?error=" + e.getMessage());
        }
        chain.doFilter(request, response); // Moved here
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
                log("carInfomationFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("carInfomationFilter()");
        }
        StringBuffer sb = new StringBuffer("carInfomationFilter(");
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
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
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
