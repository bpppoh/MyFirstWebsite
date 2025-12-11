<%-- 
    Document   : signUp
    Created on : Sep 19, 2025, 10:49:03 PM
    Author     : ponlawatchangto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WheelToGo : Sign Up</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body class="font-['Sansation'] flex flex-col min-h-screen bg-[url('https://assets.qz.com/media/8829c0e55f0522cea7b589fec420db88.jpg')] bg-cover bg-center bg-no-repeat bg-fixed">
        <jsp:include page="./header.jsp" />

        <div class="flex-grow flex items-center justify-center p-4 sm:p-6 lg:p-8 bg-black bg-opacity-60">
            <div class="w-full max-w-4xl mx-auto bg-white rounded-2xl shadow-2xl overflow-hidden flex flex-col lg:flex-row">
                <!-- Left Side: Branding -->
                <div class="w-full lg:w-1/2 p-8 sm:p-12 bg-gray-900 text-white flex flex-col justify-center items-center text-center">
                    <h2 class="text-3xl font-bold mb-4">Join WheelToGo Today</h2>
                    <p class="text-gray-300">Create your account to start your adventure. Access exclusive deals and manage your car rentals effortlessly.</p>
                </div>

                <!-- Right Side: Sign Up Form -->
                <div class="w-full lg:w-1/2 p-8 sm:p-12">
                    <form action="signUp" class="flex flex-col gap-5" method="post">
                        <h1 class="font-bold text-4xl text-center mb-4">Sign Up</h1>

                        <c:if test="${not empty param.error}">
                            <div role="alert" class="alert alert-error text-white text-sm">
                                <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" /></svg>
                                <span><c:out value="${param.error}"/></span>
                            </div>
                        </c:if>

                        <div>
                            <div class="text-lg font-bold mb-1">Username</div>
                            <label class="input input-bordered flex items-center gap-2 w-full shadow-md">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4 opacity-70"><path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6ZM12.735 14c.618 0 1.093-.561.872-1.139a6.002 6.002 0 0 0-11.215 0c-.22.578.254 1.139.872 1.139h9.47Z" /></svg>
                                <input
                                    name="username"
                                    type="text"
                                    required
                                    class="grow"
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
                            <label class="input input-bordered flex items-center gap-2 w-full shadow-md">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4 opacity-70"><path fill-rule="evenodd" d="M14 6a4 4 0 0 1-4.899 3.899l-1.955 1.955a.5.5 0 0 1-.353.146H5v1.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-2.293a.5.5 0 0 1 .146-.353l3.955-3.955A4 4 0 1 1 14 6Zm-4-2a.75.75 0 0 0 0 1.5.5.5 0 0 1 .5.5.75.75 0 0 0 1.5 0 2 2 0 0 0-2-2Z" clip-rule="evenodd" /></svg>
                                <input
                                    name="password"
                                    type="password"
                                    required
                                    class="grow"
                                    placeholder="Password"
                                    minlength="8"
                                    pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                                    title="At least 8 characters, with a number, lowercase, and uppercase letter."
                                    />
                            </label>
                        </div>

                        <div>
                            <div class="text-lg font-bold mb-1">Email</div>
                            <label class="input input-bordered flex items-center gap-2 w-full shadow-md">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4 opacity-70"><path d="M2.5 3A1.5 1.5 0 0 0 1 4.5v.793c.026.009.051.02.076.032L7.674 8.51c.206.1.446.1.652 0l6.598-3.185A.755.755 0 0 1 15 5.293V4.5A1.5 1.5 0 0 0 13.5 3h-11Z" /><path d="M15 6.954 8.978 9.86a2.25 2.25 0 0 1-1.956 0L1 6.954V11.5A1.5 1.5 0 0 0 2.5 13h11a1.5 1.5 0 0 0 1.5-1.5V6.954Z" /></svg>
                                <input
                                    name="email"
                                    type="email"
                                    required
                                    class="grow"
                                    placeholder="mail@site.com"
                                    title="Please enter a valid email address."
                                    />
                            </label>
                        </div>
                        <div>
                            <div class="text-lg font-bold mb-1">Phone Number</div>
                            <label class="input input-bordered flex items-center gap-2 w-full shadow-md">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4 opacity-70"><path d="M2.5 3A1.5 1.5 0 0 0 1 4.5v.793c.026.009.051.02.076.032L7.674 8.51c.206.1.446.1.652 0l6.598-3.185A.755.755 0 0 1 15 5.293V4.5A1.5 1.5 0 0 0 13.5 3h-11Z" /><path d="M15 6.954 8.978 9.86a2.25 2.25 0 0 1-1.956 0L1 6.954V11.5A1.5 1.5 0 0 0 2.5 13h11a1.5 1.5 0 0 0 1.5-1.5V6.954Z" /></svg>
                                <input
                                    name="phone"
                                    type="tel"
                                    required
                                    class="grow"
                                    placeholder="0xx-xxx-xxxx"
                                    pattern="[0-9]{10}"
                                    inputmode="numeric"
                                    title="Please enter a valid phone number."
                                    oninput="this.value = this.value.replace(/[^0-9]/g, '');"
                                    />
                            </label>
                        </div>

                        <input type="submit" value="Create Account" class="py-4 rounded-full bg-blue-400 w-full mt-4 font-bold text-white">

                        <div class="text-center text-sm">
                            Already have an account? <a href="${pageContext.request.contextPath}/login.jsp" class="link link-primary">Log in</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="./footer.jsp" />
    </body>
</html>
