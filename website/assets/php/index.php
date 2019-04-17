<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

    //database constants
    define('DB_HOST','35.192.116.141');
    define('DB_USER','root');
    define('DB_PASS','');
    define('DB_NAME','pams');

    //connecting to database and getting the connection object
    $conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

    //Checking if any error occured while connecting
    if (mysqli_connect_errno()) {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
        die();
    }
    
?>
