<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
require_once('index.php');


    if(isset($_REQUEST['user_ID'])
    ){
        $user_ID            = $_REQUEST['user_ID'];
    }





    $query = "  SELECT *
                FROM appointment a
                JOIN receptionist r ON a.recept_ID = r.recept_ID
                WHERE r.clinic_ID IN (
                            SELECT c.clinic_ID
                            FROM clinic c
                            JOIN user u on c.clinicman_ID = u.user_ID
                            WHERE clinicman_ID = $user_ID);            
                ";
    if($result = $conn->query($query)){
        /* determine number of rows result set */
        $row_count = $result->num_rows;

        if(((int) $row_count) > 0){
            echo $row_count.",";
        }else{
            echo "0,";
        }
    }else{
        echo "ERROR: " . mysqli_error($conn);
    }


    
    $query = "  SELECT *
                FROM receptionist a
                WHERE a.clinic_ID IN (
                            SELECT c.clinic_ID
                            FROM clinic c
                            JOIN user u on c.clinicman_ID = u.user_ID
                            WHERE clinicman_ID = $user_ID);";

        if($result = $conn->query($query)){
        /* determine number of rows result set */
        $row_count = $result->num_rows;

        if(((int) $row_count) > 0){
            echo $row_count.",";
        }else{
            echo "0,";
        }
    }else{
        echo "ERROR: " . mysqli_error($conn);
    }

    $query = "  SELECT *
                FROM dentist d
                WHERE d.clinic_ID IN (
                            SELECT c.clinic_ID
                            FROM clinic c
                            JOIN user u on c.clinicman_ID = u.user_ID
                            WHERE clinicman_ID = $user_ID);";

    if($result = $conn->query($query)){
        /* determine number of rows result set */
        $row_count = $result->num_rows;

        if(((int) $row_count) > 0){
            echo $row_count.",";
        }else{
            echo "0,";
        }
    }else{
        echo "ERROR: " . mysqli_error($conn);
    }






    $query = "  SELECT DISTINCT a.patient_ID
                FROM appointment a
                JOIN dentist d ON a.dentist_ID = d.dentist_ID
                WHERE d.clinic_ID IN (
                            SELECT c.clinic_ID
                            FROM clinic c
                            JOIN user u on c.clinicman_ID = u.user_ID
                            WHERE clinicman_ID = $user_ID);";

    if($result = $conn->query($query)){
        /* determine number of rows result set */
        $row_count = $result->num_rows;

        if(((int) $row_count) > 0){
            echo $row_count.",";
        }else{
            echo "0,";
        }
    }else{
        echo "ERROR: " . mysqli_error($conn);
    }


?>

