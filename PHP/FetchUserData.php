<?php
	//Sign In
    $con=mysqli_connect("mysql7.000webhost.com","a1812880_nixon","qwe123","a1812880_MBD");
	//get variable information from Android studio
    $email = $_POST["email"];
    $password = $_POST["password"];
	
	//Prepare statement to fetch salt encode
	$saltStmt = mysqli_prepare($con,"SELECT salt FROM `a1812880_MBD`.`User` WHERE email = ?");
	mysqli_stmt_bind_param($saltStmt, "s", $email);
	$saltStmt->execute();
	mysqli_stmt_store_result($saltStmt);
	$saltStmt->bind_result($salt);
        while(mysqli_stmt_fetch($saltStmt)){
		$salt = $salt;
	}
		
	$decodedPassword = base64_encode(sha1($password . $salt, true) . $salt);
    $response = array();
    
	//validate email
    if (filter_var($email, FILTER_VALIDATE_EMAIL)) {
		//prepare statement to fetch user details based on username and password
		$statement = mysqli_prepare($con, "SELECT * FROM `a1812880_MBD`.`User` WHERE email = ? AND password = ?");
		mysqli_stmt_bind_param($statement, "ss", $email, $decodedPassword);
		$statement->execute();
		//store returned result
		mysqli_stmt_store_result($statement);
		$statement->bind_result($user_id, $name, $email, $password, $salt, $date, $month, $year, $school, $hp);
		while(mysqli_stmt_fetch($statement)){
			$response[user_id] = $user_id;
			$response[name] = $name;
			$response[email] = $email;
            $response[password] = $password;
			$response[date] = $date;
			$response[month] = $month;
			$response[year] = $year;
			$response[school] = $school;
			$response[hp] = $hp;
		}
		echo json_encode($response);
		mysqli_stmt_close($statement);
		mysqli_close($con);
	}
	//if email is incorrect
	else {
        $response[message] = "Incorrect Email";
	echo json_encode($salt);
        mysqli_close($con);
    }
?>