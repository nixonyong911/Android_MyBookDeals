<?php
    $con=mysqli_connect("mysql7.000webhost.com","a1812880_nixon","qwe123","a1812880_MBD");
	//get variable information from Android studio
    $Newuser_id = $_POST["user_id"];
    $Newbook_id = $_POST["book_id"];


	//Get all the user interested book
    $InterestStmt = mysqli_prepare($con, "SELECT * FROM `a1812880_MBD`.`Interest` WHERE user_id = ?");
    mysqli_stmt_bind_param($InterestStmt, "s", $Newuser_id );
    $InterestStmt->execute();
    mysqli_stmt_store_result($InterestStmt);   
    $interest = array();
    $InterestStmt->bind_result($user_id, $book_id);
    while(mysqli_stmt_fetch($InterestStmt)){
        $InterestRes[user_id] = $user_id;
        $InterestRes[book_id] = $book_id;
        array_push($interest, $InterestRes);
    }
	mysqli_stmt_close($InterestStmt);

	//Get all the book information, except interested book
	$book = mysqli_prepare($con, "SELECT * FROM `a1812880_MBD`.`Book` WHERE book_id = ?");
	mysqli_stmt_bind_param($book, "s"  , $Newbook_id);
    $book->execute();
    mysqli_stmt_store_result($book);
	$book->bind_result($book_id, $user_id, $name, $email, $hp, $isbn, $title, $author, $publisher, $edition, $price, $category, $location, $book_condition, $summary, $contact_type, $view, $bid, $bidder_id, $bidder_name);
    while(mysqli_stmt_fetch($book)){
        $result[book_id] = $book_id;
        $result[user_id] = $user_id;
        $result[name] = $name;
        $result[email] = $email;
        $result[hp] = $hp;
        $result[isbn] = $isbn;
        $result[title] = $title;
        $result[author] = $author;
        $result[publisher] = $publisher;
        $result[edition] = $edition;
        $result[price] = $price;
        $result[category] = $category;
        $result[location] = $location;
        $result[book_condition] = $book_condition;
        $result[summary] = $summary;
        $result[contact_type] = $contact_type;
        $result[view] = $view;
        $result[bid] = $bid;
        $result[bidder_id] = $bidder_id;
        $result[bidder_name] = $bidder_name;
        $result[wishlist] = "false";
	}
	//check whether the is has been interested by user.
	for($x = 0; $x < count($interest); $x++){
		if($interest[$x][book_id] == $result[book_id]){
			$result[wishlist] = "true";
		} 
	}
	
    echo json_encode($result);
    mysqli_stmt_close($book);
    mysqli_close($con);
?>
