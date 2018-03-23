<?php
    $con=mysqli_connect("mysql7.000webhost.com","a1812880_nixon","qwe123","a1812880_MBD");
	//get variable information from Android studio
    $Newname = $_POST["name"];
    $NewEmail =  $_POST["email"];
    $NewEmail = strtolower($NewEmail);
    $Newpassword = $_POST["password"];
    $Newdate= $_POST["date"];
    $Newmonth= $_POST["month"];
    $Newyear= $_POST["year"];
    $Newschool= $_POST["school"];
    $Newhp= $_POST["hp"];
    $response = array();
    $existing = false;
    
	//validate email
    if (filter_var($NewEmail, FILTER_VALIDATE_EMAIL)) {
		//prepare statment to check whether email is used 
		$validate = mysqli_prepare($con, "SELECT email FROM `a1812880_MBD`.`User` WHERE email = ?");
		mysqli_stmt_bind_param($validate, "s", $NewEmail);
		$validate->execute();
		mysqli_stmt_store_result($validate);
		$validate->bind_result($email);
		while(mysqli_stmt_fetch($validate)){
				if($NewEmail == $email){
				$response[name] = $Newname;
				$response[email] = $NewEmail;
				$response[password] = $encryptedPassword;
				$response[salt] = $salt;
				$response[date] = $Newdate;
				$response[month] = $Newmonth;
				$response[year] = $Newyear;
				$response[school] = $Newschool;
				$response[hp] = $Newhp;
			        $response[message] = "Existing Email";
			        $existing = true;
				}
				
		}
        mysqli_stmt_close($validate);
		//if the email was not used
		if($existing == false){
			//password encryption
			$salt = sha1(rand());
			$salt = substr($salt, 0, 10);
			$encryptedPassword = base64_encode(sha1($Newpassword . $salt, true) . $salt);
			
			//prepare statment to insert user details
			$statement = mysqli_prepare($con, "INSERT INTO `a1812880_MBD`.`User` (name, email, password, salt, date, month, year, school, hp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? )");	
			mysqli_stmt_bind_param($statement, "sssssssss", $Newname, $NewEmail, $encryptedPassword, $salt, $Newdate, $Newmonth, $Newyear, $Newschool, $Newhp);
			mysqli_stmt_execute($statement);
			mysqli_stmt_close($statement);
				$response[message] = "Successful Update";
				$response[name] = $Newname;
				$response[email] = $NewEmail;
				$response[password] = $encryptedPassword;
				$response[salt] = $salt;
				$response[date] = $Newdate;
				$response[month] = $Newmonth;
				$response[year] = $Newyear;
				$response[school] = $Newschool;
				$response[hp] = $Newhp;
				}
				
		//prepare statement to get the registed user's ID		
		$validateSTMT = mysqli_prepare($con, "SELECT user_id FROM `a1812880_MBD`.`User` WHERE email = ?");
		mysqli_stmt_bind_param($validateSTMT, "s", $NewEmail);
		$validateSTMT->execute();
		mysqli_stmt_store_result($validateSTMT);
		$validateSTMT->bind_result($user_id);
		while(mysqli_stmt_fetch($validateSTMT)){
			$response[user_id] = $user_id;
		}		
		echo json_encode($response);
		mysqli_close($con);
	}
	//else return incorrect email
	else {
				$response[name] = $Newname;
				$response[email] = $NewEmail;
				$response[password] = $encryptedPassword;
				$response[salt] = $salt;
				$response[date] = $Newdate;
				$response[month] = $Newmonth;
				$response[year] = $Newyear;
				$response[school] = $Newschool;
				$response[hp] = $Newhp;
				$response[message] = "Incorrect Email";
	echo json_encode($response);
        mysqli_close($con);
    }
?>
