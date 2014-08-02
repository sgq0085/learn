<?php 
	header("Content-Type:text/html; charset=utf-8");
	echo "{ name : '{$_REQUEST['name']}' , address :  '{$_REQUEST['address']}' ,comment :  '{$_REQUEST['comment']}' }";
?>