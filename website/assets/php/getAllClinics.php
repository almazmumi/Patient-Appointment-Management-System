<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
require_once('index.php');




    $clinicOrManagers = $_GET['choice']; // 0 for clinics, 1 for managers

    if($clinicOrManagers == 0){
        $query = "SELECT * FROM clinic C, status S, user U WHERE C.clinicman_ID = U.user_ID AND U.type_ID = 2 AND U.status_ID = S.status_ID AND S.status_name = 'ACTIVE'";
            if($result = $conn->query($query)){
            /* determine number of rows result set */
            $row_count = $result->num_rows;

            while($row = $result->fetch_assoc()) {


                echo '<tr>
                        <td scope="row">' . $row["clinic_ID"]. '</td>
                        <td>' . $row["clinic_profile"] .'</td>
                        <td> '.$row["location"] .'</td>
                        <td> '.$row["website"] .'</td>
                        <td> '.$row["email"] .'</td>
                        <td> '.$row["rating"] .'</td>
                        <td> '.$row["fname"].' '.$row["lname"] .'</td>
                        </tr>';

                    

            }
        }else{
            echo "ERROR: " . mysqli_error($conn);
        }
    }else if($clinicOrManagers == 1){



        $query = "SELECT * FROM user JOIN status ON user.status_ID = status.status_ID WHERE type_ID= 2 AND status_name ='ACTIVE'";
            if($result = $conn->query($query)){
            /* determine number of rows result set */
            $row_count = $result->num_rows;

            while($row = $result->fetch_assoc()) {


                echo '<tr>
                        <td scope="row">' . $row["user_ID"]. '</td>
                        <td>' .$row["fname"].' '.$row["lname"]  .'</td>
                        <td> '.$row["email"] .'</td>
                        <td> '.$row["reg_date"] .'</td>
                        <td> '.$row["gender"] .'</td>
                        <td> '.$row["status_name"] .'</td>
                        </tr>';

                    

            }
        }else{
            echo "ERROR: " . mysqli_error($conn);
        }

    }

?>

