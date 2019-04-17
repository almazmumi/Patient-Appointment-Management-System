<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
require_once('index.php');



    $query = "SELECT * FROM user WHERE type_id = 4";
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

    $query = "SELECT * FROM clinic";
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

    $query = "SELECT * FROM appointment";
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

    $query = "SELECT * FROM dentist";
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



    $query = "SELECT * FROM user WHERE type_ID = 2";
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

