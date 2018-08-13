<?php

require("include/config.php");

if (!empty($_POST)) {
  
    if (empty($_POST['question']) && empty($_POST['Voteoption'])  && empty($_POST['state'])) {
        
        
        $response["success"] = 0;
        $response["message"] = "data error";
        
        
        die(json_encode($response));
    }



 $query_params = array(
      ':question' => $_POST['question'],
':Voteoption'=>$_POST['Voteoption'],
':state'=> $_POST['state']

    );


        $query = "INSERT INTO question( question, Voteoption, state ) VALUES ( :question, :Voteoption, :state )";
    

    
      
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