<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

require_once('index.php');

$username = "";
$password = "";
if(isset($_REQUEST['username']) && isset($_REQUEST['password'])){
	$username = $_REQUEST['username'];
	$password = $_REQUEST['password'];
}else{
	echo "ERROR: The information is not complete\r\n ";
}


$query = "SELECT * FROM user WHERE email = '$username' AND  hashed_pw = '$password'";
if($result = $conn->query($query)){
	/* determine number of rows result set */
	$row_count = $result->num_rows;

	if(((int) $row_count) > 0){
		while($row = mysqli_fetch_assoc($result)){
			echo $row["user_ID"]. ",". $row["fname"].",".$row["lname"].",".$row["type_ID"];
		}	
	}else{
		echo "ERROR: Either Username or password is incorrect!";
	}

}else{
	echo "ERROR: " . mysqli_error($conn);
}

?>

