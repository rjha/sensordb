<?php
	
    require("common.inc");
    
    $post_url = "http://localhost:9090/sensordb/v1/project/add" ;

    function create_project($accountId, $name) {
        global $post_url ;

        $data = array("accountId" => $accountId, "name" => $name);
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
    }

    $account1 = "531623b2-e160-4ad1-a4c5-9c614a234112" ;
    $account2 = "df72fa9b-7b26-4a23-b4c0-b332195c5890" ;
    $account3 = "be9f6585-0c66-49dd-988b-170edf541aaf" ;

    $accounts = array($account1,$account2,$account3);
    foreach($accounts as $account) {
        for($i = 1 ; $i <= 19 ; $i++) {
            $name = "test-project-".$i ;
            create_project($account,$name);
        }
    }

?>
