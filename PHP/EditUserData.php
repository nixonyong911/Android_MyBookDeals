<?php
    $con=mysqli_connect("mysql7.000webhost.com","a1812880_nixon","qwe123","a1812880_MBD");
	
	//get variable information from Android studio
    $NewUser_id = $_POST["user_id"];
    $Newname = $_POST["name"];
    $NewEmail = $_POST["email"];
    $Newpassword = $_POST["password"];
    $PasswordVerify = $_POST["oldpassword"];
    $Newdate= $_POST["date"];
    $Newmonth= $_POST["month"];
    $Newyear= $_POST["year"];
    $Newschool= $_POST["school"];
    $Newhp= $_POST["hp"];
    $response = array();
	$existing = FALSE;
	$sameAccount = FALSE;
	$verified = FALSE;
    
	//validation for email
    if (filter_var($NewEmail, FILTER_VALIDATE_EMAIL)) {
		
		//prepare statement fetch details for the user
		$validate = mysqli_prepare($con, "SELECT * FROM `a1812880_MBD`.`User` WHERE user_id= ?");
		mysqli_stmt_bind_param($validate, "s", $NewUser_id);
		$validate->execute();
		mysqli_stmt_store_result($validate);
		$validate->bind_result($user_id, $name, $email, $password, $salt, $date, $month, $year, $school, $hp);
		while(mysqli_stmt_fetch($validate)){
			$encryptedPassword  = base64_encode(sha1($PasswordVerify . $salt, true) . $salt);
			//validate existing password 
			if($encryptedPassword == $password){
				$verified = TRUE;
			} else {
                $response[message] = "Incorrect Existing Password";
            }
			//validate email, if same user, it will have same email
			if($NewEmail == $email AND $NewUser_id == $user_id){
				$sameAccount = TRUE;
			
			//validate whether email inserted is used by other user.
			if($NewEmail == $email AND $sameAccount == FALSE AND $verified == TRUE){
				$response[message] = "Existing Email";
				$existing = TRUE;
			}
		}
        $NewEncryptedPassword = base64_encode(sha1($Newpassword . $salt, true) . $salt);
		mysqli_stmt_close($validate);
		
		//prepare statement to update the user details
		if($verified == TRUE And $existing == FALSE){
			$statement = mysqli_prepare($con, "UPDATE `a1812880_MBD`.`User` SET name = ?, email= ?, password=?, date=?, month=?, year=?, school=?, hp=? WHERE user_id = ?");
			mysqli_stmt_bind_param($statement, "sssssssss", $Newname, $NewEmail, $NewEncryptedPassword , $Newdate, $Newmonth, $Newyear, $Newschool, $Newhp, $NewUser_id);
			mysqli_stmt_execute($statement);
			mysqli_stmt_close($statement);
			$response[message] = "Successful Update";
			$response[name] = $Newname;
			$response[email] = $NewEmail;
			$response[password] = $encryptedPassword;
			$response[date] = $Newdate;
			$response[month] = $Newmonth;
			$response[year] = $Newyear;
			$response[school] = $Newschool;
			$response[hp] = $Newhp;
			$response[user_id] = $NewUser_id;
		}

		echo json_encode($response);
		mysqli_close($con);
	//if email is invalid
	}else {
        $response[message] = "Incorrect Email";
	echo json_encode($response);
        mysqli_close($con);
    }
?>
