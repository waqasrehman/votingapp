
<?php
 
require("include/config.php");
 
if (!empty($_POST)) {
 
    $query = " SELECT employee , surveyid FROM VOTES WHERE employee = :employee And surveyid=:surveyid" ;
     
    $query_params = array(
        ':employee' => $_POST['employee'],
        ':surveyid' => $_POST['surveyid']
 
            );
     
    try {
        $statement   = $db->prepare($query);
        $result = $statement->execute($query_params);
    }
    catch (PDOException $ex) {
         
 
        $response["success"] = 0;
        $response["message"] = "Database query error";
        die(json_encode($response));
         
    }
 
        $row = $statement->fetch();
 
    if ($row) {
        
 
 
       // password encrytion will be added here later 
 
        if ($_POST['employee'] === $row['employee'] AND $_POST['surveyid'] === $row['surveyid']) {
            $login_successful = true;
 
        }else{
 
            $login_successful= false;
        }
    }
     
    
// if login detail match return json success as 1 else 0 and display relevant message 
 
    if ($login_successful) {
        echo "Voted";
 
    } else {

       echo "NotVoted";
    }
} else {
     
}
 
?>