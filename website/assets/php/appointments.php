<?php 

error_reporting(E_ALL);
ini_set('display_errors', 1);
require_once('index.php');



// Recive a key from the website in this matter:

// 1- GetAllAppointments(recept_ID)
// 5- getOneClinic(clinicman_ID)


$key = $_REQUEST['key'];



if((int)$key == 1){// 1- GetAllAppointments(recept_ID)

    $user_ID = "";
    if(isset($_REQUEST['user_ID'])){
        $user_ID            = $_REQUEST['user_ID'];
    }

    $queryToSelect = "SELECT a.appointment_ID, a.reservation_date, a.apm_date, a.apm_type, u.fname as pfname, u.lname as plname, d.fname as dfname, d.lname as dlname, s.status_name, s.status_ID, d.clinic_ID
    FROM appointment a
    JOIN user u ON a.patient_ID = u.user_ID
    JOIN dentist d ON d.dentist_ID = a.dentist_ID
    JOIN status s on a.status_ID = s.status_ID
    WHERE d.clinic_ID IN (
    SELECT clinic_ID FROM receptionist WHERE receptionist.recept_ID = $user_ID)
    ORDER BY a.appointment_ID DESC ";

    if($result = $conn->query($queryToSelect)){
        $row_count = $result->num_rows;
        if (((int)$row_count) > 0){
            $json = array();
            while($row = mysqli_fetch_assoc($result)){
                $json[] = $row;
            }
            echo json_encode($json);
        }
    }else{
        echo "ERROR: " . mysqli_error($conn);
    }
}else if((int)$key == 2){// 2- changeStatusAppointment(recept_ID, appointment_ID,status_ID)

    $user_ID =$appointment_ID=$status_ID="";
    if(isset($_REQUEST['user_ID'])
    && isset($_REQUEST['appointment_ID'])
    && isset($_REQUEST['status_ID'])){
        $user_ID            = $_REQUEST['user_ID'];
        $appointment_ID     = $_REQUEST['appointment_ID'];
        $status_ID          = $_REQUEST['status_ID'];
    }
    
    $comment = "";
    if(isset($_REQUEST['comment'])){
        $comment = $_REQUEST['comment'];
        $queryToUpdate = "  UPDATE pams.appointment 
                        SET status_ID = $status_ID, recept_ID = $user_ID, recept_comment = '$comment'
                        WHERE appointment_ID = $appointment_ID;";
    }else{
        $queryToUpdate = "  UPDATE pams.appointment 
                        SET status_ID = $status_ID, recept_ID = $user_ID
                        WHERE appointment_ID = $appointment_ID;";
    }

    if($result = $conn->query($queryToUpdate)){

        $queryToInsert = "SELECT a.appointment_ID, a.reservation_date, a.apm_date, a.apm_type, u.fname as pfname, u.lname as plname, d.fname as dfname, d.lname as dlname, s.status_name, s.status_ID, d.clinic_ID
        FROM appointment a
        JOIN user u ON a.patient_ID = u.user_ID
        JOIN dentist d ON d.dentist_ID = a.dentist_ID
        JOIN status s on a.status_ID = s.status_ID
        WHERE d.clinic_ID IN (
        SELECT clinic_ID FROM receptionist WHERE receptionist.recept_ID = $user_ID)
        ORDER BY a.appointment_ID DESC ";
        if($result = $conn->query($queryToInsert)){

            $json = array();
            while($row = mysqli_fetch_assoc($result)){
                $json[] = $row;
            }
            echo json_encode($json);

        }else{
            echo "ERROR: " . mysqli_error($conn);
        }

    }else{
        echo "ERROR: " . mysqli_error($conn);
    }
}else if((int)$key == 3){// 3- change status (recept_ID, appointment_ID, status_ID)

    $user_ID =$appointment_ID=$status_ID="";
    if(isset($_REQUEST['user_ID'])
    && isset($_REQUEST['appointment_ID'])
    && isset($_REQUEST['status_ID'])){
        $user_ID            = $_REQUEST['user_ID'];
        $appointment_ID     = $_REQUEST['appointment_ID'];
        $status_ID          = $_REQUEST['status_ID'];
    }

    $queryToInsert = "  UPDATE pams.appointment 
                        SET status_ID = $status_ID, recept_ID = $user_ID
                        WHERE appointment_ID = $appointment_ID;";

    if($result = $conn->query($queryToInsert)){

        $queryToInsert = "SELECT a.appointment_ID, a.reservation_date, a.apm_date, a.apm_type, u.fname as pfname, u.lname as plname, d.fname as dfname, d.lname as dlname, s.status_name, s.status_ID, d.clinic_ID
        FROM appointment a
        JOIN user u ON a.patient_ID = u.user_ID
        JOIN dentist d ON d.dentist_ID = a.dentist_ID
        JOIN status s on a.status_ID = s.status_ID
        WHERE d.clinic_ID IN (
        SELECT clinic_ID FROM receptionist WHERE receptionist.recept_ID = $user_ID)
        ORDER BY a.appointment_ID DESC ";
        if($result = $conn->query($queryToInsert)){

            $json = array();
            while($row = mysqli_fetch_assoc($result)){
                $json[] = $row;
            }
            echo json_encode($json);

        }else{
            echo "ERROR: " . mysqli_error($conn);
        }

    }else{
        echo "ERROR: " . mysqli_error($conn);
    }
}