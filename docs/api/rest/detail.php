<?php

    // read token to get YAML file name
    // read data from YAML file
    $file_name = null ;
    if(array_key_exists("token",$_REQUEST)) {
        $file_name = base64_decode($_REQUEST["token"]);
    }

    include('Spyc.php');
    $brand_name = "Yuktix" ;

    if($file_name == null || !file_exists($file_name)) {
        header('HTTP/1.1 404 Not Found');
        printf("<h1>404 Not Found</h1>");
        exit();
    }

    $data = Spyc::YAMLLoad($file_name);
    // print_r($data); exit ;

?>

<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title><?php echo $data["resource"] ?> </title>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0-wip/css/bootstrap.min.css">
<style> body {padding-top : 90px; } </style>

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
              <a class="navbar-brand" href="/"><?php echo $brand_name ?></a>
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
				<li><a href="index.php">Yuktix REST API</a></li>
                <li class="active"> <?php echo $data["resource"] ?> </li>
			</ol>
		</div>
		
	</div> <!--  breadcrumbs  -->
	
	<div class="row">
		<div class="col-lg-1">&nbsp;</div>
		<div class="col-lg-11">
			<div class="page-header">
            <h1><?php echo $data["resource"] ?></h1>
				<p>
                <span class="label label-info">API version <?php echo $data["version"] ?></span>
				</p>
			</div>
		</div>
	</div>
	
	
	

	<div class="row">
		<div class="col-lg-1">&nbsp;</div>
		<div class="col-lg-8">
            <p> <?php echo $data["description"] ?>
			<div class="panel">
				<div class="panel-heading">
					<h4>Resource URL</h4>
				</div>
				<div class="panel-body" class="para">
                <p><?php echo $data["resource_url"] ?> </p>
				</div>

			</div>

			<div class="well well-sm">

				<h3>Parameters</h3>

			</div>

			<table class="table">

				<tbody>
                    <?php foreach($data["parameters"] as $param) { ?>
					<tr>
                        <td><b><?php echo $param["name"] ?> </b></td>
                        <td><?php echo $param["description"] ; ?>  </td>
					</tr>
                    <?php } ?>

				</tbody>
			</table>


			<h3>Example</h3>

			<table class="table">
				<tbody>
					<tr>
                        <td><?php echo $data["method"] ?> </td>
                        <td><?php echo $data["sample_url"] ?> </td>
					</tr>
				</tbody>
			</table>

			<div>
				<pre>
                <code> <?php echo $data["sample_response"] ?> </code>
             </pre>
			</div>
		</div>

		<div class="col-lg-2">
			<h3>Resource Information</h3>
			<table class="table">

				<tbody>
					<tr>
						<td><b>Rate Limited?</b></td>
                        <td><?php echo $data["rate_limited"]?> </td>
					</tr>
					<tr>
						<td><b>Response Format</b></td>
                        <td><?php echo $data["response_format"]?> </td>
					</tr>
					<tr>
						<td><b>Authentication</b></td>
                        <td><?php echo $data["authentication"]?> </td>
					</tr>


				</tbody>
			</table>

		</div>
	</div>

</body>
</html>
