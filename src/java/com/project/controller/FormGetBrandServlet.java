/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.project.controller;

import com.project.model.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * @author ponlawatchangto
 */
@WebServlet(name = "FormGetBrandServlet", urlPatterns = {"/FormGetBrandServlet"})
public class FormGetBrandServlet extends HttpServlet {

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
        System.out.println("Entering FormGetBrandServlet");

        String htmlOutput = "" ;
        PrintWriter out = response.getWriter();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("select * from car_brands");
             ResultSet rs = pstmt.executeQuery()) {

            while(rs.next()) {
                htmlOutput += "<option value='" + rs.getInt("brand_id") + "'>" + rs.getString("brand_name") + "</option>";
            }
        } catch (SQLException e) {
            // Log the exception and send an error response or handle it as needed
            System.out.println("FormGetBrandServlet Error: Database access error.");
            e.printStackTrace();
            // Optionally, you could set a specific error message in htmlOutput
            // For example: htmlOutput = "<option value=''>Error loading brands</option>";
        } catch (Exception e) {
            System.out.println("FormGetBrandServlet Error : A general error occurred");
            htmlOutput = "" ;
            e.printStackTrace();
        }
        System.out.println("Exiting FormGetBrandServlet and Sent htmlOutput back to client via AJAX");
        out.print(htmlOutput);
        out.flush();
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
