<%-- 
    Document   : updateForm
    Created on : Sep 11, 2025, 2:50:50 PM
    Author     : ponlawatchangto
--%>

<%@page import="java.util.Base64"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WheelToGo : UpdateForm</title>
    </head>
    <body class="font-['Sansation']">   
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <jsp:include page="/header.jsp"/>
        <div class="flex-grow flex flex-col space-y-12 items-center my-[60px]">
            <div class="text-[50px] font-bold select-none">
                Edit Car Information
            </div>
            <div class="w-[90%] mx-auto">
                <form
                    action="${pageContext.request.contextPath}/CarUpdateServlet"
                    method="post"
                    enctype="multipart/form-data"
                    class="w-full grid grid-cols-1 lg:grid-cols-5 gap-8"
                    >
                    <input value="${requestScope.car.id}" name="id" class="hidden" />
                    <!-- Left Column - Car Details -->
                    <div class="lg:col-span-3 p-8 bg-white rounded-2xl shadow-xl flex flex-col gap-6">
                        <div class="text-3xl font-bold text-center">
                            Car Details
                        </div>

                        <!-- Title Section -->
                        <div class="col-span-full">
                            <label for="title" class="block text-md font-medium mb-2">Title</label>
                            <textarea
                                required
                                id="title"
                                placeholder="Present what you want to sell to customer here !"
                                name="title"
                                class="textarea textarea-bordered !w-full
                                        transition-all duration-300 ease-in-out
                                        hover:outline-none hover:ring-2 hover:ring-offset-2 hover:ring-blue-500"
                                rows="3"
                                ><c:out value="${requestScope.car.title}" /></textarea>
                        </div>

                        <!-- Car Information Part -->
                        <div class="w-full grid grid-cols-1 md:grid-cols-2 gap-x-6 gap-y-4">
                            
                                <div>
                                    <label for="brand" class="block text-md font-medium mb-2">Car Brand</label>
                                    <select 
                                        id="brand"
                                        name="brand_id"
                                        required
                                        class="select select-bordered w-full">
                                    </select>
                                </div>
                                <div>
                                    <label for="model" class="block text-md font-medium mb-2">Model</label>
                                    <select 
                                        id="model"
                                        name="model_id"
                                        required
                                        class="select select-bordered w-full">
                                    </select>
                                </div>
                            
                                <div>
                                    <label for="sub_model" class="block text-md font-medium mb-2">Sub-Model</label>
                                    <select 
                                        id="sub_model"
                                        name="sub_model_id"
                                        class="select select-bordered w-full">
                                    </select>
                                </div>
                                <div>
                                    <label for="year" class="block text-md font-medium mb-2">Year</label>
                                    <input
                                        type="text"
                                        pattern="[0-9]+"
                                        inputmode="numeric"
                                        name="year"
                                        value="<c:out value='${requestScope.car.year}' />"
                                        class="input input-bordered w-full"
                                        />
                                </div>
                            
                                <div>
                                    <label for="body_type" class="block text-md font-medium mb-2">Body Type</label>
                                    <select 
                                        id="body_type"
                                        name="body_type_id"
                                        required
                                        class="select select-bordered w-full">
                                    </select>
                                </div>
                                <div>
                                    <label for="color" class="block text-md font-medium mb-2">Color</label>
                                    <input
                                        type="text"
                                        name="color"
                                        value="<c:out value='${requestScope.car.color}' />"
                                        class="input input-bordered w-full"
                                        />
                                </div>
                            
                                <div>
                                    <label for="mileage" class="block text-md font-medium mb-2">Mileage (km)</label>
                                    <input
                                        type="text"
                                        name="mileage"
                                        value="<c:out value='${requestScope.car.mileage}' />"
                                        class="input input-bordered w-full"
                                        />
                                </div>
                                <div>
                                    <label for="price" class="block text-md font-medium mb-2">Price (THB)</label>
                                    <input
                                        type="text"
                                        name="price"
                                        value="<c:out value='${requestScope.car.price}' />"
                                        class="input input-bordered w-full"
                                        />
                                </div>
                            
                                <div>
                                    <label for="gear" class="block text-md font-medium mb-2">Gear</label>
                                    <select 
                                        id="gear"
                                        name="gear_id"
                                        required
                                        class="select select-bordered w-full">
                                    </select>
                                </div>
                                <div>
                                    <label for="fuel_type" class="block text-md font-medium mb-2">Fuel Type</label>
                                    <select 
                                        id="fuel_type"
                                        name="fuel_id"
                                        required
                                        class="select select-bordered w-full">
                                    </select>
                                </div>
                            
                            <div class="md:col-span-2">
                                <label for="engine_displacement" class="block text-md font-medium mb-2">Engine Displacement (cc)</label>
                                <input
                                    type="text"
                                    name="engine_displacement"
                                    value="<c:out value='${requestScope.car.engineDisplacement}' />"
                                    class="input input-bordered w-full"
                                    />
                            </div>
                        </div>
                         <!-- Submit Button -->
                        <button 
                            type="submit" 
                            class="btn btn-primary w-full lg:w-3/4 mx-auto mt-4 text-lg">
                            Update Car Information
                        </button>
                    </div>

                    <!-- Right Column - Image Upload -->
                    <div class="lg:col-span-2 p-8 bg-white rounded-2xl shadow-xl flex flex-col gap-6">
                        <div class="text-3xl font-bold text-center">
                            Car Images
                        </div>

                        <!-- Main Picture Upload -->
                        <div class="w-full">
                            <label class="block text-md font-medium mb-2">Main Picture Cover</label>
                            <div class="w-full aspect-video bg-gray-100 rounded-lg 
                                 flex items-center justify-center 
                                 border-2 border-dashed border-gray-300
                                 cursor-pointer transition-all duration-300 relative group" 
                                 id="imageUploadContainer">
                                <div class="text-center text-gray-500 ${not empty requestScope.car.carMainPic ? 'hidden' : ''}" id="imageLabel">
                                    <svg class="mx-auto h-12 w-12 text-gray-400" stroke="currentColor" fill="none" viewBox="0 0 48 48" aria-hidden="true"><path d="M28 8H12a4 4 0 00-4 4v20m32-12v8m0 0v8a4 4 0 01-4 4H12a4 4 0 01-4-4v-4m32-4l-3.172-3.172a4 4 0 00-5.656 0L28 28M8 32l9.172-9.172a4 4 0 015.656 0L28 28m0 0l4 4m4-24h8m-4-4v8" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path></svg>
                                    Click to upload image (JPEG or PNG)
                                </div>
                                <img 
                                    id="imagePreview"
                                    src="data:image/jpeg;base64,${requestScope.car.carMainPic}" 
                                    alt="Image Preview" 
                                    class="absolute inset-0 w-full h-full object-cover rounded-lg ${empty requestScope.car.carMainPic ? 'hidden' : ''}"/>
                                <input type="file" name="car_main_pic" accept="image/jpeg, image/png" class="absolute inset-0 opacity-0 cursor-pointer" id="imageInput"/>
                            </div>
                        </div>

                        <!-- Additional Images -->
                        <div class="w-full">
                            <label class="block text-md font-medium mb-2">Additional Photos (max 15 photos)</label>
                            <input
                                type="file"
                                name="additional_images"
                                id="additional_images_input"
                                multiple
                                accept="image/*"
                                class="file-input file-input-bordered w-full"
                                />
                            <div id="image-upload-error" class="text-red-500 text-sm mt-2"></div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <jsp:include page="/footer.jsp"/>
    <script>
        $(document).ready(function () {
            // --- Constants and Selectors ---
            const currentBrandId = "${requestScope.car.brandId}";
            const currentModelId = "${requestScope.car.modelId}";
            const currentSubModelId = "${requestScope.car.subModelId}";
            const currentBodyTypeId = "${requestScope.car.bodyTypeId}";
            const currentGearId = "${requestScope.car.gearId}";
            const currentFuelId = "${requestScope.car.fuelId}";

            const brandSelect = $('#brand');
            const modelSelect = $('#model');
            const subModelSelect = $('#sub_model');
            const bodyTypeSelect = $('#body_type');
            const gearSelect = $('#gear');
            const fuelSelect = $('#fuel_type');

            // --- Data Loading Functions ---
            function loadBrand() {
                console.log("Loading brands...");
                brandSelect.html('<option disabled selected> Select Brand </option>');
                modelSelect.prop('disabled', true);
                subModelSelect.prop('disabled', true);
                return $.ajax({
                    url: "${pageContext.request.contextPath}/FormGetBrandServlet",
                    type: 'GET',
                    dataType: 'html',
                    success: function (response) {
                        brandSelect.append(response);
                    },
                    error: function (xhr, status, error) {
                        console.error("Error loading brands:", status, error);
                        brandSelect.html('<option disabled selected>Error loading brands.</option>');
                    }
                });
            }

            function loadModel() {
                console.log("Loading Models...");
                modelSelect.html('<option disabled selected> Select Model </option>');
                subModelSelect.html('<option disabled selected> Select Sub-Model </option>'); // Clear sub-model
                subModelSelect.prop('disabled', true);
                const brandId = brandSelect.val();
                if (!brandId) {
                    return $.Deferred().resolve().promise(); // Return a resolved promise if no brand is selected
                }
                return $.ajax({
                    url: "${pageContext.request.contextPath}/FormGetModelServlet",
                    type: 'GET',
                    data: {brandId: brandId},
                    dataType: 'html',
                    success: function (response) {
                        modelSelect.append(response);
                        modelSelect.prop('disabled', false);
                    },
                    error: function (xhr, status, error) {
                        console.error("Error loading models:", status, error);
                        modelSelect.html('<option disabled selected>Error loading models.</option>');
                    }
                });
            }

            function loadSubModel() {
                console.log("Loading Sub-Models...");
                subModelSelect.html('<option disabled selected> Select Sub-Model </option>');
                const modelId = modelSelect.val();
                if (!modelId) {
                    return $.Deferred().resolve().promise(); // Return a resolved promise if no model is selected
                }
                return $.ajax({
                    url: "${pageContext.request.contextPath}/FormGetSubModelServlet",
                    type: 'GET',
                    data: {modelId: modelId},
                    dataType: 'html',
                    success: function (response) {
                        subModelSelect.append(response);
                        subModelSelect.prop('disabled', false);
                    },
                    error: function (xhr, status, error) {
                        console.error("Error loading sub-models:", status, error);
                        subModelSelect.html('<option disabled selected>Error loading sub-models.</option>');
                    }
                });
            }

            function loadBodyType() {
                console.log("Loading body types...");
                return $.ajax({
                    url: "${pageContext.request.contextPath}/AJAXBodyTypeCarForm",
                    type: 'GET',
                    dataType: 'html',
                    success: function (response) {
                        bodyTypeSelect.html(response);
                        if (currentBodyTypeId) {
                            bodyTypeSelect.val(currentBodyTypeId);
                        }
                    },
                    error: function (xhr, status, error) {
                        console.error("Error loading body types:", status, error);
                        bodyTypeSelect.html('<option disabled selected>Error loading body types.</option>');
                    }
                });
            }

            function loadGear() {
                console.log("Loading gears...");
                return $.ajax({
                    url: "${pageContext.request.contextPath}/FormGetGearServlet",
                    type: 'GET',
                    dataType: 'html',
                    success: function (response) {
                        gearSelect.html(response);
                        if (currentGearId) {
                            gearSelect.val(currentGearId);
                        }
                    },
                    error: function (xhr, status, error) {
                        console.error("Error loading gears:", status, error);
                        gearSelect.html('<option disabled selected>Error loading gears.</option>');
                    }
                });
            }

            function loadFuelType() {
                console.log("Loading fuel types...");
                return $.ajax({
                    url: "${pageContext.request.contextPath}/FormGetFuelTypeServlet",
                    type: 'GET',
                    dataType: 'html',
                    success: function (response) {
                        fuelSelect.html(response);
                        if (currentFuelId) {
                            fuelSelect.val(currentFuelId);
                        }
                    },
                    error: function (xhr, status, error) {
                        console.error("Error loading fuel types:", status, error);
                        fuelSelect.html('<option disabled selected>Error loading fuel types.</option>');
                    }
                });
            }

            // --- Event Handlers ---
            brandSelect.on('change', function () {
                loadModel().then(function () {
                    // After models are loaded, load sub-models
                    loadSubModel();
                });
            });

            modelSelect.on('change', function () {
                loadSubModel();
            });

            // --- Initial Data Load ---
            // Chain the dependent dropdowns to load in sequence
            loadBrand().then(function () {
                if (currentBrandId) {
                    brandSelect.val(currentBrandId);
                    return loadModel(); // Load models for the current brand
                }
            }).then(function () {
                if (currentModelId) {
                    modelSelect.val(currentModelId);
                    return loadSubModel(); // Load sub-models for the current model
                }
            }).then(function () {
                if (currentSubModelId) {
                    subModelSelect.val(currentSubModelId);
                }
            });

            // Load independent dropdowns concurrently
            loadBodyType();
            loadGear();
            loadFuelType();

            // --- Image Upload Logic ---
            const maxImageInput = 15;

            $('#additional_images_input').on('change', function () {
                const inputImages = this.files.length;
                if (inputImages > maxImageInput) {
                    let errorMessage = "Maximum 15 images allowed.";
                    $('#image-upload-error').text(errorMessage);
                    alert(errorMessage);
                    $(this).val('');
                } else {
                    $('#image-upload-error').text('');
                }
            });

            $('#imageInput').on('change', function () {
                var file = this.files[0];
                if (file) {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $('#imagePreview').attr('src', e.target.result).removeClass('hidden');
                        $('#imageLabel').addClass('hidden');
                        $('#imageUploadContainer').removeClass('bg-[#fafafa]');
                    };
                    reader.readAsDataURL(file);
                }
            });
        });
    </script>
    </body>
</html>
