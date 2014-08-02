<?php 
	header("Content-Type:text/html; charset=utf-8");
	echo "<h1>{$_REQUEST['name']},{$_REQUEST['address']},{$_REQUEST['comment']}</h1>";
?>