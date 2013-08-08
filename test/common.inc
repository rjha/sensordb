<?php

    function get_curl_response($post_url,$post_data,$cookies,$debug) {

        // add linefeed and carriage return
        $post_data = $post_data."\r\n" ;
        $post_headers = get_post_headers($post_data,$cookies);
	    
        $ch = curl_init();
		$user_agent = "Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20130405 Firefox/22.0" ;

		curl_setopt ($ch, CURLOPT_USERAGENT, $user_agent);
		curl_setopt ($ch, CURLOPT_TIMEOUT, 60);
		curl_setopt ($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt ($ch, CURLOPT_FOLLOWLOCATION, 1);
			
		curl_setopt($ch,CURLOPT_HTTPHEADER,$post_headers);
		curl_setopt ($ch, CURLOPT_URL, $post_url);
		curl_setopt ($ch, CURLOPT_POSTFIELDS, $post_data);
		curl_setopt ($ch, CURLOPT_POST, 1);
		// @debugging
		if($debug) {
			curl_setopt($ch, CURLOPT_VERBOSE, true);
        }

        $result = curl_exec ($ch);
        $http_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);

        $data = array("code" => $http_code , "data" => $result);
        return $data ;
    }

    function get_cookie_header($cookies) {

        $text = "Cookie: ";
        foreach($cookies as $name => $value) {
            $text = $text.$name."=".$value." " ;
        }

        $text = $text."\r\n" ;
        return $text ;
    }

    function get_post_headers($post_data,$cookies) {
        $cookie_header = get_cookie_header($cookies);
        $length = strlen($post_data) ;
        $headers = array( "Content-Type: application/json; charset=UTF-8", "Content-Length: ".$length, $cookie_header);
        return $headers ;

    }


?>
