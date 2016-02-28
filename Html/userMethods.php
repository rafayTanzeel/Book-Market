<?php
	$response = array();

	if (isset($_POST['TokenId']) && isset($_POST['Name']) && isset($_POST['Email'])) {
 
   	$tokenId = $_POST['tokenId'];
   	$name = $_POST['name'];
   	$email = $_POST['email'];
	if(isset($_POST['photoUrl'])){
		$photo_exist=1;
		$photoUrl = $_POST['photoUrl'];
	}

	
	// Connect to db
   	require_once "database_helper.php";
  	$db = new DatabaseHelper();
	 
   	$sql = "SELECT * FROM user where tokenid = ?";
   	$stmt = $db->prepareStatement($sql);
	$db->bindParameter($stmt, 's', $tokenId);
   	$db->executeStatement($stmt);
	$exist = $db->getResult($stmt);
   	if($exist){
      $no =  count($exist);
	   if($no > 0){
         $response["success"] = 1;
         $response["message"] = "User exists and Logged in";
   	}
    // echoing JSON response
   	echo json_encode($response);
	}
	else{
		// check if row inserted or not
		$sql = "INSERT INTO user(tokenid, email, name, photourl) VALUES (?,?,?,?)";
		$stmt = $db->prepareStatement($sql);
		if ($photo_exist==1){
			$params = array($tokenId, $email, $name, $photoUrl);
		}else{
			$params = array($tokenId, $email, $name, '');
		}
		
		$db->bindArray($stmt,$param_types,$params);
		$db->executeStatement($stmt);
		$result = $db->getAffectedRows($stmt);
		
		// check if row inserted or not
		if ($result) {
			$response["success"] = 1;
			$response["message"] = "User created";
			echo json_encode($response);
			
    	}else {
		   // failed to insert row
		   $response["success"] = 0;
		   $response["message"] = "User is neither created nor logged in";			
			// echoing JSON response
		   echo json_encode($response);
    	}
	} 
}
else {
    // required field is missing

    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing  or db is broken";
 	
	
    // echoing JSON response
    echo json_encode($response);
}
//}
?>