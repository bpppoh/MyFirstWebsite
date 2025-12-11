/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.model;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


/**
 *
 * @author ponlawatchangto
 */
public class SidePicDAO {
    
    public static boolean insertSidePic(int carId, InputStream inputStream) {
        String query = "INSERT INTO car_side_pics (car_id, picture) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, carId);
            pstmt.setBlob(2, inputStream);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<SidePic> getSidePicsByCarId(int carId) {
        System.out.println("Entering SidePicDAO.getSidePicsByCarId");
        List<SidePic> sidePics = new ArrayList<>();
        String query = "SELECT id, car_id, picture FROM car_side_pics WHERE car_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, carId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    SidePic sidePic = new SidePic();
                    sidePic.setPictureId(rs.getInt("id"));
                    sidePic.setCarId(rs.getInt("car_id"));
                    byte[] imgData = rs.getBytes("picture");
                    if (imgData != null) {
                        sidePic.setPicture(Base64.getEncoder().encodeToString(imgData));
                    }
                    sidePics.add(sidePic);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Exiting SidePicDAO.getSidePicsByCarId");
        return sidePics;
    }

    public static boolean deleteSidePic(int pictureId) {
        String query = "DELETE FROM car_side_pics WHERE picture_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, pictureId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
