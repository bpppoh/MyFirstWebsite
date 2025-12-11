package com.project.controller;

import com.project.model.CarDAO;
import com.project.model.SessionManager;
import com.project.model.UserInfoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * CarDeleteServlet
 * Handles deletion of car items from database (Admin only functionality)
 * 
 * @author ponlawatchangto
 */
@WebServlet(urlPatterns = { "/DeleteItemFromTable" })
public class CarDeleteServlet extends HttpServlet {

    private CarDAO carDAO;
    private UserInfoDAO userInfoDAO;

    @Override
    public void init() {
        System.out.println("Initializing CarDeleteServlet...");
        carDAO = new CarDAO();
        userInfoDAO = new UserInfoDAO();
    }

    /**
     * Processes car deletion requests for both GET and POST methods
     * Only admin users are allowed to delete cars
     * 
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Entering CarDeleteServlet");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        try {
            // Check if user is logged in by checking session attribute "id"
            if (SessionManager.isAttributeEmpty(session, "id")) {
                System.out.println("User not logged in - redirecting to login page");
                System.out.println("Exiting CarDeleteServlet");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Check if user is admin
            int userId = (int) session.getAttribute("id");
            String userTier = userInfoDAO.getTierByID(userId);

            if (!"Admin".equals(userTier)) {
                System.out.println("Non-admin user attempted to delete car - access denied");
                System.out.println("Exiting CarDeleteServlet");
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=Access denied - Admin only");
                return;
            }

            // Get car ID to delete
            String carIdParam = request.getParameter("id");
            if (carIdParam == null || carIdParam.trim().isEmpty()) {
                System.out.println("Car ID is required");
                System.out.println("Exiting CarDeleteServlet");
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=Car ID is required");
                return;
            }

            int carId;
            try {
                carId = Integer.parseInt(carIdParam);
            } catch (NumberFormatException e) {
                System.out.println("Invalid car ID format");
                System.out.println("Exiting CarDeleteServlet");
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=Invalid car ID format");
                return;
            }

            // Delete the car
            boolean success = carDAO.deleteCar(carId);

            if (success) {
                System.out.println("Car with ID " + carId + " deleted successfully");
                System.out.println("Exiting CarDeleteServlet");
                response.sendRedirect(request.getContextPath() + "/admin/car-list.jsp");
            } else {
                System.out.println("Failed to delete car with ID " + carId);
                System.out.println("Exiting CarDeleteServlet");
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=Failed to delete car");
            }

        } catch (Exception e) {
            System.out.println("Error occurred in CarDeleteServlet");
            e.printStackTrace();
            System.out.println("Exiting CarDeleteServlet");
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=" + e.getMessage());
        }
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
        return "DeleteItemFromTable Servlet - Handles car deletion (Admin only)";
    }
}
