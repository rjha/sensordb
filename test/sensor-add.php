<?php
	
    require("common.inc");
    
    $post_url = "http://localhost:9090/sensordb/v1/sensor/add" ;

    function create_sensor($data) {
        global $post_url ;

        $post_data = json_encode($data);
        // print_r($post_data); exit ;

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


    $data = array("projectId" => "9471ed77-01b3-4734-a18f-8b8446a650aa",
                    "deviceId" => "2008126f-a0ff-47f4-96bf-086588604fdc" ,
                    "serialNumber" => "sensor001",
                    "metaData" => array("location" => "outdoor", "platform" => "arduino"),
                    "groupKeys" => array("location"));

    create_sensor($data);

    $data = array("projectId" => "9471ed77-01b3-4734-a18f-8b8446a650aa",
                    "deviceId" => "2008126f-a0ff-47f4-96bf-086588604fdc" ,
                    "serialNumber" => "sensor002",
                    "metaData" => array("location" => "indoor", "platform" => "Rpi"),
                    "groupKeys" => array("location"));

    create_sensor($data);


?>
