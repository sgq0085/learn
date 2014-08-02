<?php 
	header("Content-Type:text/html; charset=utf-8");
	echo "{ \"username\" : \"{$_REQUEST['username']}\" , \"content\" : \"{$_REQUEST['content']}\"}" 
?>
