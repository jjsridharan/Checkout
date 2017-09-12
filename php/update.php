<?php
	$mysql_hostname = "localhost";
$mysql_user = "id2536892_mit";
$mysql_password = "admin";
$mysql_database = "id2536892_checkstaff";
$prefix = "";
$bd = @mysqli_connect($mysql_hostname, $mysql_user,$mysql_password) or die("Could not connect database");
mysqli_select_db($bd,$mysql_database) or die("<h1>Could not select database<h1>");
      $use=$_POST['us'];
      $dep=$_POST['de'];
      $status=$_POST['st'];
      $time=$_POST['ti'];
      $r=mysqli_query($bd,"UPDATE Staff SET status='$status',time='$time' WHERE user='$use' && dept='$dep'");
      if($r)
      {	
           echo '<center><h1>Successfully logged in!</h1></center>';
      }
      else	
      {
	echo '<center><h1>Wrong Username or password!!!</h1></center>';
      }
     
?>						


