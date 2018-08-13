
<?php
 
require("include/config.php");
 
if (!empty($_POST)) {
   
    if (empty($_POST['Date']) || empty($_POST['Time'])|| empty($_POST['Name']) || empty($_POST['Desc'])) {
         
         
        $response["success"] = 0;
        $response["message"] = "data error";
         
         
        die(json_encode($response));
    }
     
   
     
     
    $query = "INSERT INTO Meetingschedules( time, date, name, Description) VALUES ( :time, :date, :name, :desc) ";
 
     
 
    $query_params = array(
        ':time' => $_POST['Time'],
        ':date'=>$_POST['Date'],
        ':name' => $_POST['Name'],
':desc'=> $_POST['Desc']

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
