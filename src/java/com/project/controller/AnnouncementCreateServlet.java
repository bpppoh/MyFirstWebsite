package com.project.controller;

import com.project.model.announceItemDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;

/**
 * AnnouncementCreateServlet
 * Handles creation of new announcements with image upload functionality
 * 
 * @author ponlawatchangto
 */
@WebServlet(urlPatterns = { "/announcementInsert" })
@MultipartConfig
public class AnnouncementCreateServlet extends HttpServlet {

    private announceItemDAO announceDAO;

    @Override
    public void init() {
        announceDAO = new announceItemDAO();
    }

    /**
     * Processes announcement insertion requests for both GET and POST methods
     * 
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Entering AnnouncementCreateServlet");

        response.setContentType("text/html;charset=UTF-8");

        try {
            // Get form parameters
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            Part imagePart = request.getPart("image");

            // Validate required fields
            if (name == null || name.trim().isEmpty()) {
                System.out.println("Name is required");
                System.out.println("Exiting AnnouncementCreateServlet");
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=Name is required");
                return;
            }

            if (imagePart == null || imagePart.getSize() == 0) {
                System.out.println("Image is required");
                System.out.println("Exiting AnnouncementCreateServlet");
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=Image is required");
                return;
            }

            // Insert announcement
            boolean success = announceDAO.insertAnnounceItem(name, description,
                    imagePart.getInputStream(),
                    startDate, endDate);

            if (success) {
                // getServletContext() is applicationScope
                getServletContext().setAttribute("announcements", announceDAO.getAllAnnounceItems());

                // Redirect to index page to prevent form resubmission on refresh (PRG Pattern)
                System.out.println("Exiting AnnouncementCreateServlet");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                System.out.println("Failed to insert announcement");
                System.out.println("Exiting AnnouncementCreateServlet");
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=Failed to insert announcement");
            }

        } catch (Exception e) {
            System.out.println("Error occurred in AnnouncementCreateServlet");
            e.printStackTrace();
            System.out.println("Exiting AnnouncementCreateServlet");
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
