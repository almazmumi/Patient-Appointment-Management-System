<?php 

error_reporting(E_ALL);
ini_set('display_errors', 1);
require_once('index.php');



// Recive a key from the website in this matter:

// 1- getAllAdvertisement()
// 2- AddNewAdvertisement(start_date,end_date,title,content,fees) 


$key = $_REQUEST['key'];



if((int)$key == 1){

    // 1- GetAllClinics()
    $query = "SELECT * FROM user U left JOIN clinic c on U.user_ID = c.clinicman_ID JOIN status S on S.status_ID = U.status_ID Where U.type_ID=2";
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



}else if((int)$key == 2){
    //2- AddNewAdvertisement(start_date,end_date,title,content,fees)
    $start_date=$end_date=$title=$content=$fees=$clinicman_ID="";

   

    if(isset($_REQUEST['start_date'])
    && isset($_REQUEST['end_date'])
    && isset($_REQUEST['title'])
    && isset($_REQUEST['content'])
    && isset($_REQUEST['fees'])
    && isset($_REQUEST['clinicman_ID'])
    ){
        $start_date                 = $_REQUEST['start_date'];
        $end_date                   = $_REQUEST['end_date'];
        $title                      = $_REQUEST['title'];
        $content                    = $_REQUEST['content'];
        $fees                       = $_REQUEST['fees'];
        $clinicman_ID               = $_REQUEST['clinicman_ID'];
        $queryToInsert = "INSERT INTO pams.advertisement (start_date, end_date, content, title, fees, clinicman_ID) 
        VALUES ('$start_date', '$end_date','$content', '$title', '$fees', $clinicman_ID);";
    
        if($result = $conn->query($queryToInsert)){
            echo "Added Successfully!";    
        }else{
            echo "ERROR: " . mysqli_error($conn);
        }

    }else{
        echo "ERROR: The information is not complete\r\n ";
    }



}

?>