<?php
    $con=mysqli_connect("mysql7.000webhost.com","a1812880_nixon","qwe123","a1812880_MBD");
	//get variable information from Android studio
    $book_id =$_POST["book_id"];

	//prepare statement to remove book based on book ID
    $statement = mysqli_prepare($con, "DELETE FROM `a1812880_MBD`.`Book` WHERE book_id = ?");
    mysqli_stmt_bind_param($statement, "s", $book_id);
    $statement->execute();
    mysqli_stmt_close($statement);
    mysqli_close($con);
?>