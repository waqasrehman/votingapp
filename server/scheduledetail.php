
<?php

require("include/config.php");

if (!empty($_POST)) {

    $query = " SELECT * FROM Meetingschedules WHERE MeetingID= :id" ;
    
    $query_params = array(
        ':id' => $_POST['ID'],


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
    
    $result = $statement->fetchAll();

    if($result){

            $datareturn =array();

            foreach($result as $rows){
        $json = array();
        $json["Name"] = $rows["name"];
$json["Time"] = $rows["time"];
$json["Date"] = $rows["date"];
$json["Description"] = $rows["Description"];




        



        array_push ($datareturn,$json);

            

            }
     echo stripcslashes(json_encode($datareturn, JSON_PRETTY_PRINT));



        




    }




} else {

}

?>
