<?php
	$myList = $_POST["myList"];
	foreach( $myList as $list){
		echo $list."\n";
	}
?>