<%-- 
    Document   : announcementInsert
    Created on : Sep 26, 2025, 10:01:08 PM
    Author     : ponlawatchangto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WheelToGo : Announcement Insert</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Sansation:ital,wght@0,300;0,400;0,700;1,300;1,400;1,700&display=swap" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />
        <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    </head>
    <body class="font-['Sansation'] flex flex-col min-h-screen">
        <jsp:include page="/header.jsp"/>
        <div class="flex-grow flex flex-col space-y-12 items-center">
            <div class="text-[50px] font-bold select-none">
                Announcement Insert Page
            </div>
            <div class="w-[90%] mx-auto">
                <form action="${pageContext.request.contextPath}/announcementInsert" method="post" enctype="multipart/form-data" class="w-full flex flex-row gap-10">
                    <div class="w-[60%] p-12 bg-white rounded-4xl shadow-xl flex flex-col items-center space-y-6">
                        <div class="text-3xl font-bold">
                            Announcement Image
                        </div>
                        <div class="w-full aspect-[3.2] bg-[#fafafa] rounded-4xl 
                             flex items-center justify-center text-black text-2xl font-bold
                             hover:shadow-md cursor-pointer transition-all duration-300 relative" 
                             id="imageUploadContainer">
                            <div class="text-gray-500" id="imageLabel">
                                Click to upload image (JPEG or PNG)
                            </div>
                            <img 
                                src="" 
                                alt="Image Preview" 
                                id="imagePreview" 
                                class="w-full h-full object-cover rounded-4xl aspect-[3.2] hidden
                                hover:scale-[110%] transition-all duration-300 ease-in-out"/>
                            <input type="file" name="image" accept="image/jpeg , image/png" class="absolute inset-0 opacity-0 cursor-pointer" id="imageInput" required/>
                        </div>
                        <div class="w-full text-center text-gray-500 italic">
                            Recommended image size: 1600x500 pixels (JPEG or PNG) with 1.5:1 aspect ratio.
                        </div>
                        <button 
                            type="submit" 
                            class="py-3 px-5 w-[60%] rounded-full bg-blue-600 
                            hover:bg-blue-700 hover:scale-[105%] hover:shadow-lg
                            cursor-pointer
                            transition-all duration-300 ease-in-out
                            text-white font-bold text-lg">
                            Submit Announcement
                        </button>
                    </div>
                    <div class="w-[40%] p-12 bg-white rounded-4xl shadow-xl flex flex-col items-center space-y-6">
                        <div class="text-3xl font-bold">
                            Announcement Detail
                        </div>
                        <div class="w-full flex flex-col space-y-5">
                            <div class="flex flex-col space-y-2">
                                <label for="name" class="text-md font-medium">Name</label>
                                <input type="text" name="name" placeholder="Announcement Name" class="input input-bordered !w-full" required/>
                            </div>
                            <div class="flex flex-col space-y-2">
                                <label for="description" class="text-md font-medium">Description</label>
                                <textarea name="description" placeholder="Announcement Detail" class="textarea textarea-bordered !w-full min-h-[5lh] max-h-[8lh]"></textarea>
                            </div>
                            <div class="flex flex-row space-x-8">
                                <div class="flex-grow flex flex-col space-y-2">
                                    <label for="startDate" class="text-md font-medium">Start Date</label>
                                    <input type="date" name="startDate" class="input input-bordered w-full" required/>
                                </div>
                                <div class="flex-grow flex flex-col space-y-2">
                                    <label for="endDate" class="text-md font-medium">End Date</label>
                                    <input type="date" name="endDate" class="input input-bordered w-full"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <jsp:include page="/footer.jsp"/>
        <script>
            $(document).ready(function () {
                $('#imageInput').on('change', function () {
                    var file = this.files[0];
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $('#imagePreview').attr('src', e.target.result);
                        $('#imagePreview').removeClass('hidden');
                        $('#imageLabel').attr('hidden', true);
                        $('#imagePreview').attr('alt', 'Image Preview');
                        $('#imageUploadContainer').removeClass('bg-[#fafafa]');
                    };
                    reader.readAsDataURL(file);
                });
            });
        </script>
    </body>
</html>
