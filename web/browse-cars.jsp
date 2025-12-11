<%-- 
    Document   : browsingCar
    Created on : Sep 30, 2025, 10:56:14 PM
    Author     : ponlawatchangto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ค้นหารถยนต์มือสอง | ซื้อ-ขายรถบ้าน สภาพดี - WheelToGo</title>
        <meta name="description" content="เลือกชมและค้นหารถยนต์มือสองคุณภาพดีหลากหลายยี่ห้อในราคาที่คุณพอใจ พร้อมระบบค้นหาที่ใช้งานง่ายที่ WheelToGo"/>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
        <script src="https://cdn.tailwindcss.com"></script>
        <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />
    </head>
    <body class="bg-[#ffffff] min-h-screen font-['Sansation'] flex flex-col bg-[url('https://assets.qz.com/media/8829c0e55f0522cea7b589fec420db88.jpg')] bg-cover bg-center bg-no-repeat bg-fixed">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <!-- Loader -->
        <div id="loader" class="fixed top-0 left-0 w-full h-full bg-black flex justify-center items-center z-50">
            <span class="loading loading-dots loading-xl text-white"></span>
        </div>

        <!-- Page Content (Initially Hidden) -->
        <div id="page-content" style="display: none;" class="flex flex-col min-h-screen">
            <jsp:include page="./header.jsp" />
            <div class="flex-grow flex flex-col bg-black bg-opacity-[70%] pb-[50px] text-white">
                <h1 class="text-5xl font-bold text-center select-none py-12">Find Your New Car!</h1>
                <%-- Content Part --%>
                <div class="flex flex-col lg:flex-row gap-8 px-4 md:px-8 lg:px-12">
                    <div class="w-full lg:w-[300px] p-6 bg-gray-800 bg-opacity-80 border border-gray-700 rounded-2xl shadow-lg self-start text-white">
                        <div class="flex flex-col space-y-4">
                            <h3 class="text-2xl font-bold border-b border-gray-600 pb-2">Filter Cars</h3>

                            <div class="flex flex-col space-y-2 pt-2">
                                <label class="text-md font-medium">Car Brand</label>
                                <div id="brand-filter-container" class="flex flex-col gap-2 mt-2">

                                </div>
                            </div>

                            <div>
                                <button id="search-button" class="rounded-xl w-full py-3 bg-black text-white font-semibold
                                                            flex items-center justify-center gap-2
                                                            transition-all duration-300 ease-in-out
                                                            hover:bg-gray-900 hover:scale-[102%] ring-2 ring-gray-400">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                        <path stroke-linecap="round" stroke-linejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
                                    </svg>
                                    <span>Search</span>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="flex-grow flex flex-col">
                         <div class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-8" id="car-grid-container">
                           Unloaded yet
                        </div>
                        <%-- Pagination Controls --%>
                        <div class="join mx-auto mt-14 text-[20px]" id="pagination-container">
                            <button class="join-item bg-black p-2 text-white" id="BackButton">«</button>
                            <button class="join-item bg-black p-2 text-white" id="PageNumber">Page 1</button>
                            <button class="join-item bg-black p-2 text-white" id="ForwardButton">»</button>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:include page="./footer.jsp" />
        </div>
        <script>
            $(window).on('load', function() {
            // Delay for 1.5 seconds before hiding the loader
                setTimeout(function() {
                    $('#loader').fadeOut('slow', function() {
                        $(this).remove();
                    });
                    $('#page-content').fadeIn('slow');
                }, 100); // 1000 milliseconds = 1 second
            });

            $(document).ready(function() {
                console.log("Document is ready!");

                const brandFilterContainer = $('#brand-filter-container');
                const searchButton = $('#search-button');
                const carGridContainer = $('#car-grid-container');
                const paginationContainer = $('#pagination-container');
                const backButton = $('#BackButton');
                const forwardButton = $('#ForwardButton');
                const pageNumberButton = $('#PageNumber');
                const isAdmin = ${not empty requestScope.isAdmin && requestScope.isAdmin == true} ;

                let currentPage = 1;
                let totalPages = 1;
                let currentBrandIds = [];

                function updatePaginationUI() {
                    if (totalPages > 1) {
                        pageNumberButton.text("Page " + currentPage + " of " + totalPages);
                        backButton.prop('disabled', currentPage <= 1);
                        forwardButton.prop('disabled', currentPage >= totalPages);
                        paginationContainer.show();
                    } else {
                        paginationContainer.hide();
                    }
                }
    
                function fetchFilteredCars(page) {
                    carGridContainer.html('<div class="col-span-full w-full text-center p-12"><span class="loading loading-dots loading-lg text-white"></span></div>');
                    $.ajax({
                        url: '${pageContext.request.contextPath}/CarFilterServlet',
                        data: { 
                                 brandIds: currentBrandIds,
                                 isAdmin: isAdmin,
                                 page: page
                             },
                        type: 'GET',
                        dataType: 'html',
                        traditional: true,
                        success: function (responseHtml, status, xhr) {
                            currentPage = parseInt(xhr.getResponseHeader("X-Current-Page")) || 1;
                            totalPages = parseInt(xhr.getResponseHeader("X-Total-Pages")) || 1;
                            setTimeout(() => {
                                carGridContainer.html(responseHtml);
                                updatePaginationUI();
                            }, 500); 
                        },error: function(xhr, status, error) {
                        console.error("Error filtering cars:", status, error);
                            carGridContainer.html('<div class="col-span-full text-center p-12 text-red-400">Error loading data. Please try again.</div>');
                            paginationContainer.hide();
                        }
                    });
                }
    
                function loadBrandFilter() {
                    brandFilterContainer.html('<p class="text-gray-400">Loading brands...</p>');
                    $.ajax({
                        url: "${pageContext.request.contextPath}/CarDataServlet",
                        data: { action: 'getBrandsAsHtml' },
                        type: 'GET',
                        dataType: 'html',
                        success: function (responseOption) {
                            brandFilterContainer.html(responseOption);
                        },
                        error: function(xhr, status, error) {
                            console.error("Error loading brands:", status, error);
                            brandFilterContainer.html('<p class="text-red-500">Error loading brands.</p>');
                        }
                    });
                }
    
                   // Initial load
                loadBrandFilter();
                fetchFilteredCars(1); // Load first page initially

                // Event Handlers
                searchButton.on('click', function() {
                    currentBrandIds = [];
                    $('input[name="brand-checkbox"]:checked').each(function() {
                        currentBrandIds.push($(this).val());
                    });
                    fetchFilteredCars(1); // Always search from page 1
                });
    
                backButton.on('click', function() {
                    if (currentPage > 1) {
                        fetchFilteredCars(currentPage - 1);
                    }
                });
   
                forwardButton.on('click', function() {
                    if (currentPage < totalPages) {
                        fetchFilteredCars(currentPage + 1);
                    }
                });

                $(document).keypress(function(e) {
                    if (e.which == 13) {
                        console.log('Enter pressed anywhere!');
                        searchButton.trigger('click');
                    }
                });

            });
           </script>
    </body>
</html>