package com.project.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author ponlawatchangto
 */
@WebFilter(filterName = "memberFilter", urlPatterns = {"/member/*"})
public class memberFilter implements Filter {

    //รันรอบแรกรอบเดียว
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("(Filtering) Member Filter is started . . .") ;
    }

    //รันทุกครั้งที่มีการเรียกใช้
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("Entering Member Filter");
        
        HttpServletRequest httpRequest = (HttpServletRequest) request ;
        HttpServletResponse httpResponse = (HttpServletResponse) response ;
        
        HttpSession session = httpRequest.getSession(false) ;

        if(session != null && session.getAttribute("id") != null ) {
            //ถ้า login แล้ว
            System.out.println("memberFilter : User is logged in");
            System.out.println("Exiting Member Filter");
            chain.doFilter(request, response);
        } else {
            //ถ้ายังไม่ login
            System.out.println("memberFilter : User is not logged in");
            System.out.println("Exiting Member Filter");
            System.out.println("Redirecting to login.jsp");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=Please login first");
        }
        
    }

    @Override
    public void destroy() {
        System.out.println("(Filtering) Member Filter is ended . . .") ;
    }
}