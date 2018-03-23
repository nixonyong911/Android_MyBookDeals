<?php
    $con=mysqli_connect("mysql7.000webhost.com","a1812880_nixon","qwe123","a1812880_MBD");

	//get variable information from Android studio
    $NewUser_id = $_POST["user_id"];
    $NewBook_id = $_POST["book_id"];
	
	//prepare statement to remove the interested button
	$deleteInterest = mysqli_prepare($con, "DELETE FROM `a1812880_MBD`.`Interest` WHERE user_id = ? AND book_id = ?");
	mysqli_stmt_bind_param($deleteInterest, "ss", $NewUser_id, $NewBook_id);
	mysqli_stmt_execute($deleteInterest);
	mysqli_stmt_close($deleteInterest);
	
	//prepare statement to get the current interested count
    $bookView;
	$getView = mysqli_prepare($con, "SELECT view  FROM `a1812880_MBD`.`Book` WHERE book_id = ?" );
	mysqli_stmt_bind_param($getView, "s", $NewBook_id);
	mysqli_stmt_execute($getView);
	$getView->bind_result($view);
	while(mysqli_stmt_fetch($getView)){
		$bookView = $view;
	}
	
	mysqli_stmt_close($getView);
	$bookView--;
	
	//decrease the interested count by one
	$decreaseView = mysqli_prepare($con, "UPDATE `a1812880_MBD`.`Book` SET view = ? WHERE book_id = ?");
	mysqli_stmt_bind_param($decreaseView, "ss", $bookView, $NewBook_id);
	mysqli_stmt_execute($decreaseView);
    mysqli_stmt_close($decreaseView);
	mysqli_close($con);
?>
