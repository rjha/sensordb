<?php
    
    if(!file_exists("index.yaml")) {
        header('HTTP/1.1 404 Not Found');
        printf("<h1>404 Not Found</h1>");
        exit();
    }

    include('Spyc.php');
    $brand_name = "Yuktix" ;
    $api_version = "1.0" ;
    $detail_url = "detail.php?token=" ;
    $data = Spyc::YAMLLoad('index.yaml');
?>


<!doctype html>
<html>
<head>

<meta charset="UTF-8">
<title><?php echo $brand_name ?> REST API Documentation</title>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0-wip/css/bootstrap.min.css">
<style> body {padding-top :90px; } </style>           


</head>


<body>
	<div class="row">
		<div class="col-lg-offset-1 col-lg-11">
			<nav class="navbar navbar-fixed-top" role="navigation">
        	<!-- Brand and toggle get grouped for better mobile display -->
	        <div class="navbar-header">
	          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex5-collapse">
	            <span class="sr-only">Toggle navigation</span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </button>
              <a class="navbar-brand" href="#"><?php echo $brand_name ?></a>
	        </div>

       
          <ul class="nav navbar-nav">
            <li><a href="/">Home</a></li>
          </ul>
        </nav>
		</div>
	</div> <!-- top toolbar  -->
	
	
	<div class="row">
		<div class="col-lg-offset-1 col-lg-8">
			<ol class="breadcrumb">
				<li><a href="/">Home</a></li>
				<li class="active">Yuktix Rest API</li>
			</ol>
		</div>
		
	</div> <!--  breadcrumbs  -->
	
	<div class="row">
		<div class="col-lg-offset-1 col-lg-11">
			<div class="page-header">
				<h1>Yuktix Rest API</h1>
			</div>
	 	
	 	</div>
	</div>
	
	
	<div class="row">
		<div class="col-lg-1">&nbsp;</div>
		<div class="col-lg-8">
			<table class="table table-hover">
					<thead>
						<tr>
						<th>Resource</th>
						<th>Description</th>
						</tr>
					
					</thead>
										
					<tbody>
                        <?php foreach($data as $api ) {  
                            $token = "#" ;
                            if(array_key_exists("file_name",$api)){ $token = base64_encode($api["file_name"]); }
                            $api_url = $detail_url.$token ; ?>
						<tr>
                            <td><a href="<?php echo $api_url?>"><b><?php echo $api["name"] ?> </b></a></td>
                            <td><?php echo $api["description"] ?> </td> 
						</tr>

                        <?php } ?> 
					</tbody>
				
				</table>
		</div>
		<div class="col-lg-2"> 
			Right sidebar
		</div>
	</div>
	
</body>
</html>
