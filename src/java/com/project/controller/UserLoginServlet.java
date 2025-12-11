package com.project.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import com.project.model.CookieManager;
import com.project.model.SessionManager;
import com.project.model.UserInfo;
import com.project.model.UserInfoDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import jakarta.servlet.http.Cookie;

/**
 * UserLoginServlet
 * Handles user authentication and login functionality
 * 
 * @author ponlawatchangto
 */
@WebServlet(urlPatterns = { "/Login" })
public class UserLoginServlet extends HttpServlet {

    public void init() {
        System.out.println("Initializing Login Servlet...");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Entering Login Servlet");
        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            UserInfo userInfo = UserInfoDAO.checkUser(username, password);
            // If userInfo is not null, login is successful
            if (userInfo != null) {
                System.out.println("Login Success");

                // Set cookies for username and loggedIn status via CookieManager methods
                CookieManager.setCookie(response, "username", username); // 1 day
                CookieManager.setCookie(response, "loggedIn", "true");

                // Set session attributes ("id") via SessionManager method
                if (SessionManager.setAttribute(session, "id", userInfo.getID())) {
                    System.out.println("Session attribute 'id' set successfully");
                } else {
                    System.out.println("Failed to set session attribute 'id'");
                    SessionManager.destroySession(session);
                    response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error="
                            + "Error while setting session in UserLoginServlet when SessionManager.setAttribute");
                }
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                System.out.println("Login Failed");
                request.setAttribute("errorMessage", "Wrong username or password");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("UserLoginServlet: Error occurred during login process");
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=" + e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
