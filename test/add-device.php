<?php


	
	// cookies

	$cookie1 = "ABCD1234" ; $cookie2 = "magic1234" ;
	$cookie = sprintf("Cookie: COOKIE_1=%s; COOKIE_2=%s\r\n", $cookie1, $cookie2);

	// Actual post data

	$device = array("name" => "machine works sample device" ,
				"manufacturer" => "machine works ltd. " ,
				"version" => "1.0.0.1" ,
				"description" => "first device by machine works" ,
				"deviceId" => "mwx1001" ,
				"variables" => array(
					array("name" => "pressure" ,"unit" => "lb", "symbol" => "P"), 
					array("name" => "temperature" ,"unit" => "k", "symbol" => "T")
				));

	$strJson = json_encode($device);
	$post_data = $strJson."\r\n" ;
	// print_r($post_data); 
	// exit ;

	$length = strlen($post_data) ;

	$headers = array(
		"Content-Type: application/json; charset=UTF-8",
		"Content-Length: ".$length,
		$cookie
	   );

	
	$post_url = "http://localhost:8080/sensordb/apiv1/device/add?token=bobo" ;

	$ch = curl_init();
	$user_agent = "Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20130405 Firefox/22.0" ;

    curl_setopt ($ch, CURLOPT_USERAGENT, $user_agent);
    curl_setopt ($ch, CURLOPT_TIMEOUT, 60);
    curl_setopt ($ch, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt ($ch, CURLOPT_FOLLOWLOCATION, 1);
        
	curl_setopt($ch,CURLOPT_HTTPHEADER,$headers);
    curl_setopt ($ch, CURLOPT_URL, $post_url);
    curl_setopt ($ch, CURLOPT_POSTFIELDS, $post_data);
    curl_setopt ($ch, CURLOPT_POST, 1);
	$result = curl_exec ($ch);
	echo "\n ---- response ------- \n" ;
	print_r($result);
	echo "\n ---------------------- \n" ;

?>
