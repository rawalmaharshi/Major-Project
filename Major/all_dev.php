
<?php
 
/*
 * Following code will get all the devices in the database.
 * A device is defined by its id.
 */
 
// array for JSON response
//$response = array();
 
// include db connect class
//require_once __DIR__ . '/db_connect.php';
 
// connecting to db
//$db = new DB_CONNECT();

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "devices";


$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
// array for JSON response
$response = array();

//Get all the devices
$result = mysqli_query($conn,"SELECT * FROM devices");


 
        if (mysqli_num_rows($result) > 0) {
            //echo mysqli_num_rows($result);
             $response["device"] = array();
            
            while($row = mysqli_fetch_array($result)){
 
            $device = array();
            $device["dev_id"] = $row["dev_id"];
            $device["dev_name"] = $row["dev_name"];
            $device["dev_state"] = $row["dev_state"];
            $device["created_at"] = $row["created_at"];
            
            array_push($response["device"], $device);
            }
            // success
            $response["success"] = 1;
  
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No device found";
 
            // echo no users JSON
            echo json_encode($response);
        }
$conn->close();
?>