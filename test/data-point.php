<?php
	

	function get_curl_handle($headers,$url,$data,$debug=false) {

		$ch = curl_init();
		$user_agent = "Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20130405 Firefox/22.0" ;

		curl_setopt ($ch, CURLOPT_USERAGENT, $user_agent);
		curl_setopt ($ch, CURLOPT_TIMEOUT, 60);
		curl_setopt ($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt ($ch, CURLOPT_FOLLOWLOCATION, 1);
			
		curl_setopt($ch,CURLOPT_HTTPHEADER,$headers);
		curl_setopt ($ch, CURLOPT_URL, $url);
		curl_setopt ($ch, CURLOPT_POSTFIELDS, $data);
		curl_setopt ($ch, CURLOPT_POST, 1);
		// @debugging
		if($debug)
			curl_setopt($ch, CURLOPT_VERBOSE, true);
		return $ch ;


	}


	// cookies

	$cookie1 = "ABCD1234" ; $cookie2 = "magic1234" ;
	$cookie = sprintf("Cookie: COOKIE_1=%s; COOKIE_2=%s\r\n", $cookie1, $cookie2);

	// Add a  data point
	$data = array(
				"projectId" => "p001" ,
				"serialNumber" => "sensor001" ,
				"readings" => array(
					array("name" => "pressure" ,"value" => "70.1", "timestamp" => "12345678"), 
					array("name" => "temperature" ,"value" => "270.1", "timestamp" => "12345678"), 
					array("name" => "pressure" ,"value" => "78.1", "timestamp" => "12345679"), 
                    array("name" => "temperature" ,"value" => "250.1", "timestamp" => "12345679")),
                 "metaData" => array( array( "location" => "building001")));

	$strJson = json_encode($data);
	// extra linefeed and carriage is required for POST data 
	$post_data = $strJson."\r\n" ;

	$length = strlen($post_data) ;
	$headers = array(
		"Content-Type: application/json; charset=UTF-8",
		"Content-Length: ".$length,
		$cookie
	   );

	
	$post_url = "http://localhost:9090/sensordb/v1/datapoint?token=t001" ;
	$ch = get_curl_handle($headers,$post_url,$post_data) ;

	$result = curl_exec ($ch);
	$http_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);

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
