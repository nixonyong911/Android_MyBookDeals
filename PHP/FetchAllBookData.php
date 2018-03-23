<?php
    $con=mysqli_connect("mysql7.000webhost.com","a1812880_nixon","qwe123","a1812880_MBD");

	//prepare statement to fetch all the available book
    $statement = mysqli_prepare($con, "SELECT book_id, user_id, title, author, price  FROM `a1812880_MBD`.`Book`");
    $statement->execute();
	//store the result
    mysqli_stmt_store_result($statement);
        $result = array();
        $statement->bind_result($book_id, $user_id, $title, $author, $price);
        while(mysqli_stmt_fetch($statement)){
        $response[book_id] = $book_id;
        $response[user_id] = $user_id;
        $response[title] = $title;
        $response[author] = $author;
        $response[price] = $price;
        array_push($result, $response);
        }
		//return JSON result
    echo json_encode($result);
    mysqli_stmt_close($statement);
    mysqli_close($con);
?>