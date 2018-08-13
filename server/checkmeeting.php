<?php
 
require("include/config.php");
 
if (!empty($_POST)) {
 
    $query = " SELECT employee, MeetingID  FROM confirmschedule  WHERE employee = :employee AND MeetingID =:meetingid" ;
     
    $query_params = array(
        ':employee' => $_POST['employee'],
        ':meetingid' => $_POST['meetingid']
 
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
 
        if ($_POST['employee'] === $row['employee'] AND $_POST['meetingid'] === $row['MeetingID']) {
            $login_successful = true;
 
        }else{
 
            $login_successful= false;
        }
    }
     
    
// if login detail match return json success as 1 else 0 and display relevant message 
 
    if ($login_successful) {
        echo "Attending";
 
    } else {

       echo "NotAttending";
    }
} else {
     
}
 
?>