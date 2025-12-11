<%-- 
    Document   : reserveSuccess
    Created on : Nov 6, 2025, 1:19:21 AM
    Author     : ponlawatchangto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WheelToGo : Reservation Success</title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
        <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />
        <script src="https://cdn.tailwindcss.com"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <!-- Vue.js CDN -->
        <script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
    </head>
    <body class="bg-[#ffffff] min-h-screen font-['Sansation'] flex flex-col bg-[url('https://assets.qz.com/media/8829c0e55f0522cea7b589fec420db88.jpg')] bg-cover bg-center bg-no-repeat bg-fixed">
        <!-- Loader -->
        <div id="loader" class="fixed top-0 left-0 w-full h-full bg-black flex justify-center items-center z-50">
            <span class="loading loading-dots loading-xl text-white"></span>
        </div>

        <!-- Page Content (Initially Hidden) -->
        <div id="page-content" style="display: none;" class="flex flex-col min-h-screen bg-black bg-opacity-80 text-white">
            <jsp:include page="header.jsp" />
            
            <c:set var="escapedReservationId">
                <c:out value="${requestScope.reservationId}"/>
            </c:set>
            <c:set var="escapedCarName">
                <c:out value="${requestScope.carName}"/>
            </c:set>

            <!-- Vue App Root -->
            <div id="app" class="hero min-h-screen">
                <div class="hero-content text-center">
                    <div class="max-w-md">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12 mx-auto text-success" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                        <h1 class="text-5xl font-bold mt-4">{{ title }}</h1>
                        <p class="py-6">{{ message }}</p>
                        <p v-if="reservation.id" class="pb-6">Your Reservation ID: <strong>{{ reservation.id }}</strong></p>
                        <a href="${pageContext.request.contextPath}/browse-cars.jsp" class="btn btn-primary text-white">Go to Browse Cars</a>
                    </div>
                </div>
            </div>

            <jsp:include page="footer.jsp" />
        </div>

        <script>
            // Inject data from JSP into a JavaScript variable
            const reservationData = {
                id: "${escapedReservationId}",
                carName: "${escapedCarName}"
            };

            // Initialize Vue
            new Vue({
                el: '#app',
                data: {
                    title: 'Reserved Successfully!',
                    message: 'Your car has been successfully reserved. You will get a call from our staff shortly.',
                    reservation: {
                        id: null,
                        carName: null
                    }
                },
                mounted() {
                    // Use the data injected from JSP
                    if (reservationData.id) {
                        this.reservation.id = reservationData.id;
                    }
                    if (reservationData.carName) {
                        // You can use carName if needed, for example:
                        // this.message = 'Your ' + reservationData.carName + ' has been successfully reserved...';
                    }
                }
            });

            // Existing loader script
            $(window).on('load', function() {
                setTimeout(function() {
                    $('#loader').fadeOut('slow');
                    $('#page-content').fadeIn('slow');
                }, 100);
            });
        </script>
    </body>
</html>