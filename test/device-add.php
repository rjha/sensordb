<?php
	
    require("common.inc");
    
    $post_url = "http://localhost:9090/sensordb/v1/device/add" ;

    function create_device($data) {
        global $post_url ;

        $post_data = json_encode($data);
        $cookies = array("COOKIE1" => "ABCD1234" , "COOKIE2" => "magic1234");
        $response = get_curl_response($post_url,$post_data,$cookies,false);
        $http_code = $response["code"];
        $result = $response["data"];


        if($http_code == 200 ) {
            print_r($result);
            printf(" \n \n ");

        } else {
            printf("ERROR \n" );
            print_r($result);
            printf(" \n \n ");
        }
    }


    $device = array("accountId" => "531623b2-e160-4ad1-a4c5-9c614a234112" ,
                    "name" => "My blinken lights" ,
                    "description" => "fundoo device running on Arduino board",
                    "manufacturer" => "yuktix",
                    "variables" => array( 
                        array("name" => "pressure", "unit" => "bar", "symbol" => "P", "type" => "numeric"),
                        array("name" => "temperature", "unit" => "kelvin", "symbol" => "T", "type" => "numeric")
                    )) ;

    create_device($device);


?>
