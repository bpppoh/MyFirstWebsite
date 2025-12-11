<%-- 
    Document   : pre-buying
    Created on : Nov 5, 2025, 11:05:55 PM
    Author     : ponlawatchangto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WheelToGo : Browse Cars</title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
        <script src="https://cdn.tailwindcss.com"></script>
        <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    </head>
    <body class="bg-[#ffffff] min-h-screen font-['Sansation'] flex flex-col bg-[url('https://assets.qz.com/media/8829c0e55f0522cea7b589fec420db88.jpg')] bg-cover bg-center bg-no-repeat bg-fixed">
        <!-- Loader -->
        <div id="loader" class="fixed top-0 left-0 w-full h-full bg-black flex justify-center items-center z-50">
            <span class="loading loading-dots loading-xl text-white"></span>
        </div>

        <!-- Page Content (Initially Hidden) -->
        <div id="page-content" style="display: none;" class="flex flex-col min-h-screen">
            <jsp:include page="../header.jsp" />
            <div class="grow flex flex-col bg-black bg-opacity-[70%] pb-[50px] text-white">
                <!-- Content container -->
                <div class="w-[90%] flex mx-auto my-18 gap-16">
                    <div class="flex flex-col gap-8 bg-white rounded-3xl text-black grow p-8 max-w-[40%] h-fit">
                        <img
                            src="data:image/jpeg;base64,${car.carMainPic}"
                            alt="Car Image"
                            class="aspect-[3/2] object-cover rounded-2xl shadow-md"
                            />
                        <div class="carousel h-[170px] space-x-4 mx-auto rounded-3xl" id="carouselbox">
                            <c:forEach var="pic" items="${sidePics}">
                                <div class="carousel-item cursor-pointer" onclick="document.getElementById('${pic.pictureId}').showModal()">
                                    <img
                                        src="data:image/jpeg;base64,${pic.picture}"
                                        alt="Burger"    
                                        />
                                </div>
                                <dialog id="${pic.pictureId}" class="modal">
                                    <div class="modal-box w-11/12 max-w-[70%] rounded-3xl">
                                        <img
                                            src="data:image/jpeg;base64,${pic.picture}"
                                            alt="Burger"
                                            />
                                    </div>
                                    <form method="dialog" class="modal-backdrop">
                                        <button>close</button>
                                    </form>
                                </dialog>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="grow flex flex-col gap-4 bg-white rounded-3xl p-8">
                        <div class="text-black font-bold text-[48px]">
                            Confirmation Order
                        </div>
                        <div class="text-black flex flex-col gap-3 ms-8">
                            <div class="">Brand : <c:out value="${car.brandName}"/></div>
                            <div class="">Model : <c:out value="${car.modelName}"/></div>
                            <div class="">Sub Model : <c:out value="${car.subModelName}"/></div>
                            <div class="">Price : <c:out value="${car.price}"/></div>
                            <div class="">Mileage : <c:out value="${car.mileage}"/> KM</div>
                            <div class="">Gear : <c:out value="${car.gearName}"/></div>
                            <div class="">Body Type : <c:out value="${car.bodyTypeName}"/></div>
                            <div class="">Color : <c:out value="${car.color}"/></div>
                            <div class="">Year : <c:out value="${car.year}"/></div>
                            <div class="">Engine Displacement : <c:out value="${car.engineDisplacement}"/> CC</div>
                            <div class="">Fuel Type : <c:out value="${car.fuelTypeName}"/></div>
                        </div>
                        <button class="py-5 my-8 w-full text-[24px] bg-[#5AB33B] text-center rounded-3xl font-bold cursor-pointer hover:scale-[101%] active:scale-[100%] hover:shadow-lg transition-all duration-300 ease-in-out" onclick="document.getElementById('modal').showModal()">
                            Confirm Reserve 5,000 Baht
                        </button>
                        <dialog id="modal" class="modal text-black">
                            <div class="modal-box flex flex-col">
                                <div class="font-bold text-center text-3xl">Qrcode for payment</div>
                                <img src="<c:out value="${qrcode}"/>" alt="qrcode" class="w-[500px] rounded-3xl mx-auto">
                                <button id="finishbutton" class="py-2 w-[70%] mx-auto bg-[#5AB33B] text-center text-white rounded-3xl font-bold cursor-pointer mt-4">Click when Finish</button>
                                <form action="${pageContext.request.contextPath}/ReserveServlet" method="post" id="reserveform">
                                    <input type="hidden" name="carId" value="${car.id}"/>
                                </form>
                            </div>
                            <form method="dialog" class="modal-backdrop">
                                <button>close</button>
                            </form>
                        </dialog>
                    </div>
                </div>
            </div>
            <jsp:include page="../footer.jsp" />
        </div>
        <script>
            $(window).on('load', function() {
            // Delay for 1.5 seconds before hiding the loader
                setTimeout(function() {
                    $('#loader').fadeOut('slow');
                    $('#page-content').fadeIn('slow');
                }, 100); // 1000 milliseconds = 1 second

                $('#finishbutton').on('click', function() {
                    document.getElementById('modal').close();
                    $('#loader').fadeIn('slow');
                    $(this).prop('disabled', true);
                    setTimeout(function() {
                        document.getElementById('reserveform').submit();
                    }, 2000); // 2000 milliseconds = 2 seconds
                });
            });
            
           </script>
    </body>
</html>