<?php
    $con=mysqli_connect("mysql7.000webhost.com","a1812880_nixon","qwe123","a1812880_MBD");
	//get variable information from Android studio
    $user_id =$_POST["user_id"];

	//prepare statement to fetch book data which posted by the user
    $statement = mysqli_prepare($con, "SELECT book_id, user_id, title, author, view  FROM `a1812880_MBD`.`Book` WHERE user_id = ?");
    mysqli_stmt_bind_param($statement, "s", $user_id);
    $statement->execute();
	//store returned result
    mysqli_stmt_store_result($statement);
        $result = array();
        $statement->bind_result($book_id, $user_id, $title, $author, $view  );
        while(mysqli_stmt_fetch($statement)){
        $response[book_id] = $book_id;
        $response[user_id] = $user_id;
        $response[title] = $title;
        $response[author] = $author;
        $response[view] = $view ;
        array_push($result, $response);
        }
	//return JSON result
    echo json_encode($result);
    mysqli_stmt_close($statement);
    mysqli_close($con);
?>