/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.model;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ponlawatchangto
 */
public class CookieManager {

    private static int time = 60 * 30; // 30 minutes

    public CookieManager() {
    }

    public static boolean deleteAllCookies(HttpServletResponse response, Cookie[] cookies) {
        try {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    Cookie toDelete = new Cookie(cookie.getName(), "");
                    toDelete.setMaxAge(0);
                    response.addCookie(toDelete);
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error occurred in CookieManager.deleteAllCookies");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean setCookie(HttpServletResponse response, String name, String value) {
        try {
            Cookie cookie = new Cookie(name, value);
            cookie.setMaxAge(time);
            response.addCookie(cookie);
            return true;
        } catch (Exception e) {
            System.out.println("Error occurred in CookieManager.setCookie");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteCookie(HttpServletResponse response, String name) {
        try {
            Cookie cookie = new Cookie(name, "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return true;
        } catch (Exception e) {
            System.out.println("Error occurred in CookieManager.deleteCookie");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkCookieExist(Cookie[] cookies, String name) {
        if (cookies == null || cookies.length == 0) {
            return false;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
