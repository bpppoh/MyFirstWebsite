/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.model;

import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ponlawatchangto
 */
public class SessionManager {
    private int anHour = 60 * 60; // 1 hour in seconds
    private int aDay = 24 * anHour; // 1 day in seconds

    public SessionManager() {
    }

    public static void destroySession(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

    public static boolean isSessionEmpty(HttpSession session) {
        try {
            return (session == null);
        } catch (Exception e) {
            System.out.println("Error occurred in SessionManager.isSessionEmpty");
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isAttributeEmpty(HttpSession session, String attributeName) {
        try {
            if (isSessionEmpty(session)){
                return true;
            }
            Object attribute = session.getAttribute(attributeName);
            return (attribute == null || attribute.toString().isEmpty());
        } catch (Exception e) {
            System.out.println("Error occurred in SessionManager.isAttributeEmpty");
            e.printStackTrace();
            return true;
        }
    }

    public static boolean setAttribute(HttpSession session, String attributeName, Object value) {
        try {
            if (isSessionEmpty(session)){
                return false;
            }
            session.setAttribute(attributeName, value);
            session.setMaxInactiveInterval(60 * 30); // 30 minutes
            return true;
        } catch (Exception e) {
            System.out.println("SessionManager.setAttribute : Error occurred while setting attribute");
            e.printStackTrace();
            return false;
        }
    }
    
}
