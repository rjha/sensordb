<?php
	
    require("common.inc");
    
    $post_url = "http://localhost:9090/sensordb/v1/account/add" ;

    function create_account($name) {
        global $post_url ;

        $account = array("name" => $name);
        $post_data = json_encode($account);
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

    create_account("Stranger in Strange Land");


    /*
    
    create_account("Alice in wonderland");
    create_account("Big Heat");
    create_account("Candy Man");
    create_account("Detour");
    create_account("Entrapment");

    create_account("Freedom at midnight");
    create_account("Ghostbusters");
    create_account("Iruku");
    create_account("Jamon Jamon");
    create_account("Solyaris");

    create_account("Thief of Baghdad");
    create_account("Magnolia");
    create_account("Stalker");
    create_account("Last Year in Marinabad");
    create_account("Opening of Misty Beethoven");

    create_account("Boogie Nights");
    create_account("Hard Candy");
    create_account("Riffifi"); 
    create_account("Rosemary Baby"); 
    create_account("Groundhog Day"); 

    create_account("Bonnie and Clyde"); 
    create_account("48HR"); 
    create_account("pink panther"); 
    create_account("Dr Strangelove"); 
	
     */
?>
