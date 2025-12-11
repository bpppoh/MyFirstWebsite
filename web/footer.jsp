<%-- 
    Document   : footer
    Created on : Sep 26, 2025, 10:22:24 AM
    Author     : ponlawatchangto
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.project.model.visitor_counter"%>
<%@page import="com.project.model.visitor_counterDAO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div>
    <footer class="bg-black text-white py-6 font-['Sansation'] select-none w-screen">
        <div class="container mx-auto px-6 flex flex-col md:flex-row justify-between items-center">
            <div class="text-sm mb-4 md:mb-0">&copy; 2025 WheelToGo. All rights reserved.</div>
            <div class="flex flex-col space-y-2 items-start ">
                <div class="text-sm" id="totalVisitors">
                    Total Visitors: 
                    <c:choose>
                        <c:when test="${not empty applicationScope.total_visitor_count}">
                            <c:out value="${applicationScope.total_visitor_count}"/>
                        </c:when>
                        <c:otherwise>0 (Loading...)</c:otherwise>
                    </c:choose>
                </div>
                <div class="text-sm" id="todayVisitors">
                    Today's Visitors: 
                    <c:choose>
                        <c:when test="${not empty applicationScope.today_visitor_count}">
                            <c:out value="${applicationScope.today_visitor_count}"/>
                        </c:when>
                        <c:otherwise>0 (Loading...)</c:otherwise>
                    </c:choose>
                </div>
                <div class="text-sm" id="monthVisitors">
                    This Month's Visitors: 
                    <c:choose>
                        <c:when test="${not empty applicationScope.month_visitor_count}">
                            <c:out value="${applicationScope.month_visitor_count}"/>
                        </c:when>
                        <c:otherwise>0 (Loading...)</c:otherwise>
                    </c:choose>
                </div>
                <div class="text-sm" id="yearVisitors">
                    This Year's Visitors: 
                    <c:choose>
                        <c:when test="${not empty applicationScope.year_visitor_count}">
                            <c:out value="${applicationScope.year_visitor_count}"/>
                        </c:when>
                        <c:otherwise>0 (Loading...)</c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </footer>
</div>
