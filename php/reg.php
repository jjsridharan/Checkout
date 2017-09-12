<?php
	$mysql_hostname = "localhost";
$mysql_user = "id2536892_mit";
$mysql_password = "admin";
$mysql_database = "id2536892_checkstaff";
$prefix = "";
$bd = @mysqli_connect($mysql_hostname, $mysql_user,$mysql_password) or die("Could not connect database");
mysqli_select_db($bd,$mysql_database) or die("<h1>Could not select database<h1>");
	$user=$_POST['re'];
	$pass=$_POST['na'];
	$mai=$_POST['ma'];
	$pho=$_POST['ph'];
     $dep=$_POST['de'];
     $fla=1;
header('Refresh: 5; url=register.php');
	        if(empty($user) || empty($pass) || empty($mai) || empty($pho) || empty($dep))
        {
                $fla=0;
        }
        if($fla!=0)
        {
	    $r=mysqli_query($bd,"INSERT INTO Admin VALUES('$user','$pass','$dep','$mai','$pho')");
	    if($r)
	    {	
               	echo '<center><h1>Successfully Registered!!</h1></center>';
		echo '<center><h2>Wait while you are forwarded....</h2></center>'; 	
	    }
          else
		echo '<center><h1>Not Registered Try Again!!</h1></center>';
       
        }
	else
		echo '<center><h1>Invalid User Input!!</h1></center>';
?>	