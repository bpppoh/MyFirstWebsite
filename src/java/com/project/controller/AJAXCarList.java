/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.project.controller;

import com.project.model.Car;
import com.project.model.CarDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ponlawatchangto
 */
@WebServlet(name = "AJAXCarList", urlPatterns = {"/AJAXCarList"})
public class AJAXCarList extends HttpServlet {

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

        int totalCars = CarDAO.getCarAmount();
        int recordsPerPage = 6;
        int totalPages = (int) Math.ceil(totalCars / (double) recordsPerPage);

        String pageStr = request.getParameter("page");
        int currentPage = 1;
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                int requestedPage = Integer.parseInt(pageStr);
                if (requestedPage > 0 && requestedPage <= totalPages) {
                    currentPage = requestedPage;
                } else if (requestedPage > totalPages) {
                    currentPage = totalPages;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        int offset = (currentPage - 1) * recordsPerPage;
        List<Car> carList = CarDAO.getCarEachPage(offset);
        
        response.addHeader("X-Current-Page", String.valueOf(currentPage));
        response.addHeader("X-Total-Pages", String.valueOf(totalPages));
        
        try (PrintWriter out = response.getWriter()) {
            if (carList == null || carList.isEmpty()) {
                out.print("<tr><td colspan='8' class='text-center py-8 text-gray-500'>No cars available at the moment.</td></tr>");
            } else {
                for (Car car : carList) {
                    out.print("<tr class='odd:bg-white even:bg-gray-50 border-b border-gray-200'>");
                    out.print("    <td class='px-6 py-3'>" + car.getId() + "</td>");
                    out.print("    <td class='px-6 py-3 hidden lg:table-cell'>" + car.getTitle() + "</td>");
                    out.print("    <td class='px-6 py-3'>" + car.getBrandName() + "</td>");
                    out.print("    <td class='px-6 py-3'>" + car.getModelName() + "</td>");
                    out.print("    <td class='px-6 py-3 ...'>");
                    if (car.getCarMainPic() != null && !car.getCarMainPic().isEmpty()) {
                        out.print("        <img src='data:image/jpeg;base64," + car.getCarMainPic() + "' alt='Car Image' class='w-full block mx-auto p-3 rounded-4xl'>");
                    } else {
                        out.print("        <div class='flex items-center justify-center h-full text-gray-400 text-sm'>No Image</div>");
                    }
                    out.print("    </td>");
                    out.print("    <td class='px-6 py-3'>" + car.getPrice() + "</td>");
                    out.print("    <td class='px-6 py-3'>" + car.getStatus() + "</td>");
                    out.print("    <td class='align-center text-center'>");
                    out.print("        <form method='post' action='" + request.getContextPath() + "/DeleteItemFromTable' class='inline-block'>");
                    out.print("            <input type='hidden' name='id' value='" + car.getId() + "'>");
                    out.print("            <button type='submit' class='py-2 px-4 bg-red-500 font-bold text-white rounded-2xl cursor-pointer'>X</button>");
                    out.print("        </form>");
                    out.print("        <form method='post' action='" + request.getContextPath() + "/CarEditFormServlet' class='inline-block'>");
                    out.print("            <input type='hidden' name='id' value='" + car.getId() + "'>");
                    out.print("            <button type='submit' class='py-2 px-4 bg-blue-500 font-bold text-white rounded-2xl cursor-pointer'>Edit</button>");
                    out.print("        </form>");
                    out.print("    </td>");
                    out.print("</tr>");
                }
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
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
