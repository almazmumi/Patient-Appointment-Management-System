<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

    $username=$fname=$lname=$hashed_pw=$email=$gender="";
    $reg_date = date('Y-m-d');
    $type_ID=2;
    require_once('index.php');
    if(isset($_REQUEST['username'])
    && isset($_REQUEST['fname'])
    && isset($_REQUEST['lname'])
    && isset($_REQUEST['hashed_pw'])
    && isset($_REQUEST['email'])
    && isset($_REQUEST['gender'])
    ){
        $username       = $_REQUEST['username'];
        $fname          = $_REQUEST['fname'];
        $lname          = $_REQUEST['lname'];
        $hashed_pw      = $_REQUEST['hashed_pw'];
        $email          = $_REQUEST['email'];
        $gender         = $_REQUEST['gender'];
    
    }else{

        echo "ERROR: The information is not complete\r\n ";
        echo $username . "\r\n" . $fname . "\r\n" . $lname . "\r\n" . $hashed_pw . "\r\n" . $email . "\r\n" . $reg_date . "\r\n" . $type_ID . "\r\n" . $gender;

    }
    
    // $phone_number1  = $_REQUEST['phone_number1'];
    // $phone_number2  = $_REQUEST['phone_number2'];




    // Create a new ID ==========================================
    $a_new_id = "302010". $type_ID;
    if($result = $conn->query("SELECT * FROM user")){
        /* determine number of rows result set */
        $row_count = $result->num_rows;
        /* close result set */
        $result->close();
    }

    if(((int)($row_count+1)) < 10)
    $a_new_id = $a_new_id ."0". ($row_count+1);
    else
    $a_new_id = $a_new_id . ($row_count+1);
    // ===========================================================
    
    
    // Chaeck if the emeail or the username is already exist
    $queryToCheck = "SELECT * FROM user WHERE username = '$username' OR email = '$email'";
    if($result = $conn->query($queryToCheck)){
        /* determine number of rows result set */
        $row_count = $result->num_rows;

        if (((int)$row_count) > 0){
            echo "ERROR: email or username already exists.";
        }else{
            // Insert new row in user table if doesn't exist
            $queryToInsert = "INSERT INTO user (user_ID, username, fname, lname, hashed_pw, email, reg_date, type_ID, gender) 
            VALUES ('$a_new_id', '$username', '$fname',  '$lname', '$hashed_pw', '$email','$reg_date', '$type_ID','$gender')";
            if($result = $conn->query($queryToInsert)){
                echo "Registered!";    
            }else{
                echo "ERROR: " . mysqli_error($conn);
            }
        }
    }

    // // =====================================================
   





?>