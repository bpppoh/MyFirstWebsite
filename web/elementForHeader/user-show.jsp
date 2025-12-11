<%--
    Document   : userShow
    Created on : Sep 17, 2025, 7:51:44 PM
    Author     : ponlawatchangto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />
<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%-- Determine classes based on admin status --%>
<c:set var="dropdownClass" value="bg-gray-800" />
<c:set var="usernameClass" value="" />
<c:if test="${requestScope.isAdmin}">
    <c:set var="dropdownClass" value="bg-[#f5b642]" />
    <c:set var="usernameClass" value="text-black" />
</c:if>

<div class="dropdown dropdown-end">

    <div tabindex="0" role="button" class="${dropdownClass} w-auto h-10 rounded-full flex justify-center items-center px-4 py-2 space-x-2 cursor-pointer transition-all duration-200 ease-in-out hover:scale-[103%]" id="user-dropdown">
        <img src="${pageContext.request.contextPath}/ImageForMainElement/Header/user.png" alt="user" class="h-full"/>
        <div id="username-display" class="${usernameClass} font-bold text-[20px] pr-2">
                <c:out value="${cookie.username.value}"/>
        </div>
        <img src="${pageContext.request.contextPath}/ImageForMainElement/Header/arrow-down-angle-svgrepo-com.svg" alt="arrow" class="h-[60%]" />
    </div>
    
    <form method="post" 
        action="${pageContext.request.contextPath}/LogOut" 
        id="logoutForm" 
        class="hidden">
    </form>

    <form action="${pageContext.request.contextPath}/admin/car-list.jsp"
        class="hidden"
        method="get"
        id="carListForm">
        <input type="hidden" name="page" value="1">
    </form>


    <ul tabindex="-1" 
        class="dropdown-content z-[1] menu p-2 shadow rounded-box mt-2 w-52 bg-gray-800">
        <li>
            <c:choose>
                <c:when test="${requestScope.isAdmin}">
                    <a>Status : Admin</a>
                </c:when>
                <c:when test="${requestScope.isMember}">
                    <a>Status : Member</a>
                </c:when>
                <c:otherwise>
                    <a>Status : Guest</a>
                </c:otherwise>
            </c:choose>
        </li>
        <li>
            <a class="text-red-500 hover:bg-red-500 hover:text-white" id="logOut">
                Log out
            </a>
        </li>

        <c:if test="${requestScope.isMember == true}">
            <li>
                <a href="${pageContext.request.contextPath}/member/car-form.jsp" class="hover:bg-gray-700">
                    Sell your car !
                </a>
            </li>
            
            <c:if test="${requestScope.isAdmin == true}">
                <li>
                    <a class="hover:bg-gray-700" id="showDatabase">
                        Show Database
                    </a>
                </li>
                
                <li>
                    <a href="${pageContext.request.contextPath}/admin/announcement-insert.jsp" class="hover:bg-gray-700">
                        Announcement
                    </a>
                </li>
            </c:if>
        </c:if>
    </ul>
    <script>
        $(document).ready(function() {
            // All dynamic display logic is now handled by JSTL on the server side.
    
            $("#logOut").click(function() {
                $("#logoutForm").submit();
            });
    
            $("#showDatabase").click(function() {
                $("#carListForm").submit();
            });
    
        });
    
    </script>
</div>


