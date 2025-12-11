/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author ponlawatchangto
 */
public class visitor_counterDAO {

    public static boolean logVisit(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO userLog(username, type) VALUES (?, 'login')";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            int rs = pstmt.executeUpdate();
            if (rs > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static visitor_counter getTotalVisit() throws Exception {
        visitor_counter vc = new visitor_counter();
        vc.setCounterName("total_visitor_count");
        vc.setVisitor_count(0);
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM userLog WHERE type = 'login'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                vc.setVisitor_count(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vc;
    }

    public static visitor_counter getTodayVisit() throws Exception {
        visitor_counter vc = new visitor_counter();
        vc.setCounterName("today_visitor_count");
        vc.setVisitor_count(0);
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM userLog WHERE DATE(timeStamp) = CURDATE() AND type = 'login'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                vc.setVisitor_count(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vc;
    }

    public static visitor_counter getMonthVisit() throws Exception {
        visitor_counter vc = new visitor_counter();
        vc.setCounterName("month_visitor_count");
        vc.setVisitor_count(0);
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM userLog WHERE MONTH(timeStamp) = MONTH(CURDATE()) AND YEAR(timeStamp) = YEAR(CURDATE()) AND type = 'login'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                vc.setVisitor_count(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vc;
    }

    public static visitor_counter getYearVisit() throws Exception {
        visitor_counter vc = new visitor_counter();
        vc.setCounterName("year_visitor_count");
        vc.setVisitor_count(0);
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM userLog WHERE YEAR(timeStamp) = YEAR(CURDATE()) AND type = 'login'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                vc.setVisitor_count(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vc;
    }

    public static List<visitor_counter> getAllVisitorCounts() throws Exception {
        List<visitor_counter> vcList = new ArrayList<>();
        vcList.add(getTodayVisit());
        vcList.add(getMonthVisit());
        vcList.add(getYearVisit());
        vcList.add(getTotalVisit());
        return vcList;
    }
}
