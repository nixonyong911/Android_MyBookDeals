<?php
    $con=mysqli_connect("mysql7.000webhost.com","a1812880_nixon","qwe123","a1812880_MBD");
    //get variable information from Android studio
    $user_id= $_POST["user_id"];
    $name = $_POST["name"];
    $email= $_POST["email"];
    $hp= $_POST["hp"];
    $isbn= $_POST["isbn"];
    $title= $_POST["title"];
    $author= $_POST["author"];
    $publisher= $_POST["publisher"];
    $edition = $_POST["edition"];
    $price = $_POST["price"];
    $category= $_POST["category"];
    $location= $_POST["location"];
    $book_condition= $_POST["book_condition"];
    $summary= $_POST["summary"];
    $contact_type= $_POST["contact_type"];
    //prepare statement to insert the book into database
    $statement = mysqli_prepare($con, "INSERT INTO `a1812880_MBD`.`Book` (user_id, name, email, hp, isbn, title, author, publisher, edition, price, category, location, summary, book_condition, contact_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "sssssssssssssss", $user_id, $name , $email, $hp, $isbn, $title, $author, $publisher, $edition, $price, $category, $location, $summary, $book_condition, $contact_type);
    mysqli_stmt_execute($statement);
    mysqli_stmt_close($statement);
    mysqli_close($con);
?>
