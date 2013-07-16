<?php

	$account = array("name" => "Rajeev Jha");
	$strAccount = json_encode($account);

	$postdata = http_build_query(array( 'var1' => $strAccount));

	$opts = array('http' =>
    array(
        'method'  => 'POST',
        'header'  => 'Content-type: application/x-www-form-urlencoded',
        'content' => $postdata
    ));

	$context  = stream_context_create($opts);
	$url = "http://localhost:9099/calculator/apiv1/test1?token=1234" ;
	$result = file_get_contents($url, false, $context);
	print_r($result);

?>
