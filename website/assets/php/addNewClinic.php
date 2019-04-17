<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
require_once('index.php');
    $clinicman_ID=$website=$location=$services=$email=$clinic_profile="";
    $status_ID=13;
   


    if(isset($_REQUEST['clinic_profile'])
    && isset($_REQUEST['services'])
    && isset($_REQUEST['location'])
    && isset($_REQUEST['website'])
    && isset($_REQUEST['email'])
    && isset($_REQUEST['clinicman_ID'])
    ){
        $clinic_profile         = $_REQUEST['clinic_profile'];
        $services               = $_REQUEST['services'];
        $location               = $_REQUEST['location'];
        $website                = $_REQUEST['website'];
        $email                  = $_REQUEST['email'];
        $clinicman_ID           = $_REQUEST['clinicman_ID'];
    
    }else{
        echo "ERROR: The information is not complete\r\n ";
    }





    // Create a new ID ==========================================
    $a_new_id = "7029090";
    if($result = $conn->query("SELECT * FROM user")){
        /* determine number of rows result set */
        $row_count = $result->num_rows;
        /* close result set */
        $result->close();
    }

    if(((int)($row_count+3)) < 10)
    $a_new_id = $a_new_id ."0". ($row_count+1);
    else
    $a_new_id = $a_new_id . ($row_count+1);
    // ===========================================================
    
    




    $queryToInsert = "INSERT INTO `clinic` (`clinic_ID`, `clinic_profile`, `services`, `location`, `website`, `email`, `clinicman_ID`, `status_ID`) VALUES (`$a_new_id`, `$clinic_profile`, `$services`,  `$location`, `$website`, `$email`, $clinicman_ID , $status_ID)";
    
    if($result = $conn->query($queryToInsert)){
        echo "Registered!";    
    }else{
        echo "ERROR: " . mysqli_error($conn);
    }





   





?>