<?php
    $con=mysqli_connect("mysql7.000webhost.com","a1812880_nixon","qwe123","a1812880_MBD");
	//get variable information from Android studio
    $NewUser_id = $_POST["user_id"];
    $NewBook_id = $_POST["book_id"];
	//update user's interested book list
	$addInterest = mysqli_prepare($con, "INSERT INTO `a1812880_MBD`.`Interest` (user_id, book_id) VALUES (?, ?)");
	mysqli_stmt_bind_param($addInterest, "ss", $NewUser_id, $NewBook_id);
	mysqli_stmt_execute($addInterest);
	mysqli_stmt_close($addInterest);
	
	//get the current interested value
    $bookView;
	$getView = mysqli_prepare($con, "SELECT view  FROM `a1812880_MBD`.`Book` WHERE book_id = ?" );
	mysqli_stmt_bind_param($getView, "s", $NewBook_id);
	mysqli_stmt_execute($getView);
	$getView->bind_result($view);
	while(mysqli_stmt_fetch($getView)){
		$bookView = $view;
	}
	mysqli_stmt_close($getView);
	$bookView++;
	//increase the increased count
	$increaseView = mysqli_prepare($con, "UPDATE `a1812880_MBD`.`Book` SET view = ? WHERE book_id = ?");
	mysqli_stmt_bind_param($increaseView, "ss", $bookView, $NewBook_id);
	mysqli_stmt_execute($increaseView);
    mysqli_stmt_close($increaseView);
	mysqli_close($con);
?>
