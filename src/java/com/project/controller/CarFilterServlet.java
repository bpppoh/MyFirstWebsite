/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.project.controller;

import com.project.model.DBConnection;
import com.project.model.SessionManager;
import com.project.model.UserInfo;
import com.project.model.UserInfoDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;

/**
 *
 * @author ponlawatchangto
 */
@WebServlet(name = "CarFilterServlet", urlPatterns = { "/CarFilterServlet" })
public class CarFilterServlet extends HttpServlet {

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

        System.out.println("Entering CarFilterServlet");
        response.setContentType("text/html;charset=UTF-8");

        String[] brandIds = request.getParameterValues("brandIds");
        String pageParam = request.getParameter("page");
        
        int currentPage = 1;
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                System.err.println("Invalid page number format: " + pageParam);
            }
        }

        if(request.getAttribute("isAdmin") == null) {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("id") != null) {
                int id = (int) session.getAttribute("id");
                if(UserInfoDAO.getTierByID(id).equals("Admin")){
                    request.setAttribute("isAdmin", true);
                } else {
                    request.setAttribute("isAdmin", false);
                }
            } else {
                request.setAttribute("isAdmin", false);
            }
        }

        String htmlOutput = "";
        int pageSize = 6; // 6 cars per page

        try (Connection conn = DBConnection.getConnection();) {
            int totalItems = com.project.model.CarDAO.getCarCountByBrands(brandIds);
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);
            int offset = (currentPage - 1) * pageSize;

            String sql = "SELECT c.id, c.title, c.year, c.mileage, c.price, c.car_main_pic, " +
                         "b.brand_name, m.model_name, sm.sub_model_name " +
                         "FROM carTable c " +
                         "LEFT JOIN car_brands b ON c.brand_id = b.brand_id " +
                         "LEFT JOIN car_models m ON c.model_id = m.model_id " +
                         "LEFT JOIN car_sub_models sm ON c.sub_model_id = sm.sub_model_id " +
                         "WHERE c.status = 'Available' ";

            if (brandIds != null && brandIds.length > 0) {
                sql += "AND c.brand_id IN (";
                for (int i = 0; i < brandIds.length; i++) {
                    sql += "?";
                    if (i < brandIds.length - 1) {
                        sql += ",";
                    }
                }
                sql += ")";
            }

            sql += " ORDER BY c.id DESC LIMIT ? OFFSET ?";

            System.out.println("Executing SQL: " + sql);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            int paramIndex = 1;
            if (brandIds != null && brandIds.length > 0) {
                for (String brandId : brandIds) {
                    pstmt.setInt(paramIndex++, Integer.parseInt(brandId));
                }
            }
            pstmt.setInt(paramIndex++, pageSize);
            pstmt.setInt(paramIndex, offset);

            ResultSet rs = pstmt.executeQuery();

            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;

                byte[] imgBytes = rs.getBytes("car_main_pic");
                String carMainPic = "";
                if (imgBytes != null) {
                    carMainPic = Base64.getEncoder().encodeToString(imgBytes);
                }

                htmlOutput += "<div class='w-full h-fit bg-white rounded-3xl shadow-sm flex flex-col overflow-hidden transition-all duration-300 ease-in-out hover:-translate-y-[5px] hover:shadow-lg'>";
                htmlOutput += "<img src='data:image/jpeg;base64," + carMainPic
                        + "' alt='Car Image' class='w-full h-52 md:h-60 object-cover' />";
                htmlOutput += "<div class='p-4 sm:p-6 flex flex-col gap-3 sm:gap-4 flex-grow'>";
                htmlOutput += "<div class='flex-grow'>";
                htmlOutput += "<h2 class='text-lg md:text-xl font-bold text-black truncate' title='"
                        + rs.getString("title") + "'>" + rs.getString("title") + "</h2>";
                htmlOutput += "<p class='font-semibold text-gray-700 text-base'>" + rs.getString("brand_name") + " "
                        + rs.getString("model_name") + " " + rs.getString("sub_model_name") + "</p>";
                htmlOutput += "<div class='flex flex-col space-y-1 text-sm md:text-base text-gray-600 mt-2'>";
                htmlOutput += "<div class='flex items-center gap-2'>";
                htmlOutput += "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 20 20' fill='currentColor' class='w-5 h-5 text-gray-500'><path fill-rule='evenodd' d='M5.75 2a.75.75 0 0 1 .75.75V4h7V2.75a.75.75 0 0 1 1.5 0V4h.25A2.75 2.75 0 0 1 18 6.75v8.5A2.75 2.75 0 0 1 15.25 18H4.75A2.75 2.75 0 0 1 2 15.25v-8.5A2.75 2.75 0 0 1 4.75 4H5V2.75A.75.75 0 0 1 5.75 2Zm-1 5.5a.75.75 0 0 0 0 1.5h10.5a.75.75 0 0 0 0-1.5H4.75Z' clip-rule='evenodd' /></svg>";
                htmlOutput += "<span>Year: " + rs.getInt("year") + "</span>";
                htmlOutput += "</div>";
                htmlOutput += "<div class='flex items-center gap-2'>";
                htmlOutput += "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 20 20' fill='currentColor' class='w-5 h-5 text-gray-500'><path d='M4.5 2.5a.5.5 0 0 0 0 1h11a.5.5 0 0 0 0-1h-11ZM5 6.125a.75.75 0 0 1 .75-.75h8.5a.75.75 0 0 1 0 1.5h-8.5a.75.75 0 0 1-.75-.75Zm2.5 3.375a.75.75 0 0 1 .75-.75h3.5a.75.75 0 0 1 0 1.5h-3.5a.75.75 0 0 1-.75-.75Zm-2.5 3.375a.75.75 0 0 1 .75-.75h8.5a.75.75 0 0 1 0 1.5h-8.5a.75.75 0 0 1-.75-.75Z' /><path fill-rule='evenodd' d='M3.954 1.71A2.5 2.5 0 0 0 1.5 4.104V15.5A2.5 2.5 0 0 0 4 18h12a2.5 2.5 0 0 0 2.5-2.5V4.104A2.5 2.5 0 0 0 16.046 1.71l-.22-1.104a.75.75 0 0 0-1.458-.292L14.25 1.5H5.75l-.118-1.186a.75.75 0 0 0-1.458.292l-.22 1.104ZM4 3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 .5.5v1h-12v-1Z' clip-rule='evenodd' /></svg>";
                htmlOutput += "<span>Mileage: " + rs.getInt("mileage") + " km</span>";
                htmlOutput += "</div>";
                htmlOutput += "</div>";
                htmlOutput += "</div>";
                htmlOutput += "<div class='border-t border-gray-200 pt-4 mt-4 flex justify-between items-center'>";
                htmlOutput += "<span class='text-xl font-bold text-blue-600'>$" + rs.getInt("price") + "</span>";
                htmlOutput += "<div class='flex items-center gap-2'>";
                if (request.getAttribute("isAdmin") != null && request.getAttribute("isAdmin").equals(true)) {
                    htmlOutput += "<form action='" + request.getContextPath()
                            + "/CarEditFormServlet' method='post' class='m-0'>";
                    htmlOutput += "<input type='hidden' name='id' value='" + rs.getInt("id") + "'>";
                    htmlOutput += "<button type='submit' class='px-4 py-2 text-sm font-semibold bg-yellow-500 rounded-lg text-white transition-all duration-300 ease-in-out hover:bg-yellow-600 hover:scale-105 hover:shadow-lg cursor-pointer'>Edit</button>";
                    htmlOutput += "</form>";
                }
                htmlOutput += "<form action='" + request.getContextPath()
                        + "/carInfomation.jsp' method='get' class='m-0'>";
                htmlOutput += "<input type='hidden' name='id' value='" + rs.getInt("id") + "'>";
                htmlOutput += "<button type='submit' class='px-4 py-2 text-sm font-semibold bg-blue-600 text-white rounded-lg transition-all duration-300 ease-in-out hover:bg-blue-700 hover:scale-105 hover:shadow-lg cursor-pointer'>View more</button>";
                htmlOutput += "</form>";
                htmlOutput += "</div>";
                htmlOutput += "</div>";
                htmlOutput += "</div>";
                htmlOutput += "</div>";
            }

            if (!hasResults) {
                htmlOutput = "<div class='col-span-full text-center p-12 text-lg text-gray-300'>No cars found matching your criteria.</div>";
            }

            response.setHeader("X-Current-Page", String.valueOf(currentPage));
            response.setHeader("X-Total-Pages", String.valueOf(totalPages));

            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("CarFilterServlet Error: Error occurred in SQL connection scope");
            e.printStackTrace();
            htmlOutput = "<div class='col-span-full text-center p-12 text-red-500'>An error occurred while fetching data from DB.</div>";
        } catch (Exception e) {
            System.out.println("CarFilterServlet Error : A general error occurred.");
            e.printStackTrace();
            htmlOutput = "<div class='col-span-full text-center p-12 text-red-500'>An unexpected error occurred.</div>";
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(htmlOutput);
            out.flush();
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
