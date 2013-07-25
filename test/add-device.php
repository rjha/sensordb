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

	// Add a device
	$device = array(
				"name" => "machine works sample device" ,
				"manufacturer" => "machine works ltd. " ,
				"version" => "1.0.0.1" ,
				"description" => "first device by machine works" ,
				"variables" => array(
					array("name" => "pressure" ,"unit" => "lb", "symbol" => "P"), 
					array("name" => "temperature" ,"unit" => "k", "symbol" => "T")
				));

	$strJson = json_encode($device);
	// extra linefeed and carriage is required for POST data 
	$post_data = $strJson."\r\n" ;

	$length = strlen($post_data) ;
	$headers = array(
		"Content-Type: application/json; charset=UTF-8",
		"Content-Length: ".$length,
		$cookie
	   );

	
	$post_url = "http://localhost:9099/sensordb/apiv1/device/add?token=bobo" ;
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
