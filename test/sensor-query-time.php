<?php
    
    require("common.inc");
	
	$post_url = "http://localhost:9090/sensordb/v1/query/sensor/time" ;
    // Unix timestamp
    $unix_ts = time();

	$data = array("projectId" => "p001" ,
        "serialNumber" => "sensor001",
        "time_slice" => array(
            "rstart" => array("value" => 14, "unit" => "MINUTE")));

	$post_data = json_encode($data);
    $cookies = array("COOKIE1" => "ABCD1234" , "COOKIE2" => "magic1234");
    $response = get_curl_response($post_url,$post_data,$cookies,false);
    $http_code = $response["code"];
    $result = $response["data"];


	if($http_code == 200 ) {
		printf("success \n \t response => \n" );
		print_r($result);
		printf(" \n \n ");

	} else {
		printf("ERROR \n" );
		print_r($result);
		printf(" \n \n ");
	}
	
?>
