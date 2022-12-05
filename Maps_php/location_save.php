<?php

if($_SERVER[METHOD_REQUEST]=='POST'){
	$x=$_POST['x'];
	$y=$_POST['y'];
	
}


   require_once'connect.php';

  $sql="insert into location_table (x,y) values('$x','$y')";

  if(mysqli_query($con,$sql)){

  	$resulte['success']="1";
  	$resulte['message']="success";

    echo json_encode($resulte);
    
    mysqli_close($con);

    }

    else{

    $resulte['success']="0";
  	$resulte['message']="error";

    echo json_encode($resulte);
    
    mysqli_close($con);
    
    }
?>