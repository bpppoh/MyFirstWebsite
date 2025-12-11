<%-- 
    Document   : errorPage
    Created on : Sep 11, 2025, 1:51:09 PM
    Author     : ponlawatchangto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WheelToGo : Error</title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>
    <body class="font-['Sansation']">
        <jsp:include page="header.jsp"/>
        <div class="flex flex-col mb-18 space-y-12">
            <div class="text-[48px] font-bold">
                Error
            </div>
            <div class="text-slate-700 lowercase">
                <c:if test="${not empty param.error}">
                    <c:out value="${param.error}"/>
                </c:if>
                <c:if test="${empty param.error}">
                    cannot load error message .
                </c:if>
            </div>
        </div>
    </body>
</html>
