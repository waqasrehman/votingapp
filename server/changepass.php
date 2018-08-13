<?php
require("include/config.php");
 
if (!empty($_POST)) {
   
    if (empty($_POST['employee']) || empty($_POST['currentpass'])|| empty($_POST['newpass'])) {
         
         
        $response["success"] = 0;
        $response["message"] = " Please enter require detail .";
         
         
        die(json_encode($response));
    }
     
   
    $query = " SELECT 1 FROM admin WHERE email = :email  AND password = :currentpass";
    $query_params = array(
        ':email' => $_POST['employee'],
        ':currentpass' => $_POST['currentpass']
    );
     
    try {
 
        $statement   = $db->prepare($query);
        $result = $statement->execute($query_params);
    }
    catch (PDOException $ex) {
 
        $response["success"] = 0;
        $response["message"] = "Database Error 1. Please Try Again!";
;
        die(json_encode($response));
    }
     
 
    $row = $statement->fetch();
    if (!$row) {
 
          
        $response["success"] = 0;
        $response["message"] = "email doesnt exist";
     
         
    }
     
     
      $query = "UPDATE admin SET password =:newpassword WHERE email= :email";
     
 
    $query_params = array(
        ':newpassword'=>$_POST['newpass'],
        ':email' => $_POST['employee']
 
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
    $response["message"] = "password  changed  successfully !";
 
    echo json_encode($response);
   
     
     
} else {
 
   
 
}
 
?>