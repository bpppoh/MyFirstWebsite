/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.project.controller;

import com.project.model.CarDAO;
import com.project.model.SessionManager;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ponlawatchangto
 */
@WebServlet(name = "ReserveServlet", urlPatterns = {"/ReserveServlet"})
public class ReserveServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        System.out.println("----------------------------------");
        System.out.println("Entering ReserveServlet");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();

        if(SessionManager.isSessionEmpty(session) || session.getAttribute("id") == null) {
            System.out.println("id in session is null , Please login first");
            System.out.println("----------------------------------");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=Please login first");
            return;
        }
        if(request.getParameter("carId") == null || request.getParameter("carId").equals("")) {
            System.out.println("carId isn't sent to PreBuyingFilter");
            System.out.println("----------------------------------");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/browse-cars.jsp");
            return;
        }
        int carId = Integer.parseInt(request.getParameter("carId"));
        if(CarDAO.checkAvailable(carId)){
            if(CarDAO.ReserveCar(carId)){
                System.out.println("Car is reserved successfully");
                System.out.println("----------------------------------");
                request.getRequestDispatcher("/reserveSuccess.jsp").forward(request, response);
            } else {
                System.out.println("Failed to reserve car");
                System.out.println("----------------------------------");
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/errorPage.jsp?error=Failed to reserve car");
            }
        } else {
            System.out.println("Car is not available");
            System.out.println("----------------------------------");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/errorPage.jsp?error=Car is not available");
        }

        System.out.println("Exiting ReserveServlet");
        System.out.println("----------------------------------");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
