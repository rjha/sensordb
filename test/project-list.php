<?php
	
    require("common.inc");

    $partition_key = null ;
    $row_key = null ;
    $post_url = "http://localhost:9090/sensordb/v1/project/list" ;

    while(true) {

        $data = new stdClass;
        $data->accountId = "df72fa9b-7b26-4a23-b4c0-b332195c5890" ;
        $data->scrolling = new stdClass ;

        if($partition_key != null ) {
            $data->scrolling->partition_key = $partition_key ;
            $data->scrolling->row_key = $row_key ;
        }

        $post_data = json_encode($data);

        $cookies = array("COOKIE1" => "ABCD1234" , "COOKIE2" => "magic1234");
        $response = get_curl_response($post_url,$post_data,$cookies,false);
        $code = $response["code"];
        $result = $response["data"];


        if($code == 200 ) {
            printf("\t server response => \n" );
            print_r($result);
            printf("\n");

            //pagination parameters
            $jsonObj = json_decode($result);
            $response = $jsonObj->response ;
            // no pagination? break out of loop
            if(is_object($jsonObj) && property_exists($jsonObj, "response")) { 
                $pagination = $response->pagination ;
                if(is_object($pagination)) {
                    $partition_key = $pagination->next_partition;
                    $row_key = $pagination->next_row;
                    if($partition_key == null || $row_key == NULL ) { goto finish ; }
                }
            }

        } else {
            printf("ERROR \n" );
            print_r($result);
            printf(" \n \n ");
            goto finish ;
        }

    } //:loop

    finish:
        echo " script done! \n" ;

 

?>
