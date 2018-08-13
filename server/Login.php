
<?php
 
require("include/config.php");
 
if (!empty($_POST)) {

    $query = " SELECT email , password , user  FROM admin WHERE email = :email And password =:password And user =:admin" ;
    
    $admin="admin";
    $query_params = array(
        ':email' => $_POST['email'],
        ':password' => $_POST['password'],
        ':admin'=>$admin

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
    
   // validation set to false initailly 
    $validated_info = false;
    
    $row = $statement->fetch();
    if ($row) {
       


       // password encrytion will be added here later 

        if ($_POST['password'] === $row['password']) {
            $adminsuccess = true;
        }else{

            $adminsuccess= false;
        }
    }
    $query1= " SELECT email , password , user FROM admin WHERE email = :email And password =:password And user =:employee" ;


$employee="employee";
    $query_params = array(
        ':email' => $_POST['email'],
        ':password' => $_POST['password'],
        ':employee'=>$employee

            );
    
    try {
        $statement   = $db->prepare($query1);
        $result = $statement->execute($query_params);
    }
    catch (PDOException $ex) {
        

        $response["success"] = 0;
        $response["message"] = "Database query error";
        die(json_encode($response));
        
    }
    
   // validation set to false initailly 
    $validated_info = false;
    
    $row = $statement->fetch();
    if ($row) {
       


       // password encrytion will be added here later 

        if ($_POST['password'] === $row['password']) {
            $employeesuccess = true;
        }else{

            $employeesuccess= false;
        }
    }




    
   
// if login detail match return json success as 1 else 0 and display relevant message 

    if ($adminsuccess) {
        $response["success"] = 2;
        $response["message"] = "Login admin successful!";

        die(json_encode($response));



    } else {



    }


    if($employeesuccess){
        $response["success"] = 1;
        $response["message"] = "Login employee successful!";
        die(json_encode($response));
    }else {
 

  
    }

        $response["success"] = 0 ;
        $response["message"] = "Invalid credidentials!";
        die(json_encode($response));

   


} else {
    
}

 
 
 
 
 
 
 
 
?>