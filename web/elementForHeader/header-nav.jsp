<%-- 
    Document   : HeaderNav
    Created on : Sep 17, 2025, 9:06:26 PM
    Author     : ponlawatchangto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel='stylesheet' href='https://cdn-uicons.flaticon.com/3.0.0/uicons-solid-rounded/css/uicons-solid-rounded.css'>
<li class="transition-all duration-200 ease-in-out hover:text-yellow-500 hover:scale-105 hover:-translate-y-0.5 cursor-pointer">
    <a href="${pageContext.request.contextPath}/index.jsp" class=""><i class="fi fi-sr-home mr-1"></i>Home</a>
</li>
<li class="transition-all duration-200 ease-in-out hover:text-red-400 hover:scale-105 hover:-translate-y-0.5 cursor-pointer">
    <a href="${pageContext.request.contextPath}/browse-cars.jsp"><i class="fi fi-sr-search mr-1"></i>
        Browsing</a>
</li>
<li class="transition-all duration-200 ease-in-out hover:text-green-500 hover:scale-105 hover:-translate-y-0.5 cursor-pointer">
    <a href="#"><i class="fi fi-sr-heart mr-1"></i>About us</a>
</li>
