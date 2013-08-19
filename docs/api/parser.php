<?php
    include('Spyc.php');
    $array = Spyc::YAMLLoad('rest.yaml');
    print_r($array);
    printf("description : \n %s \n ",$array["description"]);




?>
