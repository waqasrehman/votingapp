<?php
 
require("include/config.php");
 
if (!empty($_POST)) {
   
    if (empty($_POST['ID']) || empty($_POST['name'])|| empty($_POST['user']) || empty($_POST['Confirm']) || empty($_POST['meetingname'])) {
         
         
        $response["success"] = 0;
        $response["message"] = "data error";
         
         
        die(json_encode($response));
    }
     
   
     
     
    $query = "INSERT INTO confirmschedule( MeetingID, Name, employee, Confirm,meetingname) VALUES ( :id, :name, :employee, :confirm, :mname) ";
 
     
 
    $query_params = array(
        ':id' => $_POST['ID'],
        ':name'=>$_POST['name'],
        ':employee' => $_POST['user'],
':confirm'=> $_POST['Confirm'],
':mname'=> $_POST['meetingname']

    );
     
    try {
        $statement   = $db->prepare($query);
        $result = $statement->execute($query_params);
    }
    catch (PDOException $ex) {
       
        $ex->getMessage();
         
        $response["success"] = 0;
        $response["message"] = "Database Error 2. Please Try Again!";
 
        die(json_encode($response));
    }
     
   
    $response["success"] = 1;
    $response["message"] = "Successfully Added!";
 
    echo json_encode($response);
 
     
} else {
 
}
 
?>
