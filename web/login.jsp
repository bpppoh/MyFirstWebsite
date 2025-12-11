<%-- 
    Document   : login
    Created on : Sep 17, 2025, 9:12:10 PM
    Author     : ponlawatchangto
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login - WheelToGo</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
        <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body class="bg-[#ffffff] min-h-screen font-['Sansation'] flex flex-col bg-[url('https://assets.qz.com/media/8829c0e55f0522cea7b589fec420db88.jpg')] bg-cover bg-center bg-no-repeat bg-fixed">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <!-- Loader -->
        <div id="loader" class="fixed top-0 left-0 w-full h-full bg-black flex justify-center items-center z-50">
            <span class="loading loading-dots loading-xl text-white"></span>
        </div>

        <!-- Page Content (Initially Hidden) -->
        <div id="page-content" style="display: none;" class="flex flex-col min-h-screen">
            <jsp:include page="./header.jsp"/>
            <div class="flex-grow flex items-center justify-center p-4 sm:p-6 lg:p-8 bg-black bg-opacity-60">
                <div class="w-full max-w-4xl mx-auto bg-white rounded-2xl shadow-2xl overflow-hidden flex flex-col lg:flex-row">
                    <!-- Left Side: Branding -->
                    <div class="w-full lg:w-1/2 p-8 sm:p-12 bg-gray-900 text-white flex flex-col justify-center items-center text-center">
                        <h2 class="text-3xl font-bold mb-4">Welcome Back to WheelToGo</h2>
                        <p class="text-gray-300">Your journey continues here. Log in to access premium car rentals and manage your bookings with ease.</p>
                    </div>

                    <!-- Right Side: Login Form -->
                    <div class="w-full lg:w-1/2 p-8 sm:p-12">
                        <form action="Login" class="flex flex-col gap-5" method="post">
                            <h1 class="font-bold text-4xl text-center mb-4">Login</h1>  

                            <c:if test="${not empty param.error or not empty errorMessage}">
                                <div role="alert" class="alert alert-error text-white text-sm">
                                    <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" /></svg>
                                    <span>
                                        <c:choose>
                                            <c:when test="${not empty errorMessage}"><c:out value="${errorMessage}"/></c:when>
                                            <c:when test="${not empty param.error}"><c:out value="${param.error}"/></c:when>
                                            <c:otherwise>An unknown error occurred. Please try again.</c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                            </c:if>

                            <div>
                                <div class="text-lg font-bold mb-1">Username</div>
                                <label class="input input-bordered flex items-center gap-2 w-full shadow-md inset-shadow-black">
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4 opacity-70"><path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6ZM12.735 14c.618 0 1.093-.561.872-1.139a6.002 6.002 0 0 0-11.215 0c-.22.578.254 1.139.872 1.139h9.47Z" /></svg>
                                    <input
                                        id="username"
                                        name="username"
                                        type="text"
                                        required
                                        class="pl-2"
                                        placeholder="Username"
                                        pattern="[A-Za-z][A-Za-z0-9_-]*"
                                        minlength="6"
                                        maxlength="12"
                                        title="6-12 characters, starts with a letter. Can contain letters, numbers, underscore, or dash."
                                        />
                                </label>
                            </div>

                            <div>
                                <div class="text-lg font-bold mb-1">Password</div>
                                <label class="input input-bordered flex items-center gap-2 w-full shadow-md inset-shadow-black">
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4 opacity-70"><path fill-rule="evenodd" d="M14 6a4 4 0 0 1-4.899 3.899l-1.955 1.955a.5.5 0 0 1-.353.146H5v1.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-2.293a.5.5 0 0 1 .146-.353l3.955-3.955A4 4 0 1 1 14 6Zm-4-2a.75.75 0 0 0 0 1.5.5.5 0 0 1 .5.5.75.75 0 0 0 1.5 0 2 2 0 0 0-2-2Z" clip-rule="evenodd" /></svg>
                                    <input
                                        name="password"
                                        type="password"
                                        required
                                        class="pl-2"
                                        placeholder="Password"
                                        minlength="8"
                                        pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                                        title="Must be at least 8 characters, including a number, a lowercase letter, and an uppercase letter."
                                        />
                                </label>
                            </div>

                            <input 
                                type="submit" 
                                value="Login" 
                                class="py-3 rounded-full bg-blue-400 w-full mt-4 font-bold
                                        hover:scale-[105%] transition-all duration-300 ease-in-out">

                            <div class="text-center text-sm">
                                Don't have an account? <a href="${pageContext.request.contextPath}/sign-up.jsp" class="hover:underline !hover:text-blue-400">Sign up</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <jsp:include page="footer.jsp"/>
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
            });
        </script>
    </body>
</html>
