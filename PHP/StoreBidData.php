<?php
    $con=mysqli_connect("mysql7.000webhost.com","a1812880_nixon","qwe123","a1812880_MBD");
    //get variable information from Android studio
    $bid = $_POST["bid"];
    $book_id = $_POST["book_id"];
    $bidder_id = $_POST["bidder_id"];
    $bidder_name = $_POST["bidder_name"];
    
    //prepare statement to store bid
    $statement = mysqli_prepare($con, "UPDATE `a1812880_MBD`.`Book` SET bid = ?, bidder_id = ?, bidder_name = ? WHERE book_id = ?");
    mysqli_stmt_bind_param($statement, "ssss", $bid, $bidder_id, $bidder_name, $book_id);
    mysqli_stmt_execute($statement);
    mysqli_stmt_close($statement);
    mysqli_close($con);
?>
