<?php
	
    $bencode = "d4:tempi298e9:projectId4:p00112:serialNumber4:s001e" ;    
	$get_url = "http://localhost:9090/sensordb/v1/sms?".$bencode ;
    $result = file_get_contents($get_url); 
    print_r($result);

?>
