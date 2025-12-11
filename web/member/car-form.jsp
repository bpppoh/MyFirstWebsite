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
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <title>Car To Go : form (number1)</title>
    </head>
    <body class="font-['Sansation'] flex flex-col min-h-screen">
        <jsp:include page="/header.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script> 
        <div class="flex-grow bg-gray-100 py-12 px-4 sm:px-6 lg:px-8">
            <div class="text-5xl font-bold select-none text-center mb-8">
                Car Information Form
            </div>
            <div class="max-w-7xl mx-auto">
                <form
                    id="carForm"
                    action="${pageContext.request.contextPath}/UploadServlet"
                    method="post"
                    enctype="multipart/form-data"
                    class="w-full grid grid-cols-1 lg:grid-cols-5 gap-8"
                    >
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
                                ></textarea>
                        </div>

                        <!-- Car Information Part -->
                        <div class="w-full grid grid-cols-1 md:grid-cols-2 gap-x-6 gap-y-4">
                            
                                <div>
                                    <label for="brand" class="block text-md font-medium mb-2">Car Brand</label>
                                    <select 
                                        id="brand"
                                        name="brand"
                                        required
                                        class="select select-bordered w-full">
                                        <option disabled selected>Pick a Brand</option>
                                    </select>
                                </div>
                                <div>
                                    <label for="model" class="block text-md font-medium mb-2">Model</label>
                                    <select 
                                        id="model"
                                        name="model"
                                        required
                                        class="select select-bordered w-full">
                                        <option disabled selected>Pick a model</option>
                                    </select>
                                </div>
                            
                                <div>
                                    <label for="sub_model" class="block text-md font-medium mb-2">Sub-Model</label>
                                    <select 
                                        id="sub_model"
                                        name="sub_model"
                                        required
                                        class="select select-bordered w-full">
                                        <option value="" disabled selected>Pick a Sub-model</option>
                                    </select>
                                </div>
                                <div>
                                    <label for="year" class="block text-md font-medium mb-2">Year</label>
                                    <input
                                        type="number"
                                        required
                                        placeholder="For Example : 2004"
                                        name="year"
                                        class="input input-bordered w-full"
                                        />
                                </div>
                            
                                <div>
                                    <label for="body_type" class="block text-md font-medium mb-2">Body Type</label>
                                    <select 
                                        id="body_type"
                                        name="body_type"
                                        required
                                        class="select select-bordered w-full">
                                        <option disabled selected>Pick a Body Type</option>
                                    </select>
                                </div>
                                <div>
                                    <label for="color" class="block text-md font-medium mb-2">Color</label>
                                    <input
                                        type="text"
                                        required
                                        placeholder="For Example : Red"
                                        name="color"
                                        class="input input-bordered w-full"
                                        />
                                </div>
                            
                                <div>
                                    <label for="mileage" class="block text-md font-medium mb-2">Mileage (km)</label>
                                    <input
                                        type="number"
                                        required
                                        placeholder="For Example : 140250"
                                        name="mileage"
                                        class="input input-bordered w-full"
                                        />
                                </div>
                                <div>
                                    <label for="price" class="block text-md font-medium mb-2">Price (THB)</label>
                                    <input
                                        type="number"
                                        required
                                        placeholder="For Example : 150000"
                                        name="price"
                                        class="input input-bordered w-full"
                                        />
                                </div>
                            
                                <div>
                                    <label for="gear" class="block text-md font-medium mb-2">Gear</label>
                                    <select 
                                        id="gear"
                                        name="gear"
                                        required
                                        class="select select-bordered w-full">
                                        <option disabled selected>Pick a Gear</option>
                                        <option value="1">Automatic</option>
                                        <option value="2">Manual</option>
                                        <option value="3">CVT</option>
                                    </select>
                                </div>
                                <div>
                                    <label for="fuel_type" class="block text-md font-medium mb-2">Fuel Type</label>
                                    <select 
                                        id="fuel_type"
                                        name="fuel_type"
                                        required
                                        class="select select-bordered w-full">
                                        <option disabled selected>Pick a Fuel Type</option>
                                        <option value="1">Gasoline</option>
                                        <option value="2">Diesel</option>
                                        <option value="3">Hybrid</option>
                                        <option value="4">EV</option>
                                    </select>
                                </div>
                            
                            <div class="md:col-span-2">
                                <label for="engine_displacement" class="block text-md font-medium mb-2">Engine Displacement (cc)</label>
                                <input
                                    type="number"
                                    required
                                    placeholder="For Example : 3000"
                                    name="engine_displacement"
                                    class="input input-bordered w-full"
                                    />
                            </div>
                        </div>

                        <!-- Submit Button -->
                        <button
                            id="submitBtn"
                            type="submit"
                            disabled
                            class="btn btn-primary w-full lg:w-3/4 mx-auto mt-4 text-lg">
                            Loading Form...
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
                                <div class="text-center text-gray-500" id="imageLabel">
                                    <svg class="mx-auto h-12 w-12 text-gray-400" stroke="currentColor" fill="none" viewBox="0 0 48 48" aria-hidden="true"><path d="M28 8H12a4 4 0 00-4 4v20m32-12v8m0 0v8a4 4 0 01-4 4H12a4 4 0 01-4-4v-4m32-4l-3.172-3.172a4 4 0 00-5.656 0L28 28M8 32l9.172-9.172a4 4 0 015.656 0L28 28m0 0l4 4m4-24h8m-4-4v8" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path></svg>
                                    Click to upload image (JPEG or PNG)
                                </div>
                                <img 
                                    src="" 
                                    alt="Image Preview" 
                                    id="imagePreview" 
                                    class="absolute inset-0 w-full h-full object-cover rounded-lg hidden"/>
                                <input type="file" name="car_main_pic" accept="image/jpeg , image/png" class="absolute inset-0 opacity-0 cursor-pointer" id="imageInput" required/>
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
                            <div id="additional-images-preview" class="mt-4 grid grid-cols-3 sm:grid-cols-4 gap-2"></div>
                        </div>


                    </div>
                </form>
            </div>
        </div>
        <jsp:include page="/footer.jsp"/>
        <script>
            $(document).ready(function () {
                console.log("Document is ready!");
                if(localStorage.getItem("FormSave")){
                    console.log("FormSave is true");
                }

                // jQuery is library of Javascript
                // Show input image on #imagePreview
                $('#imageInput').on('change', function () {
                    var file = this.files[0];
                    if (file) {
                        var reader = new FileReader();
                        reader.onload = function(e) {
                            $('#imagePreview').attr('src', e.target.result).removeClass('hidden');
                            $('#imageLabel').hide();
                            $('#imageUploadContainer').removeClass('bg-gray-100 border-dashed border-gray-300').addClass('border-transparent');
                        };
                        reader.readAsDataURL(file);
                    } else {
                        $('#imagePreview').attr('src', '').addClass('hidden');
                        $('#imageLabel').show();
                        $('#imageUploadContainer').addClass('bg-gray-100 border-dashed border-gray-300').removeClass('border-transparent');
                    }
                });

                // Additional Images Upload Limit
                const maxImageInput = 15;
                $('#additional_images_input').on('change', function () {
                    const inputImages = this.files.length;
                    const previewContainer = $('#additional-images-preview');
                    previewContainer.empty(); // Clear previous previews

                    if (inputImages > maxImageInput) {
                        let errorMessage = "Maximum images selected is 15";
                        $('#image-upload-error').text(errorMessage);
                        alert(errorMessage);
                        $(this).val('');
                        return;
                    } else {
                        $('#image-upload-error').text('');
                    }

                    if (this.files) {
                        Array.from(this.files).forEach(file => {
                            let reader = new FileReader();
                            reader.onload = function(e) {
                                let img = $('<img>').attr('src', e.target.result)
                                        .addClass('w-full h-24 object-cover rounded-md');
                                previewContainer.append(img);
                            };
                            reader.readAsDataURL(file);
                        });
                    }
                });

                //AJAX
                // Function to load car brands into the select dropdown
                const brandSelect = $('#brand');
                function loadCarBrands() {
                    console.log("Entering loadCarBrands function in <script> tag");
                    brandSelect.html('<option disabled selected>Pick a Brand</option>') ;
                    return $.ajax({
                        url : '${pageContext.request.contextPath}/FormGetBrandServlet' ,
                        type : 'GET' ,
                        dataType : 'html' ,
                        success : function(response) {
                            console.log("loadCarBrands success") ;
                            brandSelect.append(response);
                            clearModels() ;
                            modelSelect.prop('disabled', true);
                            clearSubModels() ;
                            subModelSelect.prop('disabled', true);
                        },
                        error : function(error) {
                            console.log("loadCarBrands error") ;
                            console.log(error);
                            // Propagate the error for $.when() to catch it
                            return $.Deferred().reject(error).promise();
                        }
                    })
                }
                const bodyTypeSelect = $('#body_type');
                function loadBodyTypes() {
                    console.log("Entering loadBodyTypes function in <script> tag");
                    bodyTypeSelect.html('<option disabled selected>Loading Body Types...</option>');
                    return $.ajax({
                        url: '${pageContext.request.contextPath}/AJAXBodyTypeCarForm',
                        type: 'GET',
                        dataType: 'html',
                        success: function(response) {
                            console.log("loadBodyTypes success");
                            bodyTypeSelect.html('<option disabled selected>Pick a Body Type</option>');
                            bodyTypeSelect.append(response);
                        },
                        error: function(error) {
                            console.log("loadBodyTypes error");
                            console.log(error);
                            bodyTypeSelect.html('<option disabled selected>Error loading data</option>');
                            // Propagate the error for $.when() to catch it
                            return $.Deferred().reject(error).promise();
                        }
                    });
                }

                // Use $.when to handle multiple AJAX calls and control form readiness
                const submitBtn = $('#submitBtn'); // Ensure submitBtn is defined here
                $.when(loadCarBrands(), loadBodyTypes()).done(function() {
                    // This runs when ALL promises are resolved successfully
                    console.log("All initial data loaded successfully.");
                    submitBtn.prop('disabled', false).text('Submit Car Information');
                    loadFormFromLocalStorage(); // Load saved data only after dropdowns are populated
                }).fail(function() {
                    // This runs if ANY promise is rejected
                    console.error("Failed to load initial form data.");
                    submitBtn.text('Failed to load form. Please refresh.').prop('disabled', true);
                    // Show a more prominent error message on the page
                    $('#carForm').prepend('<div class="text-red-500 text-center font-bold mb-4">Error: Could not load required car data. Please refresh the page.</div>');
                });

                //Function to load Model of Brand that user Selected
                const modelSelect = $('#model');
                function loadCarModels() {
                    console.log("Entering loadCarModels function in <script> tag");
                    clearModels() ;
                    return $.ajax({
                        // must have url , type , data , dataType , success , error
                        url : '${pageContext.request.contextPath}/FormGetModelServlet' ,
                        type : 'GET' ,
                        data : { brandId : brandSelect.val() },
                        dataType : 'html' ,
                        success : function(response) {
                            console.log("loadCarModels success") ;
                            modelSelect.append(response);
                            modelSelect.prop('disabled', false);
                            clearSubModels() ;
                            subModelSelect.prop('disabled', true);
                        },
                        error : function(error) {
                            console.log("loadCarModels error") ;
                            console.log(error);
                        }
                    })
                }
                function clearModels() {
                    modelSelect.html('<option value="" disabled selected>Pick a model</option>') ;
                }
                brandSelect.on('change', function() {
                    loadCarModels();
                });
                //Function to load Car Sub-Model of Model that user Selected
                const subModelSelect = $('#sub_model');
                function loadCarSubModels() {
                    console.log("Entering loadCarSubModels function in <script> tag");
                    clearSubModels() ;
                    return $.ajax({
                        url : '${pageContext.request.contextPath}/FormGetSubModelServlet' ,
                        type : 'GET' ,
                        data : { modelId : modelSelect.val() },
                        dataType : 'html' ,
                        success : function(response) {
                            console.log("loadCarSubModels success") ;
                            if(response && response.trim() !== '') {
                                subModelSelect.append(response);
                                subModelSelect.prop('disabled', false);
                            } else {
                                subModelSelect.html('<option value="">-- No sub-models available --</option>');
                                subModelSelect.prop('disabled', true);
                            }
                        },
                        error : function(error) {
                            console.log("loadCarSubModels error") ;
                            console.log(error);
                        }
                    })
                }
                function clearSubModels() {
                    subModelSelect.html('<option value="" disabled selected>Pick a Sub-model</option>') ;
                }
                modelSelect.on('change', function() {
                    loadCarSubModels()
                });

                //Use LocalStorage
                const form = $('#carForm');
                function saveFormIntoLocalStorage() {
                    $('input, textarea, select').each(function() {
                        const item = $(this) ;
                        if(item.attr('type') !== 'file' && item.attr('name') !== '') {
                            localStorage.setItem("carForm_"+item.attr('name'), item.val());
                            console.log(item.attr('name') + " : " + item.val()) ;
                        }
                    })
                    localStorage.setItem("FormSave",true) ; 
                }
                $('input, textarea, select').on('change', function() {
                    saveFormIntoLocalStorage();
                });

                function clearFormData() {
                    console.log("Clearing form data from localStorage");
                    for (let i = localStorage.length - 1; i >= 0; i--) {
                        const key = localStorage.key(i);
                        if (key && (key.startsWith("carForm_") || key === "FormSave")) {
                            localStorage.removeItem(key);
                        }
                    }
                }

                form.on('submit', function(e) {
                    // This check is now a secondary safety measure, as the button should be disabled if data isn't loaded
                    const brandSelect = $('#brand');
                    const modelSelect = $('#model');
                    const bodyTypeSelect = $('#body_type');

                    if (!brandSelect.val() || !modelSelect.val() || modelSelect.is(':disabled') || !bodyTypeSelect.val()) {
                        e.preventDefault(); // Stop form submission
                        alert('Please ensure all required fields, including Brand, Model, and Body Type, are selected.');
                        return;
                    }
                    clearFormData();
                });

                function loadFormFromLocalStorage() {
                    if(localStorage.getItem("FormSave") === 'true' && confirm("You have unsubmitted form data. Do you want to load it?")) {
                        loadCarBrands().done(function() {
                            console.log("Start assign value for brand from localstorage") ;
                            brandSelect.val(localStorage.getItem("carForm_brand"));
                            if(brandSelect.val() !== null) {
                                loadCarModels().done(function() {
                                    console.log("Start assign value for model from localstorage") ;
                                    modelSelect.val(localStorage.getItem("carForm_model"));
                                    if(modelSelect.val() !== null) {
                                        loadCarSubModels().done(function() {
                                            console.log("Start assign value for sub_model from localstorage") ;
                                            subModelSelect.val(localStorage.getItem("carForm_sub_model"));
                                        })
                                    }
                                })
                            }
                        })

                        $('input , textarea').not('[type="file"]').each(function() {
                            const item = $(this) ;
                            if(item.attr('name') !== ''){
                                item.val(localStorage.getItem("carForm_"+item.attr('name')));
                            }
                        })
                    }
                }

                //Gear Fetch
                function loadGear() {
                    console.log("Entering loadGear function in <script> tag");
                    return $.ajax({
                        url : '${pageContext.request.contextPath}/FormGetGearServlet' ,
                    })
                }
            });
        </script>
    </body>
</html>