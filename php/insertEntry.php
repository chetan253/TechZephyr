<?php

if ($_SERVER["REQUEST_METHOD"]=="POST") {
        require_once 'connection.php';
	createEntry();
}

function createEntry()
{
	global $connect;
	$_imei = $_POST["_imei"];
	$name = $_POST["name"];
	$college = $_POST["college"];
	$event = $_POST["event"];
	$workshop = $_POST["workshop"];
	$phone = $_POST["phone"];
	$email = $_POST["email"];
	$receipt_issued = $_POST["receipt_issued"];
	
	$query = "Insert into entries(_imei, name, college, event, workshop, phone, email, receipt_issued) values ('$_imei','$name','$college','$event','$workshop','$phone','$email','$receipt_issued');";
	mysqli_query($connect, $query) or die(mysqli_error($connect));
	mysqli_close($connect);
}

?>			
