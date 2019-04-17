<?php 

error_reporting(E_ALL);
ini_set('display_errors', 1);
require_once('index.php');



// Recive a key from the website in this matter:

// 1-   GetAllManagers()
// 2-   AddNewManager(fname, lname, email,username, password, gender) 
// 3-   UpdateSpecificManager(fname, lname, email,username, password, gender)
// 4-   ChangeManagerStatus(user_ID, status_ID)
// 5-   GetAllFreeManagers()
// 6-   AddNewReceptionist(fname, lname, email, username, password, gender) 
// 7-   GetManagerRelatedInformation(user_ID) 
// 8-   AddNewDentist(fname, lname, start_career_date, bio, email, website, clinic_office, clinic_number, specialty_ID, clinicman_ID) 
// 9 -  GetAllDentists(clinicman_ID)
// 10 - updateDentistInfo(fname, lname, start_career_date, bio, email, clinic_office, clinic_number, status_ID, speciality_ID)
// 12 - GetAllReceptionists()

$key = $_REQUEST['key'];



if((int)$key == 1){         // 1- GetAllManagers()

    
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

















}else if((int)$key == 9){   // 9 - GetAllDentists(clinicman_ID)
    
    if(isset($_REQUEST['clinicman_ID'])){
        $clinicman_ID                      = $_REQUEST['clinicman_ID'];
        $query = "  SELECT * FROM dentist D
                    JOIN specialty s on D.specialty_ID = s.specialty_ID
                    JOIN status S on S.status_ID = D.status_ID
                    WHERE D.clinic_ID IN (
                                SELECT c.clinic_ID
                                FROM clinic c
                                WHERE clinicman_ID = $clinicman_ID);";

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















}else if((int)$key == 10){  // 10 - updateDentistInfo(fname, lname, start_career_date, bio, email, clinic_office, clinic_number, status_ID, speciality_ID)


    $fname=$lname=$start_career_date=$bio=$email=$clinic_office=$clinic_number=$status_ID=$speciality_ID=$dentist_ID=$clinic_ID="";

    
    if(isset($_REQUEST['fname'])
    && isset($_REQUEST['lname'])
    && isset($_REQUEST['start_career_date'])
    && isset($_REQUEST['bio'])
    && isset($_REQUEST['email'])
    && isset($_REQUEST['clinic_office'])
    && isset($_REQUEST['clinic_number'])
    && isset($_REQUEST['status_ID'])
    && isset($_REQUEST['speciality_ID'])
    && isset($_REQUEST['dentist_ID'])
    && isset($_REQUEST['clinic_ID'])
    ){
        $fname                      = $_REQUEST['fname'];
        $lname                      = $_REQUEST['lname'];
        $start_career_date          = $_REQUEST['start_career_date'];
        $email                      = $_REQUEST['email'];
        $bio                        = $_REQUEST['bio'];
        $clinic_office              = $_REQUEST['clinic_office'];
        $clinic_number              = $_REQUEST['clinic_number'];
        $status_ID                  = $_REQUEST['status_ID'];
        $speciality_ID              = $_REQUEST['speciality_ID'];
        $dentist_ID                 = $_REQUEST['dentist_ID'];
        $clinic_ID                  = $_REQUEST['clinic_ID'];
    
        $query = "  UPDATE pams.dentist 
        SET fname = '$fname', lname = '$lname', bio = '$bio', start_career_date = '$start_career_date', email = '$email', rating = null, specialty_ID = $speciality_ID, clinic_ID = $clinic_ID, clinic_office = $clinic_office, clinic_number = $clinic_number, status_ID = $status_ID 
        WHERE dentist_ID = $dentist_ID";
        if($result = $conn->query($query)){
            echo "Updated Successfully!";
        }else{
            echo "ERROR: " . mysqli_error($conn);
        }

    }else{
        echo "ERROR: The information is not complete\r\n ";
    }

















}else if((int)$key == 2){   // 2- AddNewManager(fname, lname, email,username, password, gender) 
    
    $fname=$lname=$username=$password=$email=$gender="";

   

    if(isset($_REQUEST['fname'])
    && isset($_REQUEST['lname'])
    && isset($_REQUEST['username'])
    && isset($_REQUEST['password'])
    && isset($_REQUEST['email'])
    && isset($_REQUEST['gender'])
    ){
        $fname                  = $_REQUEST['fname'];
        $lname                  = $_REQUEST['lname'];
        $username               = $_REQUEST['username'];
        $password               = $_REQUEST['password'];
        $email                  = $_REQUEST['email'];
        $gender                 = $_REQUEST['gender'];
        $today = date("Y-m-d");

        $queryToInsert = "INSERT INTO pams.user (username, fname, lname, hashed_pw, email, reg_date, type_ID, status_ID, gender) 
        VALUES ( '$username', '$fname', '$lname', '$password', '$email', '$today', 2, 1, '$gender');";
    
        if($result = $conn->query($queryToInsert)){
            echo "Added Successfully!";    
        }else{
            echo "ERROR: " . mysqli_error($conn);
        }

    }else{
        echo "ERROR: The information is not complete\r\n ";
    }

















}else if((int)$key == 3){   // 3- UpdateSpecificManager(fname, lname, email,username, password, gender)

    $fname=$lname=$username=$password=$email=$gender=$user_ID=$status_ID="";

   

    if(isset($_REQUEST['fname'])
    && isset($_REQUEST['lname'])
    && isset($_REQUEST['username'])
    && isset($_REQUEST['password'])
    && isset($_REQUEST['email'])
    && isset($_REQUEST['gender'])
    && isset($_REQUEST['user_ID'])
    && isset($_REQUEST['status_ID'])
    ){
        $fname            = $_REQUEST['fname'];
        $lname            = $_REQUEST['lname'];
        $username         = $_REQUEST['username'];
        $password         = $_REQUEST['password'];
        $email            = $_REQUEST['email'];
        $gender           = $_REQUEST['gender'];
        $user_ID          = $_REQUEST['user_ID'];
        $status_ID        = $_REQUEST['status_ID'];
        
        
        $queryToUpdate = "UPDATE pams.user 
        SET fname = '$fname', lname = '$lname', username= '$username', hashed_pw='$password', email='$email', gender='$gender', status_ID='$status_ID' 
        WHERE user_ID = $user_ID;";
    
        if($result = $conn->query($queryToUpdate)){
            echo "Updated Successfully!";    
        }else{
            echo "ERROR: " . mysqli_error($conn);
        }
    }else{
        echo "ERROR: The information is not complete\r\n ";
    }
    


















}else if((int)$key == 4){   // 4- ChangeStatus(user_ID, status_ID)

    $clinic_ID=$status_ID="";

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



















}else if((int)$key == 5){   // 5- GetAllFreeManagers()
    
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



















}else if ((int)$key == 6){  // 6- AddNewReceptionist(fname, lname, email, username, password, gender) 

    $fname=$lname=$username=$password=$email=$gender=$clinicman_ID="";

   

    if(isset($_REQUEST['fname'])
    && isset($_REQUEST['lname'])
    && isset($_REQUEST['username'])
    && isset($_REQUEST['password'])
    && isset($_REQUEST['email'])
    && isset($_REQUEST['gender'])
    && isset($_REQUEST['clinicman_ID'])

    ){
        $fname                  = $_REQUEST['fname'];
        $lname                  = $_REQUEST['lname'];
        $username               = $_REQUEST['username'];
        $password               = $_REQUEST['password'];
        $email                  = $_REQUEST['email'];
        $gender                 = $_REQUEST['gender'];
        $clinicman_ID           = $_REQUEST['clinicman_ID'];
        $clinic_ID = "";

        $today = date("Y-m-d");
        $queryToGetClinicID = " SELECT clinic_ID
                                FROM clinic
                                WHERE clinicman_ID = $clinicman_ID";
        
        if($result = $conn->query($queryToGetClinicID)){
            $data = mysqli_fetch_array($result);
            $clinic_ID = $data['clinic_ID'];

        }else{
            echo "ERROR: " . mysqli_error($conn);
        }

        $queryToInsert = "INSERT INTO pams.user (username, fname, lname, hashed_pw, email, reg_date, type_ID, status_ID, gender) 
        VALUES ( '$username', '$fname', '$lname', '$password', '$email', '$today', 3, 1, '$gender');";
        if($result = $conn->query($queryToInsert)){
            $recept_ID = mysqli_insert_id($conn);
            
            $queryToInsert1 = "INSERT INTO pams.receptionist (clinic_ID, recept_ID) VALUES ('$clinic_ID', '$recept_ID');";
            if($result = $conn->query($queryToInsert1)){
                echo "Added Successfully!";
            }else{
                echo "ERROR: " . mysqli_error($conn);
            }
        }else{
            echo "ERROR: " . mysqli_error($conn);
        }


    }














}else if((int)$key == 11){  // 11- UpdateSpecificReceptionist(fname, lname, email,username, password, gender)

    $fname=$lname=$username=$password=$email=$gender=$user_ID=$status_ID="";

   

    if(isset($_REQUEST['fname'])
    && isset($_REQUEST['lname'])
    && isset($_REQUEST['username'])
    && isset($_REQUEST['password'])
    && isset($_REQUEST['email'])
    && isset($_REQUEST['gender'])
    && isset($_REQUEST['user_ID'])
    && isset($_REQUEST['status_ID'])
    ){
        $fname            = $_REQUEST['fname'];
        $lname            = $_REQUEST['lname'];
        $username         = $_REQUEST['username'];
        $password         = $_REQUEST['password'];
        $email            = $_REQUEST['email'];
        $gender           = $_REQUEST['gender'];
        $user_ID          = $_REQUEST['user_ID'];
        $status_ID        = $_REQUEST['status_ID'];
        
        
        $queryToUpdate = "UPDATE pams.user 
        SET fname = '$fname', lname = '$lname', username= '$username', hashed_pw='$password', email='$email', gender='$gender', status_ID='$status_ID' 
        WHERE user_ID = $user_ID;";
    
        if($result = $conn->query($queryToUpdate)){
            echo "Updated Successfully!";    
        }else{
            echo "ERROR: " . mysqli_error($conn);
        }
    }else{
        echo "ERROR: The information is not complete\r\n ";
    }
    


















}else if((int)$key == 12){  // 12 - GetAllReceptionists()
    
    
    
    if(isset($_REQUEST['user_ID'])){
        $user_ID            = $_REQUEST['user_ID'];
    }

    $queryToInsert = "SELECT *
    FROM user u
    JOIN receptionist a ON u.user_ID = a.recept_ID
    JOIN status s on u.status_ID = s.status_ID
    WHERE a.clinic_ID IN (
                SELECT c.clinic_ID
                FROM clinic c
                WHERE clinicman_ID = $user_ID);";
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



















}else if ((int)$key == 8){  //8- AddNewDentist(fname, lname, start_career_date, bio, email, website, clinic_office, clinic_number, specialty_ID, clinicman_ID) 
    

    $fname=$start_career_date=$bio=$email=$website=$clinic_office=$clinic_number=$specialty_ID=$clinicman_ID="";

   

    if(isset($_REQUEST['fname'])
    && isset($_REQUEST['lname'])
    && isset($_REQUEST['start_career_date'])
    && isset($_REQUEST['bio'])
    && isset($_REQUEST['email'])
    && isset($_REQUEST['website'])
    && isset($_REQUEST['clinic_office'])
    && isset($_REQUEST['clinic_number'])
    && isset($_REQUEST['specialty_ID'])
    && isset($_REQUEST['clinicman_ID'])
    ){
        $fname                      = $_REQUEST['fname'];
        $lname                      = $_REQUEST['lname'];
        $start_career_date          = $_REQUEST['start_career_date'];
        $bio                        = $_REQUEST['bio'];
        $email                      = $_REQUEST['email'];
        $website                    = $_REQUEST['website'];
        $clinic_office              = $_REQUEST['clinic_office'];
        $clinic_number              = $_REQUEST['clinic_number'];
        $specialty_ID               = $_REQUEST['specialty_ID'];
        $clinicman_ID               = $_REQUEST['clinicman_ID'];
        $clinic_ID = "";

        $queryToGetClinicID = " SELECT clinic_ID
                                FROM clinic
                                WHERE clinicman_ID = $clinicman_ID";
        if($result = $conn->query($queryToGetClinicID)){
            $data = mysqli_fetch_array($result);
            $clinic_ID = $data['clinic_ID'];
        }else{
            echo "ERROR: " . mysqli_error($conn);
        }

        $queryToInsert = "INSERT INTO pams.dentist (fname, lname, bio, start_career_date, website, email, rating, specialty_ID, clinic_ID, clinic_office, clinic_number, status_ID) 
        VALUES ('$fname', '$lname', '$bio', '$start_career_date', '$website', '$email', null, $specialty_ID , $clinic_ID, $clinic_office, $clinic_number, 1);";
        if($result = $conn->query($queryToInsert)){
            echo "Added Successfully!";
        }else{
            echo "ERROR: " . mysqli_error($conn);
        }


    }

}else if((int)$key == 7){


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
                FROM advertisement
                WHERE clinicman_ID = $user_ID";

    if($result = $conn->query($query)){
       
        $row_count = $result->num_rows;

        if(((int) $row_count) > 0){
            echo $row_count.",";
        }else{
            echo "0,";
        }
    }else{
        echo "ERROR: " . mysqli_error($conn);
    }

    $query = "  SELECT sum(fees) AS sum
    FROM advertisement
    WHERE clinicman_ID = $user_ID";
    
    if($result = $conn->query($query)){
        $data = mysqli_fetch_array($result);
        echo $data['sum'].",";
    }else{
        echo "ERROR: " . mysqli_error($conn);
    }
}   
?>