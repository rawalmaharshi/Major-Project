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
 
 
// check for required fields
if (isset($_GET['dev_id']) && isset($_GET['dev_name']) && isset($_GET['dev_state'])) {
 
    $dev_id = $_GET['dev_id'];
    $dev_name = $_GET['dev_name'];
    $dev_state = $_GET['dev_state'];
    
    // mysql update row with matched dev_id
    $result = mysqli_query($conn,"UPDATE products SET dev_state = $dev_state WHERE dev_id = $dev_id");
 
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully updated.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
 
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