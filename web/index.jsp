<%-- 
    Document   : index
    Created on : Sep 11, 2025, 1:35:18 PM
    Author     : ponlawatchangto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="scroll-smooth">
    <head>
        <title>WheelToGo - ตลาดซื้อขายรถยนต์มือสองออนไลน์อันดับ 1</title>
        <meta name="description" content="WheelToGo แพลตฟอร์มซื้อขายรถยนต์มือสองที่เชื่อถือได้ ค้นหารถในฝันของคุณ หรือลงขายรถของคุณง่ายๆ วันนี้"/>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body class="bg-[#ffffff] min-h-screen font-['Sansation'] flex flex-col bg-[url('https://assets.qz.com/media/8829c0e55f0522cea7b589fec420db88.jpg')] bg-cover bg-center bg-no-repeat bg-fixed">

        <!-- Loader -->
        <div id="loader" class="fixed top-0 left-0 w-full h-full bg-black flex justify-center items-center z-50">
            <span class="loading loading-dots loading-xl text-white"></span>
        </div>

        <!-- Page Content (Initially Hidden) -->
        <div id="page-content" style="display: none;" class="flex flex-col min-h-screen">
            <jsp:include page="header.jsp"/>
            <div class="flex-grow flex flex-col bg-black bg-opacity-60 pb-[50px]">
                <div class="flex flex-col py-48 ml-[100px] text-[96px] text-white font-bold">
                    <h1>
                        Number 1<br>
                        Customer Choice
                    </h1>
                    <a 
                        href="${pageContext.request.contextPath}/browse-cars.jsp" 
                        class="bg-black py-3 px-10 rounded-full text-[36px] w-fit mt-[20px]
                                transition-all duration-300 ease-in-out 
                                hover:scale-[103%] ">
                        Find Your New Car!
                    </a>
                </div>
                <c:if test="${not empty applicationScope.announcements}">
                    <c:set var="total" value="${fn:length(applicationScope.announcements)}" />
                    <div class="carousel w-[90%] shadow-2xl mx-auto my-[75px] rounded-3xl" id="annoucementCarousel">
                        <c:forEach items="${applicationScope.announcements}" var="announcement" varStatus="loop">
                            <c:set var="prevIndex" value="${(loop.index - 1 + total) % total}" />
                            <c:set var="nextIndex" value="${(loop.index + 1) % total}" />

                            <div id="slide${loop.index}" class="carousel-item relative w-full">
                                <img src="data:image/jpeg;base64,${announcement.image}" class="w-full" />
                                <div class="absolute left-5 right-5 top-1/2 flex -translate-y-1/2 transform justify-between">
                                    <a href="#slide${prevIndex}" class="btn btn-circle">❮</a>
                                    <a href="#slide${nextIndex}" class="btn btn-circle">❯</a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <div class="flex flex-col items-center py-12 px-12 rounded-3xl bg-black bg-opacity-70 shadow-2xl w-[90%] mx-auto my-24">
                    <div class="font-bold text-[52px] text-white">Start Selling Your Car Today !</div>
                    <div class="text-white text-lg">
                        Our website got visited : <c:out value="${applicationScope.total_visitor_count}"/> times.
                    </div>
                    <button class="py-4 px-10 bg-red-800 mt-8 mb-4 rounded-full hover:bg-white hover:scale-[103%] transition-all duration-300 ease-in-out">
                        <a href="${pageContext.request.contextPath}/member/car-form.jsp" class="font-bold text-[24px] text-black">
                            Sell your car with us !
                        </a>
                    </button>
                </div>
                <div class="flex justify-between w-[80%] mx-auto my-[75px] items-center gap-18">
                    <div class="text-white">
                        <div class="font-bold text-[48px]">We work like Professional</div>
                        <div class="ms-12 text-lg">At WheelToGo, professionalism is the cornerstone of our service. It means providing you with meticulously maintained vehicles, transparent pricing with no hidden fees, and a dedicated support team ready to assist you on every step of your journey. We handle the details, so you can enjoy the ride with complete peace of mind.</div>
                    </div>
                    <img src="https://www.betterteam.com/images/car-salesman-job-description-5760x3840-20201126.jpeg" 
                        class="rounded-3xl h-[300px] shadow-2xl 
                                transition-all duration-300 ease-in-out 
                                hover:scale-[101.5%] hover:shadow-2xl"/>
                </div>

            </div>

            <jsp:include page="footer.jsp"/>
        </div>

        <script>
            $(window).on('load', function() {
                console.log("window on load is running")
                // Delay for 1.5 seconds before hiding the loader
                setTimeout(function() {
                    $('#loader').fadeOut('slow', function() {
                        console.log("removing #loader")
                        $(this).remove();
                    });
                    console.log("#page-content fadeIn")
                    $('#page-content').fadeIn('slow');
                }, 50); // 1000 milliseconds = 1 second
            });
        </script>
    </body>
</html>