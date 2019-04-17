$(document).ready(function() {

    
     // Change the name of the user
    $('#manager-name').html(sessionStorage.getItem('username'));


    $("#logout").click(function() {
        sessionStorage.removeItem('username');
        sessionStorage.removeItem('user_type');
        sessionStorage.removeItem('user_ID');
        window.location.href = 'login.html';
    });
    
    
    
    
    
    if (window.location.pathname == '/website/appointments.html'){
        
        if (sessionStorage.getItem('user_type') != 3) {
            window.location.href = '/website/login.html';
        }
        
        $("#logo_img").click(function() {
            window.location.href = '/website/clinicInfo.html';
        });
        
        $('#patientLink').click(function(){
            $('#patientInformationPanel').toggle();
        });
        
        $('#doctorLink').click(function(){
            $('#dentistInformationPanel').toggle();
        });
        
        
        $('#confirmText').click(function(){
            $('#appintmentStatusPanel').toggle();
        });
        
        
        $('#confirmButton').click(function(){
            // Do some Query here
        });
        var user_ID =  sessionStorage.getItem('user_ID');
        
    
        
        $.post("/website/assets/php/appointments.php", {
            key:1,
            user_ID: user_ID
        }, function(data, status) {
            var tableBody = "";
             var dataa = JSON.parse(data);
            $.each(dataa, function() {
           
                
                tableBody += "<tr><th scope='row'>" + this.appointment_ID + "</th>";
                tableBody += "<td>" + this.reservation_date + "</td>";
                tableBody += "<td>" + this.apm_date + "</td>";
                tableBody += "<td>" + this.apm_type + "</td>";
                tableBody += "<td>" + this.pfname + " " + this.plname + "</td>";
                tableBody += "<td>" + this.dfname + " " + this.dlname + "</td>";
                if(this.status_ID == 11) {
                    // appoinment needs confirmation
                    tableBody += "<td><button id = 'confirmButton' class='edit btn btn-light' value='" + this.appointment_ID + "'> Confirm </button>";
                    tableBody += "<button id = 'rejectButton' class='reject btn btn-light' value='" + this.appointment_ID + "'> Reject </button></td>";
                }else{
                    tableBody += "<td id='confirmText' style='text-align:center; color:#f10000; cursor:pointer;' value='"+this.appointment_ID+"'>"+ this.status_name;
                }

                tableBody += "</td></tr>";
                $('#managers-table').html(tableBody);
            });
        }).done(function(){
            
            $("#appointmentsLogout").on('click',function() {
                sessionStorage.removeItem('username');
                sessionStorage.removeItem('user_type');
                sessionStorage.removeItem('user_ID');
                window.location.href = 'login.html';
            });
            
            var selectedID = null;
            $('.table tbody').on('click', '.edit', function() {
                selectedID = parseInt($(this).closest('tr').find('#confirmButton').attr('value'));
                $.post("/website/assets/php/appointments.php", {
                    key:2,
                    user_ID: user_ID,
                    appointment_ID: selectedID,
                    status_ID:15
                }, function(data, status) {
                    if (!data.includes("ERROR")) {
                         var dataa = JSON.parse(data);
                        var tableBody = "";
                        $.each(dataa, function() {
                       
                            
                            tableBody += "<tr><th scope='row'>" + this.appointment_ID + "</th>";
                            tableBody += "<td>" + this.reservation_date + "</td>";
                            tableBody += "<td>" + this.apm_date + "</td>";
                            tableBody += "<td>" + this.apm_type + "</td>";
                            tableBody += "<td>" + this.pfname + " " + this.plname + "</td>";
                            tableBody += "<td>" + this.dfname + " " + this.dlname + "</td>";
                            if(this.status_ID == 11) {
                                // appoinment needs confirmation
                                tableBody += "<td style='text-align: center'><button id = 'confirmButton' class='edit btn btn-light' value='" + this.appointment_ID + "'> Confirm </button>";
                                tableBody += "<button id = 'rejectButton' class='reject btn btn-light' value='" + this.appointment_ID + "'> Reject </button></td>";
                            }else{
                                tableBody += "<td id='confirmText' style='text-align:center; color:#f10000; cursor:pointer;' value='"+this.appointment_ID+"'>"+ this.status_name;
                            }
                            tableBody += "</td></tr>";
                            $('#managers-table').html(tableBody);
                        });
                    }
                });
            });
            
            $('.table tbody').on('click', '.reject', function(){
                
                var comment = prompt("Please Enter a comment for rejection: ","");
                while(comment == null){
                    comment = prompt("Please Enter a comment for rejection: ","");
                }
                
                selectedID = parseInt($(this).closest('tr').find('#confirmButton').attr('value'));
                $.post("/website/assets/php/appointments.php", {
                    key:2,
                    user_ID: user_ID,
                    appointment_ID: selectedID,
                    comment: comment,
                    status_ID: 14
                }, function(data, status) {
                    if (!data.includes("ERROR")) {
                        var dataa = JSON.parse(data);
                        var tableBody = "";
                        $.each(dataa, function() {
                            tableBody += "<tr><th scope='row'>" + this.appointment_ID + "</th>";
                            tableBody += "<td>" + this.reservation_date + "</td>";
                            tableBody += "<td>" + this.apm_date + "</td>";
                            tableBody += "<td>" + this.apm_type + "</td>";
                            tableBody += "<td>" + this.pfname + " " + this.plname + "</td>";
                            tableBody += "<td>" + this.dfname + " " + this.dlname + "</td>";
                            if(this.status_ID == 11) {
                                // appoinment needs confirmation
                               tableBody += "<td style='text-align: center'><button id = 'confirmButton' class='edit btn btn-light' value='" + this.appointment_ID + "'> Confirm </button>";
                                tableBody += "<button id = 'rejectButton' class='reject btn btn-light' value='" + this.appointment_ID + "'> Reject </button></td>";
                            }else{
                                tableBody += "<td id='confirmText' style='text-align:center; color:#f10000; cursor:pointer;' value='"+this.appointment_ID+"'>"+ this.status_name;
                            }
                            tableBody += "</td></tr>";
                            $('#managers-table').html(tableBody);
                        });
                    }
                });
            });
            
            $('.table tbody').on('click', '#confirmText', function() {
                $('#appintmentStatusPanel').toggle();
                 selectedID = parseInt($(this).closest('tr').find('#confirmText').attr('value'));
            });
            
             $('#changeStatusButton').on('click', function() {
                selectedStatus_ID = $('#appintmentStatusPanel select[name="statusSelect"]').val();
                console.log(selectedStatus_ID);
                 $.post("/website/assets/php/appointments.php", {
                    key:3,
                    user_ID: user_ID,
                    appointment_ID: selectedID,
                    status_ID:selectedStatus_ID
                    
                }, function(data, status) {
                    if (!data.includes("ERROR")) {
                        $('#appintmentStatusPanel').toggle();
                        
                        var dataa = JSON.parse(data);
                        var tableBody = "";
                        $.each(dataa, function() {
                            tableBody += "<tr><th scope='row'>" + this.appointment_ID + "</th>";
                            tableBody += "<td>" + this.reservation_date + "</td>";
                            tableBody += "<td>" + this.apm_date + "</td>";
                            tableBody += "<td>" + this.apm_type + "</td>";
                            tableBody += "<td>" + this.pfname + " " + this.plname + "</td>";
                            tableBody += "<td>" + this.dfname + " " + this.dlname + "</td>";
                            if(this.status_ID == 11) {
                                // appoinment needs confirmation
                                tableBody += "<td style='text-align: center'><button id = 'confirmButton' class='edit btn btn-light' value='" + this.appointment_ID + "'> Confirm </button>";
                                tableBody += "<button id = 'rejectButton' class='reject btn btn-light' value='" + this.appointment_ID + "'> Reject </button></td>";
                            }else{
                                tableBody += "<td id='confirmText' style='text-align:center; color:#f10000; cursor:pointer;' value='"+this.appointment_ID+"'>"+ this.status_name;
                            }
                            tableBody += "</td></tr>";
                            $('#managers-table').html(tableBody);
                        });
                    }
                });
                 
                 
            });
            
            
        });
        
    }
    
    
    if (window.location.pathname == '/website/clinicInfo.html') {
        if (sessionStorage.getItem('user_type') != 2) {
            window.location.href = '/website/login.html';
        }
        
        $("#clickInfoLogout").on('click',function() {
                sessionStorage.removeItem('username');
                sessionStorage.removeItem('user_type');
                sessionStorage.removeItem('user_ID');
                window.location.href = 'login.html';
        });
        
         $("#logo_img").click(function() {
            window.location.href = '/website/manager.html';
        });
        
        var clinicman_ID =  sessionStorage.getItem('user_ID');
        var clinic_ID = null;
        $.getJSON("/website/assets/php/clinics.php", {
                key: 5,
                clinicman_ID: clinicman_ID
            },
            function(result) {
                    $("input[name='name']").val(result[0].clinic_name);
                    $("#clinicNameText").text(result[0].clinic_name);
            
                    $("textarea[name='description']").val(result[0].clinic_description);
                    $("#clinicDescriptionText").text(result[0].clinic_description);
            
                    $("textarea[name='location']").val(result[0].location);
                    $("#clinicLocationText").text(result[0].location);

                    $("input[name='website']").val(result[0].website);
                    $("#clinicWebsiteText").text(result[0].website);
                    $("#clinicWebsiteLink").attr("href",result[0].website);
            
                    $("input[name='email']").val(result[0].email);
                    $("#clinicEmailText").text(result[0].email);
                    
                    $("select[name='status']").val(result[0].status_ID);
                    $("#clinicStatusText").text(result[0].status_name);
            
                    $("#clinicIDText").text("Clinic ID #"+result[0].clinic_ID);
                    clinic_ID = result[0].clinic_ID;
                    
            
            }).done(function() {
            
            $('#editClinicInfo').on('click', function() {
                $('#editPanel').fadeIn();
                $('#saveClinicInfo').click(function(){
                    
                    var clinic_name = $('#clinicInformationForm').find('input[name="name"]').val();
                    var website = $('#clinicInformationForm').find('input[name="website"]').val();
                    var email = $('#clinicInformationForm').find('input[name="email"]').val();
                    var status_ID = $('#clinicInformationForm option:selected').val();
                    var status_name = $('#clinicInformationForm option:selected').text();
                    var clinic_description = $('#clinicInformationForm').find('textarea[name="description"]').val();
                    var location = $('#clinicInformationForm').find('textarea[name="location"]').val();

                    $.post("/website/assets/php/clinics.php", {
                            key: 3,
                            clinic_name: clinic_name,
                            clinic_description: clinic_description,
                            website: website,
                            email: email,
                            location: location,
                            clinic_ID: clinic_ID,
                            status_ID: status_ID
                        },
                        function(data, status) {

                            if (!data.includes("ERROR")) {
                                $("#clinicInfoResponseMessage").css('display','inline-block');
                                $("#clinicInfoResponseMessage").text("Updated Successfully!");
                                
                                setTimeout(function() {
                                    $("#clinicInfoResponseMessage").hide('blind', {}, 500)
                                }, 5000);
                                
                                $("#clinicNameText").text(clinic_name);
            
                                $("#clinicDescriptionText").html(clinic_description);

                                $("#clinicLocationText").text(location);

                                $("#clinicWebsiteText").text(website);
                                
                                $("#clinicWebsiteLink").attr("href",website);

                                $("#clinicEmailText").text(email);

                                $("#clinicStatusText").text(status_name);

                            } else {
                                console.log(data);
                            }

                        });

                    });


                });

                $('#cancelClinicInfo').click(function() {
                    $("#editPanel").fadeOut();
                });
            
        });
        
    }
        
        
    if (window.location.pathname == '/website/manager.html') {
        if (sessionStorage.getItem('user_type') != 2) {
            window.location.href = '/website/login.html';
        }
         $("#logo_img").click(function() {
            window.location.href = '/website/manager.html';
        });
        
        $("#managerLogout").click(function() {
            sessionStorage.removeItem('username');
            sessionStorage.removeItem('user_type');
            sessionStorage.removeItem('user_ID');
            window.location.href = 'login.html';
        });
        
        var clinicman_ID =  sessionStorage.getItem('user_ID');
        // Set the information of the home page
        $.get("/website/assets/php/managers.php",
            {
                key: 7,
            user_ID: clinicman_ID
            },
            function(data, status) {
         
                if (!data.includes("ERROR")) {

                    $('#appointmentsText').animateNumber({
                        number: data.split(',')[0]
                    });
                    $('#receptionistsText').animateNumber({
                        number: data.split(',')[1]
                    });
                    $('#dentistsText').animateNumber({
                        number: data.split(',')[2]
                    });
                    $('#usersText').animateNumber({
                        number: data.split(',')[3]
                    });
                    $('#advertisementsText').animateNumber({
                        number: data.split(',')[4]
                    });
                    $('#feesText').animateNumber({
                        number: data.split(',')[5]
                    });
                }
            });
        
       
         $('#addReceptionistButton').click(function() {
            var fname = $('#addReciptionistForm').find('input[name="fname"]').val();
            var lname = $('#addReciptionistForm').find('input[name="lname"]').val();
            var email = $('#addReciptionistForm').find('input[name="email"]').val();
            var username = $('#addReciptionistForm').find('input[name="username"]').val();
            var password = $('#addReciptionistForm').find('input[name="password"]').val();
            var gender = $('#addReciptionistForm').find('select[name="gender"]').val();
            
            $.post("/website/assets/php/managers.php", {
                    key: 6,
                    username: username,
                    password: password,
                    fname: fname,
                    lname: lname,
                    email: email,
                    gender: gender,
                    clinicman_ID: clinicman_ID
                },
                function(data, status) {
                    if (!data.includes("ERROR")) {
                        $('#addReciptionistFormResponse').html(data);
                        $('#addReciptionistForm').trigger("reset");
                    } else {
                        $('#addReciptionistFormResponse').html(data);
                    }
                });

        });
        
        
        
        $('#addDentistButton').click(function() {
            var fname = $('#addDentistForm').find('input[name="fname"]').val();
            var lname = $('#addDentistForm').find('input[name="lname"]').val();
            var start_career_date = $('#addDentistForm').find('input[name="start_career_date"]').val();
            var bio = $('#addDentistForm').find('textarea[name="bio"]').val();
            var email = $('#addDentistForm').find('input[name="email"]').val();
            var website = $('#addDentistForm').find('input[name="website"]').val();
            var clinic_office = $('#addDentistForm').find('input[name="clinic_office"]').val();
            var clinic_number = $('#addDentistForm').find('input[name="clinic_number"]').val();
            var specialty_ID = $('#addDentistForm').find('select[name="specialty_ID"]').val();
            console.log(fname+lname+start_career_date+bio+email+website+clinic_office+clinic_number+specialty_ID);
            $.post("/website/assets/php/managers.php", {
                    key: 8,
                    fname: fname,
                    lname: lname,
                    start_career_date: start_career_date,
                    bio: bio,
                    email: email,
                    website: website,
                    clinic_office: clinic_office,
                    clinic_number: clinic_number,
                    specialty_ID: specialty_ID,
                    clinicman_ID: clinicman_ID
                },
                function(data, status) {
                    if (!data.includes("ERROR")) {
                        $('#addDentistFormResponse').html(data);
                        $('#addDentistForm').trigger("reset");
                    } else {
                        $('#addDentistFormResponse').html(data);
                    }
                });

        });
        
        
        $('#addAdvertismentButton').click(function() {
            var start_date = $('#addAdvertismentForm').find('input[name="start_date"]').val();
            var end_date = $('#addAdvertismentForm').find('input[name="end_date"]').val();
            var title = $('#addAdvertismentForm').find('input[name="title"]').val();
            var content = $('#addAdvertismentForm').find('textarea[name="content"]').val();
            var fees = $('#addAdvertismentForm').find('input[name="fees"]').val();
            console.log(start_date + end_date + title + content + fees + clinicman_ID);
            $.post("/website/assets/php/advertisement.php", {
                    key: 2,
                    start_date: start_date,
                    end_date: end_date,
                    title: title,
                    content: content,
                    fees: fees,
                    clinicman_ID:clinicman_ID
                },
                function(data, status) {
                    if (!data.includes("ERROR")) {
                        $('#addAdvertismentFormResponse').html(data);
                        $('#addAdvertismentForm').trigger("reset");
                    } else {
                        $('#addAdvertismentFormResponse').html(data);
                    }
                });

        });
        

        $('#datepicker0').datepicker({
          uiLibrary: 'bootstrap',
            format:'yyyy-mm-dd',
            startDate: '+d'
        });

        $('#datepicker1').datepicker({
          uiLibrary: 'bootstrap',
            format:'yyyy-mm-dd',
            startDate: '+d'
        });
        
         $('#datepicker2').datepicker({
          uiLibrary: 'bootstrap',
            format:'yyyy-mm-dd',
            
        });

        
        $('#datepicker0').change(function() {
            startDate = $(this).datepicker('getDate');
            $("#datepicker1").datepicker("option", "minDate", startDate );
        })

        $('#datepicker1').change(function() {
            endDate = $(this).datepicker('getDate');
            $("#datepicker0").datepicker("option", "maxDate", endDate );
            
            var t1=$('#datepicker0').val();
            t1=t1.split('-');
            dt_t1=new Date(t1[0],t1[1]-1,t1[2]); // YYYY,mm,dd format to create date object
            dt_t1_tm = dt_t1.getTime(); // time in milliseconds for day 1
            
            //alert(dt_t1_tm);
            var t2=$('#datepicker1').val();
            t2=t2.split('-');
            dt_t2=new Date(t2[0],t2[1]-1,t2[2]); // YYYY,mm,dd format to create date object
            dt_t2_tm=dt_t2.getTime(); // time in milliseconds for day 2
            var one_day = 24*60*60*1000; // hours*minutes*seconds*milliseconds
            var diff_days=Math.abs((dt_t2_tm-dt_t1_tm)/one_day) // difference in days
            $('input[name="fees"]').val((diff_days + 1)*20 + " SAR" );
            
            
            
            
        });
        


    }
    


    if (window.location.pathname == '/website/dentists.html') {
        
         // redirect to login page if the user is an Administrator
        if (sessionStorage.getItem('user_type') != 2) {
            window.location.href = '/website/login.html';
        }
        
        $("#dentistLogout").click(function() {
            sessionStorage.removeItem('username');
            sessionStorage.removeItem('user_type');
            sessionStorage.removeItem('user_ID');
            window.location.href = 'login.html';
        });
        
        $("#logo_img").click(function() {
            window.location.href = '/website/clinicInfo.html';
        });
        
        var clinicman_ID = sessionStorage.getItem('user_ID');
        
        $('#datepicker3').datepicker({
          uiLibrary: 'bootstrap',
            format:'yyyy-mm-dd'
        });
        var jsonResult = null;
        $.getJSON("/website/assets/php/managers.php", {
            key: 9,
            clinicman_ID: clinicman_ID
        }, function(result) {
            jsonResult = result;
            var tableBody = "";
            $.each(result, function() {
                tableBody += "<tr><th scope='row'>" + this.dentist_ID + "</th>";
                tableBody += "<td>" + this.fname + " " + this.lname + "</td>";
                tableBody += "<td>" + this.bio + "</td>";
                tableBody += "<td>" + this.email + "</td>";
                tableBody += "<td>" + this.clinic_office + "</td>";
                tableBody += "<td>" + this.clinic_number + "</td>";
                tableBody += "<td>" + this.status_name + "</td>";
                tableBody += "<td>" + this.specialty_name + "</td>";
                tableBody += "<td style='text-align:center;'>";
                tableBody += "<button id = 'edit' class='edit btn btn-light' value='" + this.dentist_ID + "'> <i  style='font-size:30px' class='fa fa-edit'></i></button>";
                // tableBody += "<button id = 'remove' class='btn btn-light' value='"+ this.user_ID+"'><i id = 'remove' value='"+this.user_ID+"' style='font-size:30px' class='fa fa-remove'></i></button>";
                tableBody += "</td></tr>";
                $('#managers-table').html(tableBody);
            });

        }).done(function() {

            var selectedID = null;
            var clinic_ID = null;
            $('.table tbody').on('click', '.edit', function() {
                selectedID = parseInt($(this).closest('tr').find('#edit').attr('value'));
                $.each(jsonResult, function(i, object) {

                    if (selectedID == object.dentist_ID) {
                        $("#editDentistForm").fadeIn();
                        $('html, body').animate({
                            scrollTop: $("#editDentistForm").offset().top + 50
                        }, 800);
                        $("input[name='fname']").val(object.fname);
                        $("input[name='lname']").val(object.lname);
                        $("input[name='start_career_date']").val(object.start_career_date);
                        $("textarea[name='bio']").val(object.bio);
                        $("input[name='email']").val(object.email);
                        $("input[name='clinic_office']").val(object.clinic_office);
                        $("input[name='clinic_number']").val(object.clinic_number);
                        $("select[name='status']").val(object.status_ID);
                        $("select[name='speciality']").val(object.speciality_ID);
                        clinic_ID = object.clinic_ID;
                    }
                });
            });

            $('.updateBTN').click(function() {
               
                
                var fname = $("input[name='fname']").val();
                var lname = $("input[name='lname']").val();
                var start_career_date = $("input[name='start_career_date']").val();
                var bio = $("textarea[name='bio']").val();
                var email = $("input[name='email']").val();
                var clinic_office = $("input[name='clinic_office']").val();
                var clinic_number = $("input[name='clinic_number']").val();
                var status_ID = $('#status_select option:selected').val();
                var speciality_ID = $('#speciality_select option:selected').val();
                var dentist_ID = selectedID;
                
                console.log(fname + lname + start_career_date + bio + email + clinic_office+ clinic_number + status_ID +  speciality_ID + dentist_ID + clinic_ID);
                $.post("/website/assets/php/managers.php", {
                        key: 10,
                        fname: fname,
                        lname: lname,
                        start_career_date: start_career_date,
                        bio: bio,
                        email: email,
                        clinic_office: clinic_office,
                        clinic_number: clinic_number,
                        status_ID: status_ID,
                        speciality_ID: speciality_ID,
                        dentist_ID: dentist_ID,
                        clinic_ID: clinic_ID,
                    
                        
                        
                    },
                    function(data, status) {
                        console.log(data);
                        if (!data.includes("ERROR")) {
                            $("#editDentistForm").fadeOut();

                            $('#editDentistForm form').trigger("reset");
      $.getJSON("/website/assets/php/managers.php", {
            key: 9,
          clinicman_ID:clinicman_ID
        }, function(result) {
            jsonResult = result;
            var tableBody = "";
            $.each(result, function() {
                tableBody += "<tr><th scope='row'>" + this.dentist_ID + "</th>";
                tableBody += "<td>" + this.fname + " " + this.lname + "</td>";
                tableBody += "<td>" + this.bio + "</td>";
                tableBody += "<td>" + this.email + "</td>";
                tableBody += "<td>" + this.clinic_office + "</td>";
                tableBody += "<td>" + this.clinic_number + "</td>";
                tableBody += "<td>" + this.status_name + "</td>";
                tableBody += "<td>" + this.specialty_name + "</td>";
                tableBody += "<td style='text-align:center;'>";
                tableBody += "<button id = 'edit' class='edit btn btn-light' value='" + this.dentist_ID + "'> <i  style='font-size:30px' class='fa fa-edit'></i></button>";
                // tableBody += "<button id = 'remove' class='btn btn-light' value='"+ this.user_ID+"'><i id = 'remove' value='"+this.user_ID+"' style='font-size:30px' class='fa fa-remove'></i></button>";
                tableBody += "</td></tr>";
                $('#managers-table').html(tableBody);
                                    });

                                });

                            $('#updateClinicResponseMessage').fadeIn();
                            $('#updateClinicResponseMessage').delay(1500).fadeOut();
                        } else {
                            console.log(data);
                        }

                    });
            });




            $('.closeBTN').click(function() {
                $("#editDentistForm").fadeOut();
                $('#editDentistForm form').trigger("reset");

            });

        });

        
    }
    
    
    if (window.location.pathname == '/website/admin.html') {
        if (sessionStorage.getItem('user_type') != 1) {
            window.location.href = '/website/login.html';
        }

         $("#logo_img").click(function() {
            window.location.href = '/website/admin.html';
        });

        // Set the information of the home page
        $.get("/website/assets/php/getInformation.php",
            function(data, status) {
                if (!data.includes("ERROR")) {

                    $('#usersText').animateNumber({
                        number: data.split(',')[0]
                    });
                    $('#clinicsText').animateNumber({
                        number: data.split(',')[1]
                    });
                    $('#appointmentsText').animateNumber({
                        number: data.split(',')[2]
                    });
                    $('#dentistsText').animateNumber({
                        number: data.split(',')[3]
                    });
                    $('#managersText').animateNumber({
                        number: data.split(',')[4]
                    });
                    

                }
            });



        $.getJSON("/website/assets/php/managers.php", {
                key: "1",
            },
            function(result) {
                var tableBody = "";
                $.each(result, function() {
                    tableBody += "<tr><th scope='row'>" + this.user_ID + "</th>";
                    tableBody += "<td>" + this.fname + " " + this.lname + "</td>";
                    tableBody += "<td>" + this.username + "</td>";
                    tableBody += "<td>" + this.email + "</td>";
                    tableBody += "<td>" + this.gender + "</td>";
                    tableBody += "<td>" + this.status_name + "</td>";
                    tableBody += "<td>" + this.clinic_name + "</td>";
                    $('#managers-table-modal').html(tableBody);
                });
            });


        $.getJSON("/website/assets/php/clinics.php", {
                key: "1",
            },
            function(result) {
                var tableBody = "";
                $.each(result, function() {
                    tableBody += "<tr><th scope='row'>" + this.clinic_ID + "</th>";
                    tableBody += "<td>" + this.clinic_name + "</td>";
                    tableBody += "<td>" + this.location + "</td>";
                    tableBody += "<td>" + this.website + "</td>";
                    tableBody += "<td>" + this.email + "</td>";
                    tableBody += "<td>" + this.status_name + "</td>";
                    tableBody += "<td>" + this.fname + " " + this.lname + "</td>";
                    $('#clinics-table-modal').html(tableBody);
                });
            });



        $('#addManagerButton').click(function() {
            var fname = $('#addManagerForm').find('input[name="fname"]').val();
            var lname = $('#addManagerForm').find('input[name="lname"]').val();
            var email = $('#addManagerForm').find('input[name="email"]').val();
            var username = $('#addManagerForm').find('input[name="username"]').val();
            var password = $('#addManagerForm').find('input[name="password"]').val();
            var gender = $('#addManagerForm').find('select[name="gender"]').val();
            $.post("/website/assets/php/managers.php", {
                    key: 2,
                    username: username,
                    password: password,
                    fname: fname,
                    lname: lname,
                    email: email,
                    gender: gender
                },
                function(data, status) {

                    if (!data.includes("ERROR")) {
                        $('#addMangerResponse').html(data);
                        $('#addManagerForm').trigger("reset");
                    } else {
                        $('#addMangerResponse').html(data);
                    }
                });

        });




        $('#addNewClinicButton').click(function() {
            $.getJSON("/website/assets/php/retriveFreeManagers.php", {
                key: 5
            }, function(result) {
                var $dropdown = $("#manager-list");
                $.each(result, function() {
                    $dropdown.append($("<option />").val(this.user_ID).text(this.fname));
                });

            });

        });


        $('#addClinicButton').click(function() {
            var clinic_name = $('#addClinicForm').find('input[name="clinic_name"]').val();
            var website = $('#addClinicForm').find('input[name="website"]').val();
            var email = $('#addClinicForm').find('input[name="email"]').val();
            var clinicman_ID = $('#addClinicForm option:selected').val();
            var clinic_description = $('#addClinicForm').find('input[name="clinic_description"]').val();
            var location = $('#addClinicForm').find('input[name="location"]').val();
            $.post("/website/assets/php/clinics.php", {


                    key: 2,
                    clinic_name: clinic_name,
                    clinic_description: clinic_description,
                    clinicman_ID: clinicman_ID,
                    website: website,
                    email: email,
                    location: location
                },
                function(data, status) {

                    if (!data.includes("ERROR")) {
                        $('#addClinicResponse').html(data);
                        $('#addClinicForm').trigger("reset");
                    } else {
                        $('#addClinicResponse').html(data);
                    }
                });

        });
    }
    if (window.location.pathname == '/website/managers.html') {


        if (sessionStorage.getItem('user_type') != 1) {
            window.location.href = '/website/login.html';
        }

        $("#logo_img").click(function() {
            window.location.href = '/website/admin.html';
        });


        var jsonResult = null;
        $.getJSON("/website/assets/php/managers.php", {
            key: "1",
        }, function(result) {
            jsonResult = result;
            var tableBody = "";
            $.each(result, function() {
                tableBody += "<tr><th scope='row'>" + this.user_ID + "</th>";
                tableBody += "<td>" + this.fname + " " + this.lname + "</td>";
                tableBody += "<td>" + this.username + "</td>";
                tableBody += "<td>" + this.hashed_pw + "</td>";
                tableBody += "<td>" + this.email + "</td>";
                tableBody += "<td>" + this.gender + "</td>";
                tableBody += "<td>" + this.status_name + "</td>";
                tableBody += "<td>" + this.clinic_name + "</td>";
                tableBody += "<td>" + this.reg_date + "</td>";
                tableBody += "<td style='text-align:center;'>";
                tableBody += "<button id = 'edit' class='edit btn btn-light' value='" + this.user_ID + "'> <i  style='font-size:30px' class='fa fa-edit'></i></button>";
                // tableBody += "<button id = 'remove' class='btn btn-light' value='"+ this.user_ID+"'><i id = 'remove' value='"+this.user_ID+"' style='font-size:30px' class='fa fa-remove'></i></button>";
                tableBody += "</td></tr>";
                $('#managers-table').html(tableBody);
            });

        }).done(function() {

            var selectedID = null;

            $('.table tbody').on('click', '.edit', function() {
                selectedID = parseInt($(this).closest('tr').find('#edit').attr('value'));
                console.log(jsonResult);
                $.each(jsonResult, function(i, object) {
                    console.log(selectedID);

                    if (selectedID == object.user_ID) {
                        $("#editManagerForm").fadeIn();
                        $('html, body').animate({
                            scrollTop: $("#editManagerForm").offset().top + 50
                        }, 800);
                        $("input[name='fname']").val(object.fname);
                        $("input[name='lname']").val(object.lname);
                        $("input[name='username']").val(object.username);
                        $("input[name='password']").val(object.hashed_pw);
                        $("input[name='email']").val(object.email);
                        $("select[name='gender']").val(object.gender);
                        $("select[name='status']").val(object.status_ID);
                    }
                });
            });

            $('.updateBTN').click(function() {
                var fname = $('#editManagerForm').find('input[name="fname"]').val();
                var lname = $('#editManagerForm').find('input[name="lname"]').val();
                var email = $('#editManagerForm').find('input[name="email"]').val();
                var username = $('#editManagerForm').find('input[name="username"]').val();
                var password = $('#editManagerForm').find('input[name="password"]').val();
                var status_ID = $('#status_select option:selected').val();
                var gender = $('#gender_select option:selected').val();

                console.log(fname + lname + email + password + username + status_ID + gender + status_ID + selectedID);
                $.post("/website/assets/php/managers.php", {
                        key: 3,
                        username: username,
                        password: password,
                        fname: fname,
                        lname: lname,
                        email: email,
                        gender: gender,
                        status_ID: status_ID,
                        user_ID: selectedID
                    },
                    function(data, status) {

                        if (!data.includes("ERROR")) {
                            $("#editManagerForm").fadeOut();

                            $('#editManagerForm form').trigger("reset");
                            $.getJSON("/website/assets/php/managers.php", {
                                    key: "1",
                                },
                                function(result) {
                                    jsonResult = result;
                                    var tableBody = "";
                                    $.each(result, function() {
                                        tableBody += "<tr><th scope='row'>" + this.user_ID + "</th>";
                                        tableBody += "<td>" + this.fname + " " + this.lname + "</td>";
                                        tableBody += "<td>" + this.username + "</td>";
                                        tableBody += "<td>" + this.hashed_pw + "</td>";
                                        tableBody += "<td>" + this.email + "</td>";
                                        tableBody += "<td>" + this.gender + "</td>";
                                        tableBody += "<td>" + this.status_name + "</td>";
                                        tableBody += "<td>" + this.clinic_name + "</td>";
                                        tableBody += "<td>" + this.reg_date + "</td>";
                                        tableBody += "<td style='text-align:center;'>";
                                        tableBody += "<button id = 'edit' class='edit btn btn-light' value='" + this.user_ID + "'> <i  style='font-size:30px' class='fa fa-edit'></i></button>";
                                        // tableBody += "<button id = 'remove' class='btn btn-light' value='"+ this.user_ID+"'><i id = 'remove' value='"+this.user_ID+"' style='font-size:30px' class='fa fa-remove'></i></button>";
                                        tableBody += "</td></tr>";
                                        $('#managers-table').html(tableBody);
                                    });

                                });

                            $('#updateClinicResponseMessage').fadeIn();
                            $('#updateClinicResponseMessage').delay(1500).fadeOut();
                        } else {
                            console.log(data);
                        }

                    });
            });




            $('.closeBTN').click(function() {
                $("#editManagerForm").fadeOut();
                $('#editManagerForm form').trigger("reset");

            });

        });


    }
    
    if (window.location.pathname == '/website/receptionists.html') {


        if (sessionStorage.getItem('user_type') != 2) {
            window.location.href = '/website/login.html';
        }

        $("#receptionistLogout").click(function() {
            sessionStorage.removeItem('username');
            sessionStorage.removeItem('user_type');
            sessionStorage.removeItem('user_ID');
            window.location.href = 'login.html';
        });
        $("#logo_img").click(function() {
            window.location.href = '/website/manager.html';
        });
        
        var clinicman_ID = sessionStorage.getItem('user_ID');

        var jsonResult = null;
        $.getJSON("/website/assets/php/managers.php", {
            key: 12,
            user_ID: clinicman_ID
        }, function(result) {
            console.log(result);
            jsonResult = result;
            var tableBody = "";
            $.each(result, function() {
                tableBody += "<tr><th scope='row'>" + this.user_ID + "</th>";
                tableBody += "<td>" + this.fname + " " + this.lname + "</td>";
                tableBody += "<td>" + this.username + "</td>";
                tableBody += "<td>" + this.hashed_pw + "</td>";
                tableBody += "<td>" + this.email + "</td>";
                tableBody += "<td>" + this.gender + "</td>";
                tableBody += "<td>" + this.status_name + "</td>";
                tableBody += "<td>" + this.reg_date + "</td>";
                tableBody += "<td style='text-align:center;'>";
                tableBody += "<button id = 'edit' class='edit btn btn-light' value='" + this.user_ID + "'> <i  style='font-size:30px' class='fa fa-edit'></i></button>";
                // tableBody += "<button id = 'remove' class='btn btn-light' value='"+ this.user_ID+"'><i id = 'remove' value='"+this.user_ID+"' style='font-size:30px' class='fa fa-remove'></i></button>";
                tableBody += "</td></tr>";
                $('#managers-table').html(tableBody);
            });

        }).done(function() {

            var selectedID = null;

            $('.table tbody').on('click', '.edit', function() {
                selectedID = parseInt($(this).closest('tr').find('#edit').attr('value'));
                console.log(jsonResult);
                $.each(jsonResult, function(i, object) {
                    console.log(selectedID);

                    if (selectedID == object.user_ID) {
                        $("#editManagerForm").fadeIn();
                        $('html, body').animate({
                            scrollTop: $("#editManagerForm").offset().top + 50
                        }, 800);
                        $("input[name='fname']").val(object.fname);
                        $("input[name='lname']").val(object.lname);
                        $("input[name='username']").val(object.username);
                        $("input[name='password']").val(object.hashed_pw);
                        $("input[name='email']").val(object.email);
                        $("select[name='gender']").val(object.gender);
                        $("select[name='status']").val(object.status_ID);
                    }
                });
            });

            $('.updateBTN').click(function() {
                var fname = $('#editManagerForm').find('input[name="fname"]').val();
                var lname = $('#editManagerForm').find('input[name="lname"]').val();
                var email = $('#editManagerForm').find('input[name="email"]').val();
                var username = $('#editManagerForm').find('input[name="username"]').val();
                var password = $('#editManagerForm').find('input[name="password"]').val();
                var status_ID = $('#status_select option:selected').val();
                var gender = $('#gender_select option:selected').val();

                console.log(fname + lname + email + password + username + status_ID + gender + status_ID + selectedID);
                $.post("/website/assets/php/managers.php", {
                        key: 3,
                        username: username,
                        password: password,
                        fname: fname,
                        lname: lname,
                        email: email,
                        gender: gender,
                        status_ID: status_ID,
                        user_ID: selectedID
                    },
                    function(data, status) {

                        if (!data.includes("ERROR")) {
                            $("#editManagerForm").fadeOut();

                            $('#editManagerForm form').trigger("reset");
                            $.getJSON("/website/assets/php/managers.php", {
                                    key: 12,
                                    user_ID: clinicman_ID
                                },
                                function(result) {
                                    jsonResult = result;
                                    var tableBody = "";
                                    $.each(result, function() {
                                        tableBody += "<tr><th scope='row'>" + this.user_ID + "</th>";
                                        tableBody += "<td>" + this.fname + " " + this.lname + "</td>";
                                        tableBody += "<td>" + this.username + "</td>";
                                        tableBody += "<td>" + this.hashed_pw + "</td>";
                                        tableBody += "<td>" + this.email + "</td>";
                                        tableBody += "<td>" + this.gender + "</td>";
                                        tableBody += "<td>" + this.status_name + "</td>";
                                        tableBody += "<td>" + this.reg_date + "</td>";
                                        tableBody += "<td style='text-align:center;'>";
                                        tableBody += "<button id = 'edit' class='edit btn btn-light' value='" + this.user_ID + "'> <i  style='font-size:30px' class='fa fa-edit'></i></button>";
                                        // tableBody += "<button id = 'remove' class='btn btn-light' value='"+ this.user_ID+"'><i id = 'remove' value='"+this.user_ID+"' style='font-size:30px' class='fa fa-remove'></i></button>";
                                        tableBody += "</td></tr>";
                                        $('#managers-table').html(tableBody);
                                    });

                                });

                            $('#updateClinicResponseMessage').fadeIn();
                            $('#updateClinicResponseMessage').delay(1500).fadeOut();
                        } else {
                            console.log(data);
                        }

                    });
            });




            $('.closeBTN').click(function() {
                $("#editManagerForm").fadeOut();
                $('#editManagerForm form').trigger("reset");

            });

        });


    }
    
    
    if (window.location.pathname == '/website/clinics.html') {

        // redirect to login page if the user is an Administrator
        if (sessionStorage.getItem('user_type') != 1) {
            window.location.href = '/website/login.html';
        }
        
        $("#clinicsLogout").click(function() {
            sessionStorage.removeItem('username');
            sessionStorage.removeItem('user_type');
            sessionStorage.removeItem('user_ID');
            window.location.href = 'login.html';
        });

        $("#logo_img").click(function() {
            window.location.href = '/website/admin.html';
        });

        
        var jsonResult = null;
        $.getJSON("/website/assets/php/clinics.php", {
                key: "1",
            },
            function(result) {
                jsonResult = result;
                var tableBody = "";

                $.each(result, function() {
                    tableBody += "<tr><th scope='row'>" + this.clinic_ID + "</th>";
                    tableBody += "<td>" + this.clinic_name + "</td>";
                    tableBody += "<td>" + this.clinic_description + "</td>";
                    tableBody += "<td>" + this.location + "</td>";
                    tableBody += "<td>" + this.website + "</td>";
                    tableBody += "<td>" + this.email + "</td>";
                    tableBody += "<td>" + this.status_name + "</td>";
                    tableBody += "<td>" + this.fname + " " + this.lname + "</td>";
                    tableBody += "<td style='text-align:center;'>";
                    tableBody += "<button id = 'edit' class='edit btn btn-light' value='" + this.clinic_ID + "'> <i  style='font-size:30px' class='fa fa-edit'></i></button>";
                    // tableBody += "<button id = 'remove' class='btn btn-light' value='"+ this.user_ID+"'><i id = 'remove' value='"+this.user_ID+"' style='font-size:30px' class='fa fa-remove'></i></button>";
                    tableBody += "</td></tr>";

                    $('#clinics-table').html(tableBody);
                });

            }).done(function() {
            var selectedID = null;
            $('.table tbody').on('click', '.edit', function() {
                selectedID = parseInt($(this).closest('tr').find('#edit').attr('value'));
                $.each(jsonResult, function(i, object) {


                    if (selectedID == object.clinic_ID) {



                        $("#editClinicForm").fadeIn();
                        $('html, body').animate({
                            scrollTop: $("#editClinicForm").offset().top + 50
                        }, 800);
                        $("input[name='clinic_name']").val(object.clinic_name);
                        $("textarea[name='clinic_description']").val(object.clinic_description);
                        $("textarea[name='location']").val(object.location);
                        $("input[name='website']").val(object.website);
                        $("input[name='email']").val(object.email);
                        $("#status_select").val(object.status_ID);
                    }
                });

            });



            $('.updateBTN').click(function() {
                var clinic_name = $('#editClinicForm').find('input[name="clinic_name"]').val();
                var website = $('#editClinicForm').find('input[name="website"]').val();
                var email = $('#editClinicForm').find('input[name="email"]').val();
                var status_ID = $('#editClinicForm option:selected').val();
                var clinic_description = $('#editClinicForm').find('textarea[name="clinic_description"]').val();
                var location = $('#editClinicForm').find('textarea[name="location"]').val();
                var clinic_ID = $('#editClinicForm').find('textarea[name="location"]').val();
                console.log(status_ID);
                $.post("/website/assets/php/clinics.php", {
                        key: 3,
                        clinic_name: clinic_name,
                        clinic_description: clinic_description,
                        website: website,
                        email: email,
                        location: location,
                        clinic_ID: selectedID,
                        status_ID: status_ID
                    },
                    function(data, status) {

                        if (!data.includes("ERROR")) {
                            $("#editClinicForm").fadeOut();

                            $('#editClinicForm form').trigger("reset");
                            $.getJSON("/website/assets/php/clinics.php", {
                                    key: "1",
                                },
                                function(result) {
                                    jsonResult = result;
                                    var tableBody = "";
                                    $.each(result, function() {
                                        tableBody += "<tr><th scope='row'>" + this.clinic_ID + "</th>";
                                        tableBody += "<td>" + this.clinic_name + "</td>";
                                        tableBody += "<td>" + this.clinic_description + "</td>";
                                        tableBody += "<td>" + this.location + "</td>";
                                        tableBody += "<td>" + this.website + "</td>";
                                        tableBody += "<td>" + this.email + "</td>";
                                        tableBody += "<td>" + this.status_name + "</td>";
                                        tableBody += "<td>" + this.fname + " " + this.lname + "</td>";
                                        tableBody += "<td style='text-align:center;'>";
                                        tableBody += "<button id = 'edit' class='edit btn btn-light' value='" + this.clinic_ID + "'> <i  style='font-size:30px' class='fa fa-edit'></i></button>";
                                        // tableBody += "<button id = 'remove' class='btn btn-light' value='"+ this.user_ID+"'><i id = 'remove' value='"+this.user_ID+"' style='font-size:30px' class='fa fa-remove'></i></button>";
                                        tableBody += "</td></tr>";
                                        $('#clinics-table').html(tableBody);
                                    });

                                });
                            $('#updateClinicResponseMessage').fadeIn();
                            $('#updateClinicResponseMessage').delay(1500).fadeOut();
                        } else {
                            console.log(data);
                        }

                    });
            });




            $('.closeBTN').click(function() {
                $("#editClinicForm").fadeOut();
                $('#editClinicForm form').trigger("reset");

            });
        });
    }
    
    
    
    $('#login-form').submit(function(e) {
        e.preventDefault();
        var email = $("#username").val();
        var pass = $("#password").val();
        $.post("/website/assets/php/login.php", {
                username: email,
                password: pass
            },
            function(data, status) {

                if (!data.includes("ERROR")) {


                    sessionStorage.setItem('username', data.split(",")[1] + " " + data.split(",")[2]);
                    sessionStorage.setItem('user_type', data.split(",")[3]);
                    sessionStorage.setItem('user_ID', data.split(",")[0]);
                    if (data.split(",")[3] == 1) {
                        window.location.href = 'admin.html';
                    } else if (data.split(",")[3] == 2) {
                        window.location.href = 'manager.html';
                    } else if (data.split(",")[3] == 3) {
                        window.location.href = 'appointments.html';
                    } else {
                        $('#login-status').show();
                        $('#login-status').html("You don't have access to this website.");
                    }
                } else {
                    $('#login-status').show();
                    $('#login-status').html(data);
                }

            });




    });

   

});