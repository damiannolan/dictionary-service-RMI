<html>
<head>
<title>Dictionary Service</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" />
</head>
<body>
	<div class="container">
		<h2 class="text-center">Dictionary Service</h2>

		<form method="POST" action="DictionaryServlet">

			<div class="form-group">
				<label class="text-center" for="lookUp">Dictionary Search
					Service</label> <input type="text" id="lookUp" class="form-control"
					name="queryText" placeholder="Enter search query here" />
			</div>
			<div class="text-center">
				<input type="submit" class="btn btn-primary" name="submitBtn" />
			</div>
		</form>
	</div>
</body>
</html>
