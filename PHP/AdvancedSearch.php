<?php
    $con=mysqli_connect("mysql7.000webhost.com","a1812880_nixon","qwe123","a1812880_MBD");
	
	//get variable information from Android studio
    $search1 =$_POST["search1"];
	$search2 =$_POST["search2"];
	$search3 =$_POST["search3"];
	$search4 =$_POST["search4"];
	$search5 =$_POST["search5"];
	$search6 =$_POST["search6"];
	$search7 =$_POST["search7"];
	
	$key1 =$_POST["key1"];
	$key2 =$_POST["key2"];
	$key3 =$_POST["key3"];
	$key4 =$_POST["key4"];
	$key5 =$_POST["key5"];
	$key6 =$_POST["key6"];
	$key7 =$_POST["key7"];
	
	$result = array();
	
	//check how many field has been filled for advanced search and prepared statement
	if(isset($search7, $search6, $search5, $search4, $search3, $search2, $search1)){
		$statement = mysqli_prepare($con, "SELECT book_id, user_id, title, author, price  FROM `a1812880_MBD`.`Book` WHERE $key1 LIKE CONCAT('%', ?, '%') AND $key2 LIKE CONCAT('%', ?, '%') AND $key3 LIKE CONCAT('%', ?, '%') AND $key4 LIKE CONCAT('%', ?, '%') AND $key5 LIKE CONCAT('%', ?, '%') AND $key6 LIKE CONCAT('%', ?, '%') AND $key7 LIKE CONCAT('%', ?, '%') ");
		mysqli_stmt_bind_param($statement, "sssssss"  , $search1, $search2, $search3, $search4, $search5, $search6, $search7);
	} else if(isset($search6, $search5, $search4, $search3, $search2, $search1)){
		$statement = mysqli_prepare($con, "SELECT book_id, user_id, title, author, price  FROM `a1812880_MBD`.`Book` WHERE $key1 LIKE CONCAT('%', ?, '%') AND $key2 LIKE CONCAT('%', ?, '%') AND $key3 LIKE CONCAT('%', ?, '%') AND $key4 LIKE CONCAT('%', ?, '%') AND $key5 LIKE CONCAT('%', ?, '%') AND $key6 LIKE CONCAT('%', ?, '%')");
		mysqli_stmt_bind_param($statement, "ssssss"  , $search1, $search2, $search3, $search4, $search5, $search6);	
	} else if(isset($search5, $search4, $search3, $search2, $search1)){
		$statement = mysqli_prepare($con, "SELECT book_id, user_id, title, author, price  FROM `a1812880_MBD`.`Book` WHERE $key1 LIKE CONCAT('%', ?, '%') AND $key2 LIKE CONCAT('%', ?, '%') AND $key3 LIKE CONCAT('%', ?, '%') AND $key4 LIKE CONCAT('%', ?, '%') AND $key5 LIKE CONCAT('%', ?, '%')");
		mysqli_stmt_bind_param($statement, "sssss"  , $search1, $search2, $search3, $search4, $search5);
	} else if(isset($search4, $search3, $search2, $search1)){
		$statement = mysqli_prepare($con, "SELECT book_id, user_id, title, author, price  FROM `a1812880_MBD`.`Book` WHERE $key1 LIKE CONCAT('%', ?, '%') AND $key2 LIKE CONCAT('%', ?, '%') AND $key3 LIKE CONCAT('%', ?, '%') AND $key4 LIKE CONCAT('%', ?, '%')");
		mysqli_stmt_bind_param($statement, "ssss"  , $search1, $search2, $search3, $search4);
	} else if(isset($search3, $search2, $search1)){
		$statement = mysqli_prepare($con, "SELECT book_id, user_id, title, author, price  FROM `a1812880_MBD`.`Book` WHERE $key1 LIKE CONCAT('%', ?, '%') AND $key2 LIKE CONCAT('%', ?, '%') AND $key3 LIKE CONCAT('%', ?, '%')");
		mysqli_stmt_bind_param($statement, "sss"  , $search1, $search2, $search3);
	} else if(isset($search2, $search1)){
		$statement = mysqli_prepare($con, "SELECT book_id, user_id, title, author, price  FROM `a1812880_MBD`.`Book` WHERE $key1 LIKE CONCAT('%', ?, '%') AND $key2 LIKE CONCAT('%', ?, '%')");
		mysqli_stmt_bind_param($statement, "ss"  , $search1, $search2);
	} else{
        $statement = mysqli_prepare($con, "SELECT book_id, user_id, title, author, price  FROM `a1812880_MBD`.`Book` WHERE $key1 LIKE CONCAT('%', ?, '%')");
		mysqli_stmt_bind_param($statement, "s"  , $search1);
	}
	
    $statement->execute(); //Execute the statement prepared on aboved case
	mysqli_stmt_store_result($statement); // Store the executed result
	$statement->bind_result($book_id, $user_id, $title, $author, $price); //bind the result to a variable
	while(mysqli_stmt_fetch($statement)){ //store the result into an array and push it into another array
		$response[book_id] = $book_id;
        $response[user_id] = $user_id;
        $response[title] = $title;
        $response[author] = $author;
        $response[price] = $price;
		array_push($result, $response);
	}
    echo json_encode($result); //return JSON result
	mysqli_stmt_close($statement); //close statement
    mysqli_close($con); //close connection
?>