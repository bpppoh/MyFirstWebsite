package com.project.controller;

import com.project.model.CookieManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * UserLogoutServlet
 * Handles user logout functionality including session invalidation and cookie
 * cleanup
 * 
 * @author ponlawatchangto
 */
@WebServlet(urlPatterns = { "/LogOut" })
public class UserLogoutServlet extends HttpServlet {

    /**
     * Processes logout requests for both GET and POST methods
     * 
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Entering UserLogoutServlet");

        // Get current session (don't create new one)
        HttpSession session = request.getSession(false);

        // Invalidate session if exists
        if (session != null) {
            session.invalidate();
            // session.removeAttribute("id");
            System.out.println("Session invalidated successfully.");
        } else {
            System.out.println("No active session found.");
        }

        // Clear all cookies using CookieManager
        CookieManager.deleteAllCookies(response, request.getCookies());
        System.out.println("All user cookies cleared successfully.");
        // Redirect to index page
        // RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        // dispatcher.forward(request, response);
        System.out.println("Exiting UserLogoutServlet");
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    /**
     * Handles the HTTP GET method
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP POST method
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "LogOut Servlet - Handles user logout functionality";
    }
}
