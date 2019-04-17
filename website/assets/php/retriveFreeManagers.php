<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

require_once('index.php');

    $queryToInsert = "SELECT user_ID,fname,lname FROM user U where user_ID NOT IN (SELECT clinicman_ID FROM clinic) AND type_ID = 2";
    if($result = $conn->query($queryToInsert)){
        
        

        $row_count = $result->num_rows;
        if (((int)$row_count) > 0){
            $json = array();
            while($row = mysqli_fetch_assoc($result)){
                $json[] = $row;
            }
            echo json_encode($json);
        }else{
            echo "ERROR: There is no manager available";
        }    

       

        
    }else{
        echo "ERROR: " . mysqli_error($conn);
    }


    // // =====================================================
   





?>