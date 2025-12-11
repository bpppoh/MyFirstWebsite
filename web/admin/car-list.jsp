<%-- 
    Document   : showDataDB
    Created on : Sep 5, 2024, 1:09:03 PM
    Author     : ponlawatchangto
--%>
<%@page import="java.sql.*"%>
<%@page import="java.util.Base64"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Sansation:ital,wght@0,300;0,400;0,700;1,300;1,400;1,700&display=swap" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <!-- <script src="https://cdn.tailwindcss.com"></script> -->
        <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />
        <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    </head>
    <body class="font-['Sansation'] flex flex-col min-h-screen">
        <!-- Loader -->
        <div id="loader" class="fixed top-0 left-0 w-full h-full bg-black flex justify-center items-center z-50">
            <span class="loading loading-dots loading-xl text-white"></span>
        </div>

        <!-- Page Content (Initially Hidden) -->
        <div id="page-content" class="grow flex flex-col hidden">
            
            <jsp:include page="/header.jsp"/>
            <div class="grow my-12 min-h-screen">
                <div class="w-[90%] mx-auto flex flex-col mb-18" id="table-container">
                    <table class="w-full table-auto border-seperate border-spacing-x-4 border-spacing-y-4 mx-auto text-left text-Ro" id="table">
                        <thead class="bg-black text-white">
                            <tr class="">
                                <th class="w-[5%] px-6 py-3">ID</th>
                                <th class="w-[20%] px-6 py-3 hidden lg:block">Title</th>
                                <th class="w-[10%] px-6 py-3">Brand</th>
                                <th class="w-[10%] px-6 py-3">Model</th>
                                <th class="w-[25%] px-6 py-3 text-center hidden lg:table-cell">Picture</th>
                                <th class="w-[10%] px-6 py-3">Price</th>
                                <th class="w-[10%] px-6 py-3">Status</th>
                                <th class="w-[10%] px-6 py-3 text-center">Edit/Delete</th>
                            </tr>
                        </thead>
                        <tbody id="tbody">
                        </tbody>
                    </table>
                    <div class="join mx-auto mt-14">
                        <button class="join-item btn" id="BackButton">«</button>
                        <button class="join-item btn" id="PageNumber">Page <c:out value="${requestScope.page}"/></button>
                        <button class="join-item btn" id="ForwardButton">»</button>
                    </div>
                </div>
            </div>
            <jsp:include page="/footer.jsp"/>
        </div>

        <script>
            var currentPage = 1;
            var totalPages = 1;

            function updatePaginationUI() {
                $("#PageNumber").text("Page " + currentPage);
                $("#BackButton").prop('disabled', currentPage <= 1);
                $("#ForwardButton").prop('disabled', currentPage >= totalPages);
            }

            function fetchcar(pageNumber) {
                var colCount = $("#table thead th").length; 
                var loadingRow = "<tr><td colspan='" + colCount + "' class='text-center'><div class='loading loading-dots loading-xl mx-auto py-18'></div></td></tr>";
                $("#tbody").html(loadingRow);

                $.ajax({
                    url : "${pageContext.request.contextPath}/AJAXCarList",
                    data : { page : pageNumber },
                    type : "GET",
                    dataType : "html",
                    success: function (responseHtml, status, xhr) {
                        currentPage = parseInt(xhr.getResponseHeader("X-Current-Page"));
                        totalPages = parseInt(xhr.getResponseHeader("X-Total-Pages"));

                        $("#tbody").html(responseHtml);
                        
                        updatePaginationUI();
                    },
                    error: function(xhr, status, error) {
                        console.error("Error loading cars:", status, error);
                        var errorRow = "<tr><td colspan='" + colCount + "' class='text-center py-8 text-red-500'>Error occurred.</td></tr>";
                        $("#tbody").html(errorRow);
                    }
                });
            }

            $(document).ready(function() {
                var initialPage = ${requestScope.page != null ? requestScope.page : 1};
                fetchcar(initialPage);

                $("#BackButton").click(function() {
                    if (currentPage > 1) {
                        fetchcar(currentPage - 1);
                    }
                });

                $("#ForwardButton").click(function() {
                    if (currentPage < totalPages) {
                        fetchcar(currentPage + 1);
                    }
                });
                
            });
            $(window).on('load', function() {
                setTimeout(function() {
                    $('#loader').fadeOut('slow', function() {
                        $(this).remove();
                    });
                    $('#page-content').fadeIn('slow');
                }, 100);
            });
        </script>
    </body>
</html>
