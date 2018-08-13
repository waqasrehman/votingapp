<?php


require("include/config.php");

//initial query
$query = "Select * FROM confirmschedule";

//execute query
try {
    $statement = $db->prepare($query);
    $result = $statement->execute();
}
catch (PDOException $ex) {
    $response["success"] = 0;
    $response["message"] = "Database Error!";
    die(json_encode($response));
}

//retrieve data from the rows on the table 
$rows = $statement->fetchAll();


if ($rows) {
    // $response["success"] = 1;
    // $response["message"] = "data Available";

    $response = array();
    
    foreach ($rows as $row) {
        $json= array();
        $json["meetingname"] = $row["meetingname"];
$json["meetingid"] = $row["MeetingID"];

        




        
        
        //update our repsonse JSON data
        array_push($response, $json);
    }
    
    // echoing JSON response and stripslshes to fix the format of the url
    echo stripcslashes(json_encode($response, JSON_PRETTY_PRINT));

    
    
    
} else {
        // $response["success"] = 0;
    //$response["message"] = "No data Available!";

    die(json_encode($response));
}

?>
