<?php 
	echo "这是一个测试，刚才传递的参数是：";
	foreach($_REQUEST as $param=>$value){
	echo "\n$param=$value";
	}
?>
