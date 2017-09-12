<?php
$mysql_hostname = "localhost";
$mysql_user = "id2536892_mit";
$mysql_password = "admin";
$mysql_database = "id2536892_checkstaff";
$prefix = "";
$bd = @mysqli_connect($mysql_hostname, $mysql_user,$mysql_password) or die("Could not connect database");
mysqli_select_db($bd,$mysql_database) or die("<h1>Could not select database<h1>");
	 $dep=$_POST['de'];
      $r=mysqli_query($bd,"Select * from Announce where dept='$dep'");
	$res="";
	while($row=mysqli_fetch_assoc($r))
	{
		$last=$row['last'];
                if($last==0) $last=20;
		for($i=-1;$i<=8;$i++)	
		{
			$val=($last + $i)%10;
			$val="news".$val;         
                        if($row[$val]!=NULL)               
			$res.=$row[$val]."###";
		}
	}
	echo $res;
?>