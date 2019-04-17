<?php 

error_reporting(E_ALL);
ini_set('display_errors', 1);
require_once('index.php');



// Recive a key from the website in this matter:

// 1- GetAllClinic()
// 2- AddNewClinic(clinic_name, clinic_description, clinicman_ID, location, website, email) 
// 3- UpdateSpecificClinic(clinic_name, clinic_description, clinicman_ID, location, website, email)
// 4- ChangeStatus(clinic_ID, status_ID)
// 5- getOneClinic(clinicman_ID)


$key = $_REQUEST['key'];



if((int)$key == 1){

    // 1- GetAllClinics()
    $query = "SELECT * FROM clinic C, status S, user U WHERE C.clinicman_ID = U.user_ID AND U.type_ID = 2 AND C.status_ID = S.status_ID";
    if($result = $conn->query($query)){
    
        $row_count = $result->num_rows;
        if($row_count > 0){
            $json = array();
            while($row = mysqli_fetch_assoc($result)){
                $json[] = $row;
            }
            echo json_encode($json);
        }
    }else{
        echo "ERROR: " . mysqli_error($conn);
    }



}else if((int)$key == 5){
    $clinicman_ID = "";
    // 5- getOneClinic(clinicman_ID)
    if(isset($_REQUEST['clinicman_ID'])){
        $clinicman_ID = $_REQUEST['clinicman_ID'];
        
        $query = "SELECT * FROM clinic C, status S WHERE C.clinicman_ID = $clinicman_ID AND C.status_ID = S.status_ID";
        if($result = $conn->query($query)){
        
            $row_count = $result->num_rows;
            if($row_count > 0){
                $json = array();
                while($row = mysqli_fetch_assoc($result)){
                    $json[] = $row;
                }
                echo json_encode($json);
            }
        }else{
            echo "ERROR: " . mysqli_error($conn);
        }
    }else{
        echo "ERROR: The information is not complete\r\n ";
    }


}else if((int)$key == 2){
    //2- AddNewClinic(clinic_name, clinic_description, clinicman_ID, location, website, email) 
    $clinic_name=$clinic_description=$clinicman_ID=$website=$location=$email="";


    if(isset($_REQUEST['clinic_name'])
    && isset($_REQUEST['clinic_description'])
    && isset($_REQUEST['clinicman_ID'])
    && isset($_REQUEST['website'])
    && isset($_REQUEST['email'])
    && isset($_REQUEST['location'])
    ){
        $clinic_name                = $_REQUEST['clinic_name'];
        $clinic_description         = $_REQUEST['clinic_description'];
        $clinicman_ID               = $_REQUEST['clinicman_ID'];
        $website                    = $_REQUEST['website'];
        $email                      = $_REQUEST['email'];
        $location                   = $_REQUEST['location'];
        
        if($email = ""){
            $queryToInsert = "INSERT INTO pams.clinic (clinic_name, clinic_description, location, website,  clinicman_ID, status_ID) 
            VALUES ('$clinic_name', '$clinic_description', '$location', '$website',  $clinicman_ID, 5);";
        }else{
            $queryToInsert = "INSERT INTO pams.clinic (clinic_name, clinic_description, location, website, email, clinicman_ID, status_ID) 
            VALUES ('$clinic_name', '$clinic_description', '$location', '$website', '$email', $clinicman_ID, 5);";
        }
        
        if($result = $conn->query($queryToInsert)){
            echo "Added Successfully!";    
        }else{
            echo "ERROR: " . mysqli_error($conn);
        }

    }else{
        echo "ERROR: The information is not complete\r\n ";
    }



}else if((int)$key == 3){
    //3- UpdateSpecificClinic(clinic_ID, clinic_name, clinic_description, clinicman_ID, location, website, email)
    $clinic_ID=$clinic_name=$clinic_description=$clinicman_ID=$website=$location=$email=$status_ID="";

    
    if(isset($_REQUEST['clinic_ID'])
    && isset($_REQUEST['clinic_name'])
    && isset($_REQUEST['clinic_description'])
    && isset($_REQUEST['website'])
    && isset($_REQUEST['email'])
    && isset($_REQUEST['location'])
    && isset($_REQUEST['status_ID']))
    {
        $clinic_ID                  = $_REQUEST['clinic_ID'];
        $clinic_name                = $_REQUEST['clinic_name'];
        $clinic_description         = $_REQUEST['clinic_description'];
        $website                    = $_REQUEST['website'];
        $email                      = $_REQUEST['email'];
        $location                   = $_REQUEST['location'];
        $status_ID                  = $_REQUEST['status_ID'];
        
        
        $queryToUpdate = "UPDATE pams.clinic 
        SET clinic_name = '$clinic_name', clinic_description = '$clinic_description', location= '$location', website='$website', email='$email', status_ID='$status_ID' 
        WHERE clinic_ID = $clinic_ID;";
    
        if($result = $conn->query($queryToUpdate)){
            echo "Updated Successfully!";    
        }else{
            echo "ERROR: " . mysqli_error($conn);
        }
    }else{
        echo "ERROR: The information is not complete\r\n ";
    }
    


}else if((int)$key == 4){
    // 4- ChangeStatus(clinic_ID, status_ID)
    $clinic_ID=$status_ID="";
    $clinic_ID                  = $_REQUEST['clinic_ID'];
    $status_ID                  = $_REQUEST['status_ID'];

    if(isset($_REQUEST['clinic_ID'])
    && isset($_REQUEST['status_ID']))
    {
        $clinic_ID                  = $_REQUEST['clinic_ID'];
        $status_ID                  = $_REQUEST['status_ID'];
        
        
        $queryToUpdate = "UPDATE pams.clinic 
        SET status_ID='$status_ID' 
        WHERE clinic_ID = $clinic_ID;";
    
        if($result = $conn->query($queryToUpdate)){
            echo "Changed Successfully!";    
        }else{
            echo "ERROR: " . mysqli_error($conn);
        }
    }else{
        echo "ERROR: The information is not complete\r\n ";
    }




}
?>