/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.model;

import java.io.InputStream;
import java.sql.* ;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


/**
 *
 * @author ponlawatchangto
 */
public class announceItemDAO {

    public static List<announceItem> getAllAnnounceItems() throws Exception {
        List<announceItem> announceItemList = new ArrayList<>();
        // String sql = "SELECT * FROM announcementTable where startDate <= CURDATE() and endDate >= CURDATE()";
        String sql = "SELECT * FROM announcementTable";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                announceItem item = new announceItem();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                byte[] imgData = rs.getBytes("image");
                item.setImage(Base64.getEncoder().encodeToString(imgData));
                announceItemList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            announceItemList = new ArrayList<>();
        }
        return announceItemList;
    }

    public static boolean insertAnnounceItem(String name, String description, InputStream imageStream, String startDate, String endDate) {
        System.err.println("Entering announceItemDAO.insertAnnounceItem method");

        String sql = "INSERT INTO announcementTable (name, description, image, startDate, endDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // throw if imageStream is null
            if (imageStream == null) {
                System.out.println("From announceItemDAO: Image stream is null");
                throw new Exception("Image stream is null");
            }

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setBlob(3, imageStream);
            preparedStatement.setDate(4, Date.valueOf(LocalDate.parse(startDate)));
            if (endDate == null || endDate.isEmpty()) {
                preparedStatement.setNull(5, Types.DATE);
            } else {
                preparedStatement.setDate(5, Date.valueOf(LocalDate.parse(endDate)));
            }
            int rowAffected = preparedStatement.executeUpdate();
            System.out.println("announceItemDAO: Rows affected: " + rowAffected);
            System.err.println("Exiting announceItemDAO.insertAnnounceItem method");
            return rowAffected > 0;
        } catch (Exception e) {
            System.out.println("From announceItemDAO: Exception occurred");
            e.printStackTrace();
            System.err.println("Exiting announceItemDAO.insertAnnounceItem method with exception");
            return false;
        }
    }
}
