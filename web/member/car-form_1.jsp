<%--
    Document   : formsave
    Created on : Sep 4, 2025, 2:35:16 PM
    Author     : ponlawatchangto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />
        <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <title>Car To Go : form</title>
    </head>
    <body class="font-['Sansation'] flex flex-col min-h-screen">
        <jsp:include page="/header.jsp"/>
        <div class="flex-grow flex flex-col space-y-12 items-center my-[60px]">
            <div class="text-[50px] font-bold select-none">
                Car Information Form
            </div>
            <div class="w-[90%] mx-auto">
                <form
                    id="carForm"
                    action="<%= request.getContextPath()%>/UploadServlet"
                    method="post"
                    enctype="multipart/form-data"
                    class="w-full flex flex-row gap-10"
                    >
                    <!-- Left Column - Car Details -->
                    <div class="w-[60%] p-12 bg-white rounded-4xl shadow-xl flex flex-col items-center space-y-6">
                        <div class="text-3xl font-bold">
                            Car Details
                        </div>

                        <!-- Title Section -->
                        <div class="w-full flex flex-col space-y-2">
                            <label for="title" class="text-md font-medium">Title</label>
                            <textarea
                                required
                                placeholder="Present what you want to sell to customer here !"
                                name="title"
                                class="textarea textarea-bordered 
                                !w-full h-[3lh] min-h-[1lh] max-h-[5lh]
                                textarea-lg
                                border-slate-300
                                focus:outline-none focus:ring-2 focus:ring-offset-2"
                                ></textarea>
                        </div>

                        <!-- Car Information Part -->
                        <div class="w-full flex flex-col space-y-5">
                            <div class="flex flex-row space-x-8 justify-around">
                                <div class="flex-grow flex flex-col space-y-2">
                                    <label for="brand" class="text-md font-medium">Car Brand</label>
                                    <input
                                        type="text"
                                        placeholder="For Example : Toyota"
                                        name="brand"
                                        class="input input-bordered !w-full"
                                        />
                                </div>
                                <div class="flex-grow flex flex-col space-y-2">
                                    <label for="model" class="text-md font-medium">Model</label>
                                    <input
                                        type="text"
                                        placeholder="For Example : Vios"
                                        name="model"
                                        class="input input-bordered !w-full"
                                        />
                                </div>
                            </div>

                            <div class="flex flex-row space-x-8">
                                <div class="flex-grow flex flex-col space-y-2">
                                    <label for="sub_model" class="text-md font-medium">Sub-Model</label>
                                    <input
                                        type="text"
                                        placeholder="For Example : Generation 2"
                                        name="sub_model"
                                        class="input input-bordered !w-full"
                                        />
                                </div>
                                <div class="flex-grow flex flex-col space-y-2">
                                    <label for="year" class="text-md font-medium">Year</label>
                                    <input
                                        type="text"
                                        placeholder="For Example : 2004"
                                        name="year"
                                        class="input input-bordered !w-full"
                                        />
                                </div>
                            </div>

                            <div class="flex flex-row space-x-8">
                                <div class="flex-grow flex flex-col space-y-2">
                                    <label for="body_type" class="text-md font-medium">Body Type</label>
                                    <input
                                        type="text"
                                        placeholder="For Example : Sedan"
                                        name="body_type"
                                        class="input input-bordered !w-full"
                                        />
                                </div>
                                <div class="flex-grow flex flex-col space-y-2">
                                    <label for="color" class="text-md font-medium">Color</label>
                                    <input
                                        type="text"
                                        placeholder="For Example : Red"
                                        name="color"
                                        class="input input-bordered !w-full"
                                        />
                                </div>
                            </div>

                            <div class="flex flex-row space-x-8">
                                <div class="flex-grow flex flex-col space-y-2">
                                    <label for="mileage" class="text-md font-medium">Mileage</label>
                                    <input
                                        type="number"
                                        placeholder="For Example : 140250"
                                        name="mileage"
                                        class="input input-bordered !w-full"
                                        />
                                </div>
                                <div class="flex-grow flex flex-col space-y-2">
                                    <label for="price" class="text-md font-medium">Price</label>
                                    <input
                                        type="number"
                                        placeholder="For Example : 150000"
                                        name="price"
                                        class="input input-bordered !w-full"
                                        />
                                </div>
                            </div>

                            <div class="flex flex-row space-x-8">
                                <div class="flex-grow flex flex-col space-y-2">
                                    <label for="gear" class="text-md font-medium">Gear</label>
                                    <input
                                        type="text"
                                        placeholder="For Example : Auto Transmission"
                                        name="gear"
                                        class="input input-bordered !w-full"
                                        />
                                </div>
                                <div class="flex-grow flex flex-col space-y-2">
                                    <label for="fuel_type" class="text-md font-medium">Fuel Type</label>
                                    <input
                                        type="text"
                                        placeholder="For Example : Gasoline"
                                        name="fuel_type"
                                        class="input input-bordered !w-full"
                                        />
                                </div>
                            </div>

                            <div class="flex flex-col space-y-2">
                                <label for="engine_displacement" class="text-md font-medium">Engine Displacement</label>
                                <input
                                    type="text"
                                    placeholder="For Example : 3000"
                                    name="engine_displacement"
                                    class="input input-bordered !w-full"
                                    />
                            </div>
                        </div>

                        <!-- Submit Button -->
                        <button 
                            type="submit" 
                            class="py-3 px-5 w-[60%] rounded-full bg-blue-600 
                            hover:bg-blue-700 hover:scale-[105%] hover:shadow-lg
                            cursor-pointer
                            transition-all duration-300 ease-in-out
                            text-white font-bold text-lg">
                            Submit Car Information
                        </button>
                    </div>

                    <!-- Right Column - Image Upload -->
                    <div class="w-[40%] p-12 bg-white rounded-4xl shadow-xl flex flex-col items-center space-y-6">
                        <div class="text-3xl font-bold">
                            Car Images
                        </div>

                        <!-- Main Picture Upload -->
                        <div class="w-full flex flex-col space-y-2">
                            <label class="text-md font-medium">Main Picture Cover</label>
                            <div class="w-full aspect-[3/2] bg-[#fafafa] rounded-4xl 
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
                                <input type="file" name="car_main_pic" accept="image/jpeg , image/png" class="absolute inset-0 opacity-0 cursor-pointer" id="imageInput" required/>
                            </div>
                        </div>

                        <!-- Additional Images -->
                        <div class="w-full flex flex-col space-y-2">
                            <label class="text-md font-medium">Additional Photos (max 15 photos)</label>
                            <input
                                type="file"
                                name="additional_images"
                                id="additional_images_input"
                                multiple
                                accept="image/*"
                                class="file-input file-input-bordered !w-full !h-fit border-gray-200 border
                                file:bg-black file:text-white file:py-2 file:px-5
                                focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                                />
                            <div id="image-upload-error" class="text-red-500 mt-2"></div>
                        </div>

                        <!-- Hidden Creator Field -->
                        <input name="creator" type="hidden" value="<c:out value="${sessionScope.userInfo.username}"/>" />
                    </div>
                </form>
            </div>
        </div>
        <jsp:include page="/footer.jsp"/>
        <script>
            $(document).ready(function () {
                console.log("Document is ready!");

                // Show input image on #imagePreview
                $('#imageInput').on('change', function () {
                    var file = this.files[0];
                    if (file) {
                        var reader = new FileReader();
                        reader.onload = function (e) {
                            $('#imagePreview').attr('src', e.target.result).removeClass('hidden');
                            $('#imageLabel').hide();
                            $('#imageUploadContainer').removeClass('bg-[#fafafa]');
                        }
                        reader.readAsDataURL(file);
                    } else {
                        $('#imagePreview').attr('src', '').addClass('hidden');
                        $('#imageLabel').show();
                        $('#imageUploadContainer').addClass('bg-[#fafafa]');
                    }
                });

                // Additional Images Upload Limit
                const maxImageInput = 15;
                $('#additional_images_input').on('change', function () {
                    const inputImages = this.files.length;
                    if (inputImages > maxImageInput) {
                        let errorMessage = "Maximum images selected is 15";
                        $('#image-upload-error').text(errorMessage);
                        alert(errorMessage);
                        $(this).val('');
                    } else {
                        $('#image-upload-error').text('');
                    }
                });

                // Form Auto-Save to Local Storage
                const form = $('#carForm');
                const formDataKey = 'savedCarFormData'; // Name to recall in local storage

                // saveFormData() use to collect all input data and save to local storage
                function saveFormData() {
                    // save data from input each element into local storage
                    console.log("Entering saveFormData function in <script> tag");

                    // Access all input , textarea and select(that may be added in the future) elements in the form
                    // Using form.find(..).each( function() {..})
                    form.find('input, textarea,select').each(function() {
                        const item = $(this) ;
                        const name = item.attr('name');
                        // First check if item has valid name
                        // And item is not file input
                        // ผมเขียนเองนะครับอาจารย์
                        if (name && name.length > 0 && item.attr('type') !== 'file') {
                            // Ust item.val() to get value of input
                            const val = item.val();
                            // Use localStorage.setItem to save data as key-value pair
                            localStorage.setItem('car-form_' + name, val);
                            console.log("{" + name + "} : " + val);
                        }
                    })
                    localStorage.setItem('hasSavedKey', 'true');
                    console.log("Exit saveFormData function in <script> tag");
                }

                // loadFormData() use to retrieve all input data from local storage
                function loadFormData() {
                    console.log("Entering loadFormData function in <script> tag");
                    if (localStorage.getItem('hasSavedKey') === 'true' && confirm("You have unsubmitted form data. Do you want to load it?")) {
                        console.log("User confirm to load saved data from local storage");
                        // Access all input , textarea and select(that may be added in the future) elements in the form
                        // Using form.find(..).each( function() {..}) Same as saveFormData function
                        form.find('input, textarea, select').each(function() {
                            const item = $(this) ;
                            const name = item.attr('name');
                            // First check if item has valid name
                            // And item is not file input
                            if (name && name.length > 0 && item.attr('type') !== 'file') {
                                // Use localStorage.getItem to get value of input
                                const value = localStorage.getItem('car-form_' + name);
                                // Check if value that get from local storage is not null
                                if (value !== null) {
                                    // Sure that value is not null
                                    // Use item.val(value) to set value of input
                                    item.val(value);
                                }
                            }
                        })
                    }
                    console.log("Exit loadFormData function in <script> tag");
                }

                // clearFormData() use to clear all saved data in local storage
                // key_item starts with 'car-form_'
                function clearFormData() {
                    console.log("Entering clearFormData function in <script> tag");
                    // Loop through all keys in local storage by for loop
                    // Start at the end of local storage so that removing item will not affect the loop
                    for(let i = localStorage.length - 1 ; i >= 0 ; i--) {
                        // localStorage.key(i) to get key at index i
                        const key = localStorage.key(i);
                        // Check if key starts with 'car-form_'
                        if(key && key.startsWith('car-form_')) {
                            // Sure that key is starts with 'car-form_'
                            // Use localStorage.removeItem(key) to remove item with key
                            localStorage.removeItem(key);
                        }
                    }
                    console.log("All car-form_ data cleared from local storage");
                    console.log("Exit clearFormData function in <script> tag");
                }

                form.on('input change', 'input, textarea, select', saveFormData);
                form.on('submit', clearFormData);
                loadFormData();
            }); 
        </script>
    </body>
</html>