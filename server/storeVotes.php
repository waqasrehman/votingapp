<?php
 
require("include/config.php");
 
if (!empty($_POST)) {
   
    if (empty($_POST['surveyid']) || empty($_POST['employee'])|| empty($_POST['Voted']) || empty($_POST['name'])) {
         
         
        $response["success"] = 0;
        $response["message"] = "data error";
         
         
        die(json_encode($response));
    }
     
   
     
     
    $query = "INSERT INTO VOTES( surveyid, employee , Voted, name ) VALUES ( :surveyid, :employee, :voted, :name ) ";
 
     
 
    $query_params = array(
        ':surveyid' => $_POST['surveyid'],
        ':employee'=>$_POST['employee'],
        ':voted' => $_POST['Voted'],
':name'=> $_POST['name']

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