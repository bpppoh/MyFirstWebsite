package com.project.controller;

import com.project.model.Car;
import com.project.model.CarDAO;
import com.project.model.UserInfo;
import com.project.model.UserInfoDAO;
import com.project.model.SessionManager;
import com.project.model.SidePicDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;

/**
 * CarUploadServlet
 * Handles car information upload with image upload functionality
 * 
 * @author ponlawatchangto
 */
@WebServlet(urlPatterns = { "/UploadServlet" })
@MultipartConfig
public class CarUploadServlet extends HttpServlet {


    @Override
    public void init() {
        System.out.println("Initializing CarUploadServlet");
    }

    /**
     * Processes car upload requests for both GET and POST methods
     * 
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Entering CarUploadServlet");

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Use HttpSession from request.getSession() to get session information
        HttpSession session = request.getSession();

        // Check session and id attribute in session
        // if session is null or id attribute is missing, redirect to login page
        if (SessionManager.isSessionEmpty(session) || SessionManager.isAttributeEmpty(session, "id")) {
            System.out.println("Session got Expired or User not logged in - redirecting to login page");
            System.out.println("Exiting CarUploadServlet");
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please Login");
            return;
        }

        // Get uploaded file
        Part filePart = request.getPart("car_main_pic");
        if (filePart == null || filePart.getSize() == 0) {
            System.out.println("No file selected or file is empty");
            System.out.println("Exiting CarUploadServlet");
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=No File Selected");
            return;
        }

        try {
            // Session is surely not null here
            // Get session's id attribute
            Object idAttr = session.getAttribute("id");
            if (idAttr == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please Login");
                return;
            }
            // Sure that idAttr is not null here
            // Get int of userID
            int userID;
            if (idAttr instanceof Integer) {
                userID = (Integer) idAttr;
            } else {
                try {
                    userID = Integer.parseInt(idAttr.toString());
                } catch (Exception e)  {
                    System.out.println("Error occurred in CarUploadServlet.processRequest : while parsing user ID from session");
                    response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please Login");
                    return;
                }
            }
            // Sure that userID is int and valid here
            // Get username from userID
            UserInfo user = UserInfoDAO.getUserByID(userID);
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=User+not+found");
                return;
            }
            // Sure that user is not null here
            // Get username from user object
            String creator = user.getUsername();
            if(creator == null || creator.trim().isEmpty()) {
                System.out.println("Username is empty for user ID: " + userID);
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=Invalid+User");
                return;
            }

            // Create car object and populate with form data
            Car car = new Car();
            car.setTitle(request.getParameter("title"));

            // Beware of Parsing String to int/float here
            // So need to try-catch 
            try {
                car.setBrandId(Integer.parseInt(request.getParameter("brand")));
                car.setModelId(Integer.parseInt(request.getParameter("model")));
                car.setSubModelId(Integer.parseInt(request.getParameter("sub_model")));
                car.setYear(Integer.parseInt(request.getParameter("year")));
                car.setMileage(Integer.parseInt(request.getParameter("mileage")));
                car.setPrice(Float.parseFloat(request.getParameter("price")));
                car.setEngineDisplacement(Integer.parseInt(request.getParameter("engine_displacement")));
                car.setBodyTypeId(Integer.parseInt(request.getParameter("body_type")));
                car.setGearId(Integer.parseInt(request.getParameter("gear")));
                car.setFuelId(Integer.parseInt(request.getParameter("fuel_type")));
            } catch (NumberFormatException e) {
                System.err.println("Error occurred in CarUploadServlet.processRequest : while parsing number from form data");
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=Invalid number format");
                return;
            }

            car.setColor(request.getParameter("color"));

            // Insert car into database via CarDAO.insertCar()
            int generatedId = CarDAO.insertCar(car, filePart.getInputStream(), userID);

            // Check if CarDAO.insertCar() succeed
            if (generatedId > 0) {
                System.out.println("Car uploaded successfully with ID: " + generatedId);

                // Handle additional images
                int successfulUploads = 0;
                int attemptedUploads = 0;
                for (Part part : request.getParts()) {
                    if ("additional_images".equals(part.getName()) && part.getSize() > 0) {
                        attemptedUploads++;
                        System.out.println("Found additional image part, size: " + part.getSize());
                        try {
                            boolean success = SidePicDAO.insertSidePic(generatedId, part.getInputStream());
                            if (success) {
                                successfulUploads++;
                                System.out.println("Successfully inserted additional image for car ID: " + generatedId);
                            } else {
                                System.err.println("DAO returned false for additional image upload for car ID: " + generatedId);
                            }
                        } catch (Exception e) {
                            System.err.println("Failed to upload an additional image for car ID: " + generatedId);
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("Attempted to upload " + attemptedUploads + " additional images. Successfully uploaded: " + successfulUploads);

                response.sendRedirect(request.getContextPath() + "/carInfomation.jsp?id=" + generatedId);
            } else {
                System.out.println("Failed to upload car");
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp?error=Failed to upload car");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error processing car upload: " + e.getMessage());
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
        return "UploadServlet - Handles car information upload with image";
    }
}
