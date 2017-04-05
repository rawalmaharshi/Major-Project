<?php 
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
 
// check for post data
if (isset($_GET["dev_id"])) {
    $dev_id = $_GET['dev_id'];
 
    // get a product from products table
    $result = mysqli_query($conn,"SELECT *FROM devices WHERE dev_id = $dev_id");
    
    if (!empty($result)) {
        // check for empty result
        if (mysqli_num_rows($result) > 0) {
 
            $row = mysqli_fetch_array($result);
 
            $device = array();
            $device["dev_id"] = $row["dev_id"];
            $device["dev_name"] = $row["dev_name"];
            $device["dev_state"] = $row["dev_state"];
            $device["created_at"] = $row["created_at"];
            // success
            $response["success"] = 1;
 
            // user node
            $response["currentDevice"] = array();
 
            array_push($response["currentDevice"], $device);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No devices found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No dev found";
 
        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
$conn->close();
?>