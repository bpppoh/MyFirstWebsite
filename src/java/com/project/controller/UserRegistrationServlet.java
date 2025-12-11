package com.project.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import com.project.model.*;

/**
 * UserRegistrationServlet
 * Handles user registration and account creation
 * 
 * @author ponlawatchangto
 */
@WebServlet(urlPatterns = { "/signUp" })
public class UserRegistrationServlet extends HttpServlet {


    public void init() {
        System.out.println("Initializing UserRegistrationServlet...");
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
        System.out.println("Entering UserRegistrationServlet");
        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        try {
            boolean success = UserInfoDAO.insertUser(username, password, email, phone);
            if (success) {
                // After successful sign-up, log the user in automatically
                System.out.println("User registration successful, proceeding to login.");
                UserInfo user = UserInfoDAO.checkUser(username, password);
                if (user != null) {
                    System.out.println("User login after registration successful.");
                    SessionManager.setAttribute(session, "id", user.getID());
                    System.out.println("Exiting UserRegistrationServlet");
                    response.sendRedirect(request.getContextPath() + "/index.jsp"); // Redirect to the main page
                } else {
                    // if user from UserInfoDAO.checkUser is null
                    System.out.println("UserRegistrationServlet: User not found after registration! Go to error page.");
                    String e = "User not found after registration!";
                    System.out.println("Exiting UserRegistrationServlet");
                    response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=" + e);
                }
            } else {
                // if success from UserInfoDAO.insertUser is false
                System.out.println("User registration failed! No row inserted.");
                SessionManager.destroySession(session);
                String e = "No row has been inserted !";
                System.out.println("Exiting UserRegistrationServlet");
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=" + e);
            }
        } catch (Exception e) {
            System.out.println("Error catch in UserRegistrationServlet");
            e.printStackTrace();
            System.out.println("Exiting UserRegistrationServlet");
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
