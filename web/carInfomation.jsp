<%-- 
    Document   : carInfomation
    Created on : Oct 3, 2025, 1:05:25 PM
    Author     : ponlawatchangto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
        <title>${car.brandName} ${car.modelName} ${car.year} มือสอง ราคา ${car.price} บาท - WheelToGo</title>
        <meta name="description" content="ขายรถยนต์มือสอง ${car.brandName} ${car.modelName} ${car.subModelName} ปี ${car.year} สี ${car.color} เลขไมล์ ${car.mileage} กม. ในราคาพิเศษ ${car.price} บาท. ดูรายละเอียดและข้อมูลติดต่อผู้ขายได้ที่ WheelToGo"/>
        <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    </head>
    <body class="bg-[#ffffff] min-h-screen font-['Sansation'] flex flex-col">
        <!-- Loader -->
        <div id="loader" class="fixed top-0 left-0 w-full h-full bg-black flex justify-center items-center z-50">
            <span class="loading loading-dots loading-xl text-white"></span>
        </div>

        <!-- Page Content (Initially Hidden) -->
        <div id="page-content" style="display: none;" class="flex flex-col min-h-screen">
            <jsp:include page="/header.jsp" />

            <div class="flex-grow w-[85%] mx-auto mt-[50px]">
                <div class="flex flex-row gap-10 px-5">
                    <%-- Picture Path --%>
                    <div class="flex flex-col gap-4 grow mb-12 max-w-[50%]">
                        <img
                            src="data:image/jpeg;base64,${car.carMainPic}"
                            alt="${car.brandName} ${car.modelName} ปี ${car.year}"
                            class="w-full aspect-[3/2] object-cover rounded-2xl shadow-md"
                            />
                        <div class="outline-1 outline-gray-300 rounded-3xl p-4 h-[200px]">
                            <div class="carousel h-[170px] space-x-4 mx-auto" id="carouselbox">
                                <c:forEach var="pic" items="${sidePics}">
                                    <div class="carousel-item cursor-pointer" onclick="document.getElementById('${pic.pictureId}').showModal()">
                                        <img
                                            src="data:image/jpeg;base64,${pic.picture}"
                                            alt="รูปเพิ่มเติมของ ${car.brandName} ${car.modelName}"
                                            />
                                    </div>
                                    <dialog id="${pic.pictureId}" class="modal">
                                        <div class="modal-box w-11/12 max-w-[70%] rounded-3xl">
                                            <img
                                                src="data:image/jpeg;base64,${pic.picture}"
                                                alt="รูปเพิ่มเติมของ ${car.brandName} ${car.modelName} (ขนาดใหญ่)"
                                                />
                                        </div>
                                        <form method="dialog" class="modal-backdrop">
                                            <button>close</button>
                                        </form>
                                    </dialog>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <%-- Infomation Path --%>
                    <div class="flex flex-col gap-8 flex-grow max-w-[50%]">
                        <%-- Infomation Block --%>
                        <div class="flex flex-col w-full h-fit p-8 rounded-xl bg-white outline-1 outline-gray-100 space-y-8">
                            <%-- First Part : Title , brand , model , sub_Model , Gear , CreateDate , Share --%>
                            <div class="flex flex-row justify-between">
                                <%-- Title , brand , model , sub_Model , Gear , CreateDate --%>
                                <div class="max-w-[75%]">
                                    <h1 class="text-2xl font-bold text-pretty mb-3">
                                        <c:out value="${car.title}"/>
                                    </h1>
                                    <div class="text-md text-wrap text-gray-600">
                                        <%-- &nbsp; --%>
                                        <c:out value="${car.brandName}"/> &nbsp; <c:out value="${car.modelName}"/> &nbsp; <c:out value="${car.subModelName}"/> &nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;<c:out value="${car.gearName}"/>
                                    </div>
                                    <div class="text-sm text-wrap text-gray-600">
                                        Post date : <c:out value="${car.createDate}"/> <c:out value="${car.createTime}"/>
                                    </div>
                                </div>
                                <%-- Share --%>
                                <div class="flex flex-col items-end w-fit gap-4">
                                    <a class="cursor-pointer" id="sharebutton">
                                        <div 
                                            class="px-4 py-2 bg-white
                                            transition-all duration-300 ease-in-out
                                            outline-1 outline-gray-300
                                            hover:scale-[110%]
                                            w-fit rounded-full
                                            text-black font-bold text-sm">
                                            Share
                                            <i class="fi fi-sr-share text-[#ff8000] ms-[5px] relative top-[1.5px]"></i>
                                        </div>
                                    </a>
                                </div>
                            </div>
                            <%-- Second Part : Price --%>
                            <div class="flex flex-col space-y-4">
                                <div class="flex justify-between">
                                    <div class="text-xl">Price</div>
                                    <div class="text-xl font-bold text-[#ff8000]"><c:out value="${car.price}"/></div>
                                </div>
                                <hr/>
                                <div class="flex justify-between">
                                    <div class="text-xl">Mileage</div>
                                    <div class="text-xl font-bold text-[#ff8000]"><c:out value="${car.mileage}"/> KM</div>
                                </div>

                                <div class="flex justify-between p-4 outline-1 outline-gray-100 rounded-xl bg-white">
                                    <div class="">
                                        <span class="font-bold">Publisher :</span> <c:out value="${publisher.username}"/>
                                    </div>
                                    <div class="">
                                        <span class="font-bold">Tel :</span> <c:out value="${publisher.phone}"/>
                                    </div>
                                </div>
                            </div>
                            <div class="flex justify-end">
                                <div class="py-3 px-6 bg-[#00cc66] cursor-pointer font-bold rounded-3xl text-white hover:scale-[110%] hover:shadow-lg transition-all duration-300 ease-in-out" id="buybutton">Reserve Now</div>
                                <form id="form" action="${pageContext.request.contextPath}/member/pre-buying.jsp" method="post" class="hidden">
                                    <input type="hidden" name="carId" value="${car.id}"/>
                                </form>
                            </div>
                        </div> <%-- End of Infomation Block Part --%>
                        <div class="collapse bg-base-100 border-base-300 border px-3 mb-16">
                            <input type="checkbox" />
                            <div class="collapse-title font-semibold">
                                <i class="fi fi-sr-mouse-pointer-click me-1"></i>
                                Click for More Infomation
                            </div>
                            <div class="collapse-content text-sm flex flex-col space-y-1">
                                <div class="flex justify-between">
                                    <div class="ps-8">Brand</div>
                                    <div class=""><c:out value="${car.brandName}"/></div>
                                </div>
                                <div class="flex justify-between">
                                    <div class="ps-8">Model</div>
                                    <div class=""><c:out value="${car.modelName}"/></div>
                                </div>
                                <div class="flex justify-between">
                                    <div class="ps-8">Sub Model</div>
                                    <div class=""><c:out value="${car.subModelName}"/></div>
                                </div>
                                <div class="flex justify-between">
                                    <div class="ps-8">Engine Displacement</div>
                                    <div class=""><c:out value="${car.engineDisplacement}"/> CC</div>
                                </div>
                                <div class="flex justify-between">
                                    <div class="ps-8">Fuel Type</div>
                                    <div class=""><c:out value="${car.fuelTypeName}"/></div>
                                </div>
                                <div class="flex justify-between">
                                    <div class="ps-8">Gear</div>
                                    <div class=""><c:out value="${car.gearName}"/></div>
                                </div>
                                <div class="flex justify-between">
                                    <div class="ps-8">Body Type</div>
                                    <div class=""><c:out value="${car.bodyTypeName}"/></div>
                                </div>
                                <div class="flex justify-between">
                                    <div class="ps-8">Color</div>
                                    <div class=""><c:out value="${car.color}"/></div>
                                </div>
                                <div class="flex justify-between">
                                    <div class="ps-8">Year</div>
                                    <div class=""><c:out value="${car.year}"/></div>
                                </div>
                                <div class="flex justify-between">
                                    <div class="ps-8">Mileage</div>
                                    <div class=""><c:out value="${car.mileage}"/> km</div>
                                </div>
                                <div class="flex justify-between">
                                    <div class="ps-8">Price</div>
                                    <div class=""><c:out value="${car.price}"/></div>
                                </div>
                            </div> <%-- End of Collapse-content Part --%>
                        </div> <%-- End of Collapse Part --%>
                    </div> <%-- End of Infomatino Part --%>

                </div>

            </div>

            <jsp:include page="/footer.jsp" />
        </div>
        <script>
            $(window).on('load', function() {
                // Delay for 1.5 seconds before hiding the loader
                setTimeout(function() {
                    $('#loader').fadeOut('slow', function() {
                        $(this).remove();
                    });
                    $('#page-content').fadeIn('slow');
                }, 50); // 1000 milliseconds = 1 second

                $('#sharebutton').on('click', function(event) {
                    event.preventDefault(); // Prevent default anchor tag behavior
                    const urlToCopy = window.location.href; // Get the current URL
                    navigator.clipboard.writeText(urlToCopy)
                    .then(() => {
                        console.log('URL copied to clipboard!');
                        // Optional: Provide visual feedback to the user
                        alert('URL copied to clipboard!');
                    })
                    .catch(err => {
                        console.error('Failed to copy URL: ', err);
                        alert('Failed to copy URL. Please try again.');
                    });
                });

                $('#buybutton').on('click', function(event) {
                    $('#form').submit();
                });
            });
        </script>
    </body>
</html>
