<?php

require("include/config.php");

if (!empty($_POST)) {

    $query = " SELECT * FROM confirmschedule WHERE MeetingID= :meetingid " ;
    
    $query_params = array(
        ':meetingid' => $_POST['meetingid'],


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
        $json["email"] = $rows["employee"];
$json['name']=$rows["Name"];




        



        array_push ($datareturn,$json);

            

            }
     echo stripcslashes(json_encode($datareturn, JSON_PRETTY_PRINT));



        




    }




} else {

}

?>