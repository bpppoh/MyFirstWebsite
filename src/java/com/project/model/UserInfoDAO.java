/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.model;

import java.sql.*;

/**
 *
 * @author ponlawatchangto
 */
public class UserInfoDAO {

    public static UserInfo checkUser(String username, String password) {
        UserInfo userInfo = null;
        String sql = "select id, username, email, tier, phone from users where username = ? and password = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    userInfo = new UserInfo();
                    userInfo.setID(rs.getInt("id"));
                    userInfo.setUsername(rs.getString("username"));
                    userInfo.setEmail(rs.getString("email"));
                    userInfo.setTier(rs.getString("tier"));
                    userInfo.setPhone(rs.getString("phone"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public static boolean insertUser(String username, String password, String email, String phone) {
        boolean success = false;
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("insert into users(username,password,email,phone) values (?,?,?,?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            int rs = pstmt.executeUpdate();
            if (rs > 0) {
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public static UserInfo getUserByID(int id) throws SQLException, Exception {
        UserInfo userInfo = null;
        String sql = "select id, username, email, tier, phone from users where id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    userInfo = new UserInfo();
                    userInfo.setID(rs.getInt("id"));
                    userInfo.setUsername(rs.getString("username"));
                    userInfo.setEmail(rs.getString("email"));
                    userInfo.setTier(rs.getString("tier"));
                    userInfo.setPhone(rs.getString("phone"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public static String getTierByID (int id) {
        String tier = null;
        String sql = "select tier from users where id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    tier = rs.getString("tier");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tier;
    }

    public static String getUsernameByID(int id) {
        String username = null;
        String sql = "select username from users where id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    username = rs.getString("username");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return username;
    }
}