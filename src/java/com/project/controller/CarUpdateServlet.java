/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.project.controller;

import java.io.IOException;
import java.io.InputStream;
import com.project.model.Car;
import com.project.model.CarDAO;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.project.model.SessionManager;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ponlawatchangto
 */
@MultipartConfig
@WebServlet(name = "CarUpdateServlet", urlPatterns = {"/CarUpdateServlet"})
public class CarUpdateServlet extends HttpServlet {

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
        System.out.println("Entering CarUpdateServlet (POST)");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Entering CarUpdateServlet (POST)");

        // Create session
        HttpSession session = request.getSession();
        // Check id in sessionScope
        if(SessionManager.isSessionEmpty(session) || SessionManager.isAttributeEmpty(session, "id")) {
            System.out.println("Session got Expired or User not logged in - redirecting to login page");
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please Login");
            return;
        }

        try {
            Car car = new Car();
            // Set title and color first
            car.setTitle(request.getParameter("title"));
            car.setColor(request.getParameter("color"));

            // Parse all integer and float values in a single try-catch
            try {
                car.setId(Integer.parseInt(request.getParameter("id")));
                car.setBrandId(Integer.parseInt(request.getParameter("brand_id")));
                car.setModelId(Integer.parseInt(request.getParameter("model_id")));
                car.setSubModelId(Integer.parseInt(request.getParameter("sub_model_id")));
                car.setYear(Integer.parseInt(request.getParameter("year")));
                car.setBodyTypeId(Integer.parseInt(request.getParameter("body_type_id")));
                car.setMileage(Integer.parseInt(request.getParameter("mileage")));
                car.setPrice(Float.parseFloat(request.getParameter("price")));
                car.setGearId(Integer.parseInt(request.getParameter("gear_id")));
                car.setFuelId(Integer.parseInt(request.getParameter("fuel_id")));
                car.setEngineDisplacement(Integer.parseInt(request.getParameter("engine_displacement")));
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format in form submission: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=Invalid data format for one of the fields.");
                return;
            }

            Part filePart = request.getPart("car_main_pic");
            InputStream inputStream = null;
            boolean isPictureUpdated = false;

            if (filePart != null && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty() && filePart.getSize() > 0) {
                System.out.println("New picture uploaded: " + filePart.getSubmittedFileName());
                inputStream = filePart.getInputStream();
                isPictureUpdated = true;
            } else {
                System.out.println("No new picture uploaded. Updating details only.");
            }

            boolean updateSuccess;
            if (isPictureUpdated) {
                updateSuccess = CarDAO.updateCarWithPicture(car, inputStream);
            } else {
                updateSuccess = CarDAO.updateCar(car);
            }

            if (updateSuccess) {
                System.out.println("Car updated successfully");
                response.sendRedirect(request.getContextPath() + "/carInfomation.jsp?id=" + car.getId());
            } else {
                System.out.println("Car update failed in DAO");
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=Failed to update car information in the database.");
            }

        } catch (Exception e) {
            System.err.println("An error occurred in CarUpdateServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=" + e.getMessage());
        }
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
