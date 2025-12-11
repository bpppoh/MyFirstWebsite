<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Sansation:ital,wght@0,300;0,400;0,700;1,300;1,400;1,700&display=swap" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />
<link rel='stylesheet' href='https://cdn-uicons.flaticon.com/3.0.0/uicons-bold-rounded/css/uicons-bold-rounded.css'>
<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>

<div id="" class="w-full font-['Sansation'] flex justify-between items-center bg-black py-4 px-20 text-white">
    <a class="font-bold text-[38px]" href="${pageContext.request.contextPath}/index.jsp">
        WheelToGo
    </a>
    <div class="hidden xl:block">
        <div class="flex font-bold gap-8">
            <ul class="flex gap-12 text-[20px] items-center">
                <jsp:include page="elementForHeader/header-nav.jsp"/>
            </ul>
            <c:choose>
                <c:when test="${not empty sessionScope.id && sessionScope.id != null}"> 
                    <jsp:include page="elementForHeader/user-show.jsp"/>
                </c:when>
                <c:otherwise>
                    <jsp:include page="elementForHeader/login-and-signup.jsp"/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="block xl:hidden">
        <div class="drawer">
            <input id="my-drawer-1" type="checkbox" class="drawer-toggle" />
            <div class="drawer-content text-black">
                <!-- Page content here -->
                <label for="my-drawer-1" class="drawer-button"><i class="fi fi-br-menu-burger text-white font-bold text-[24px] hover:cursor-pointer"></i></label>
            </div>
            <div class="drawer-side text-white font-bold">
                <label for="my-drawer-1" aria-label="close sidebar" class="drawer-overlay"></label>
                <ul class="menu bg-black min-h-full w-80 p-8 flex flex-col gap-2 text-[16px]">
                <!-- Sidebar content here -->
                    <li class="transition-all duration-200 ease-in-out hover:text-yellow-500 hover:translate-x-1 cursor-pointer">
                        <a href="${pageContext.request.contextPath}/index.jsp" class=""><i class="fi fi-sr-home mr-1"></i>Home</a>
                    </li>
                    <li class="transition-all duration-200 ease-in-out hover:text-red-400 hover:translate-x-1 cursor-pointer">
                        <a href="${pageContext.request.contextPath}/browse-cars.jsp"><i class="fi fi-sr-search mr-1"></i>
                            Browsing</a>
                    </li>
                    <li class="transition-all duration-200 ease-in-out hover:text-green-500 hover:translate-x-1 cursor-pointer mb-[40px]">
                        <a href="#"><i class="fi fi-sr-heart mr-1"></i>About us</a>
                    </li>
                        <c:choose>
                            <c:when test="${not empty sessionScope.id && sessionScope.id != null}"> 
                                <c:set var="usernameClass" value="" />
                                <c:if test="${requestScope.isAdmin}">
                                    <c:set var="usernameClass" value="text-yellow-500" />
                                </c:if>
                                <li>
                                    <a class="flex items-center">
                                        <img src="${pageContext.request.contextPath}/ImageForMainElement/Header/user.png" alt="user" class="h-6 w-6 mr-2"/>
                                        <span class="${usernameClass} text-white font-bold"><c:out value="${cookie.username.value}"/></span>
                                    </a>
                                </li>
                                <li><a>Status : 
                                    <c:choose>
                                        <c:when test="${requestScope.isAdmin}">Admin</c:when>
                                        <c:when test="${requestScope.isMember}">Member</c:when>
                                        <c:otherwise>Guest</c:otherwise>
                                    </c:choose>
                                </a></li>
                                <c:if test="${requestScope.isMember == true}">
                                    <li class="hover:bg-gray-800 transition-all duration-200 ease-in-out hover:bg-gray-700 rounded-xl"><a href="${pageContext.request.contextPath}/member/car-form.jsp">Sell your car !</a></li>
                                    <c:if test="${requestScope.isAdmin == true}">
                                        <li><a id="showDatabaseMobile" class="hover:bg-gray-800 transition-all duration-200 ease-in-out hover:bg-gray-700 rounded-xl">Show Database</a></li>
                                        <li><a href="${pageContext.request.contextPath}/admin/announcement-insert.jsp" class="hover:bg-gray-800 transition-all duration-200 ease-in-out hover:bg-gray-700 rounded-xl">Announcement</a></li>
                                    </c:if>
                                </c:if>
                                <li class="bg-red-700 hover:bg-gray-800 transition-all duration-200 ease-in-out hover:bg-gray-700 rounded-xl"><a id="logOutMobile">Log out</a></li>
                            </c:when>
                            <c:otherwise>
                                <li class="flex gap-2" id="here">
                                    <a class="inline-block rounded-full bg-blue-500 hover:translate-x-1 transition-all duration-200 ease-in-out" href="${pageContext.request.contextPath}/login.jsp">Login</a>
                                    <a class="inline-block rounded-full bg-white text-black hover:translate-x-1 transition-all duration-200 ease-in-out" href="${pageContext.request.contextPath}/sign-up.jsp">Sign up</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                        <form method="post" action="${pageContext.request.contextPath}/LogOut" id="logoutFormMobile" class="hidden"></form>
                        <form action="${pageContext.request.contextPath}/admin/car-list.jsp" method="get" id="carListFormMobile" class="hidden"><input type="hidden" name="page" value="1"></form>
                        <script>
                            $(document).ready(function() {
                                $("#logOutMobile").click(function() {
                                    $("#logoutFormMobile").submit();
                                });
                                $("#showDatabaseMobile").click(function() {
                                    $("#carListFormMobile").submit();
                                });
                            });
                        </script>
                </ul>
            </div>
        </div>
    </div>
</div>  