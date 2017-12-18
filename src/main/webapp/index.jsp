<html>
<head>
<title>Dictionary Service</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" />
</head>
<body>
	<h2>Dictionary Service</h2>



	<form method="POST" action="DictionaryServlet">

		<div class="form-group">
			<label for="lookUp">Dictionary Search Service</label> 
			<input type="text" id="lookUp"  class="form-control" name="queryText" placeholder="Enter search query here" />

		</div>
		<input type="submit" class="btn btn-primary" name="submitBtn" />
	</form>
	
</body>
</html>
