<?php
	$mysql_hostname = "localhost";
$mysql_user = "id2536892_mit";
$mysql_password = "admin";
$mysql_database = "id2536892_checkstaff";
$prefix = "";
$bd = @mysqli_connect($mysql_hostname, $mysql_user,$mysql_password) or die("Could not connect database");
mysqli_select_db($bd,$mysql_database) or die("<h1>Could not select database<h1>");
      $use=$_POST['us'];
      $pas=$_POST['pa'];
	$dept=$_POST['de'];
      $r=mysqli_query($bd,"select * from Admin where user='$use' && pass='$pas' && dept='$dept'");
      if(mysqli_num_rows($r)==1)
      {	
           echo '<center><h1>Successfully logged in!</h1></center>';
      }
      else	
      {
	echo '<center><h1>Wrong Username or password!!!</h1></center>';
      }
     
?>						
