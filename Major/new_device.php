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
if (isset($_POST['dev_name']) && isset($_POST['dev_state'])) {
 
    $dev_name = $_POST['dev_name'];
    $dev_state = $_POST['dev_state'];

$sql  = "
        INSERT INTO  devices (dev_state,dev_name) VALUES ($dev_state,'$dev_name') ;";

    // check if row inserted or not
    if ($conn->query($sql) === TRUE) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
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