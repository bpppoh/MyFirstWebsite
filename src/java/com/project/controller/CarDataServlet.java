/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.project.controller;

import com.project.model.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Use to Create checkBox List
/**
 *
 * @author ponlawatchangto
 */
@WebServlet(name = "CarDataServlet", urlPatterns = { "/CarDataServlet" })
public class CarDataServlet extends HttpServlet {

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
        System.out.println("Entering CarDataServlet");
        // Brought here by loadBrandFilter method in <script>
        // Get action parameter from AJAX
        String action = request.getParameter("action");

        // Set contentType as usual
        // Match with datatype in ajax
        response.setContentType("text/html;charset=UTF-8");

        // PrintWriter will write what will be sent back to browser
        try (PrintWriter out = response.getWriter()) {

            // Check action and it always true
            if ("getBrandsAsHtml".equals(action)) {

                // Prepared String to store what will appeared on Web page
                String htmlOutput = "";

                // SQL query to get all car brands
                String sql = "SELECT brand_id, brand_name FROM car_brands ORDER BY brand_name";

                // Connect to database using method from DBConnection
                try (Connection conn = DBConnection.getConnection();
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery()) {

                    System.out.println("Successfully connected to DB and executed query.");

                    // Loop through each brand from the database result.
                    while (rs.next()) {
                        int brandId = rs.getInt("brand_id");
                        String brandName = rs.getString("brand_name");

                        // use += so loop can append String as much as loop want
                        htmlOutput += "<label class='label cursor-pointer justify-start gap-3 p-0 hover:bg-gray-700/50 rounded-md px-2 py-1 transition-colors duration-200'>";
                        // Use DaisyUI classes for better styling and consistency
                        htmlOutput += "  <input type='checkbox' name='brand-checkbox' value='" + brandId
                                + "' class='checkbox checkbox-sm checkbox-primary'/>";
                        // Changed text-black to text-gray-200 to be visible on the dark filter panel
                        htmlOutput += "  <span class='label-text text-gray-200'>" + brandName + "</span>";
                        htmlOutput += "</label>";
                    }

                } catch (SQLException e) {
                    System.out.println("CarDataServlet Error: Error occurred in SQL connection scope");
                    e.printStackTrace();
                    System.out.println("Exiting CarDataServlet with Error");

                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                            "Error connecting to the database.");
                    return;
                }

                // Send the final HTML string back to the AJAX call.
                System.out.println("Sending HTML response to client.");
                // put htmlOutput into Buffer
                out.print(htmlOutput);

            } else if("getModelAsHtml".equals(action)) {

            } else {
                // If the action is something else, it's a bad request.
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
            }

            // Sent htmlOutput that in buffer back to web page as responseOption
            out.flush();

        } catch (Exception e) {
            System.out.println("CarDataServlet Error : A general error occurred.");
            e.printStackTrace();
            System.out.println("Exiting CarDataServlet with Error");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "A general error occurred.");
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
