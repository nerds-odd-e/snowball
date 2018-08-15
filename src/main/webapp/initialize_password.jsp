<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<!-- Bootstrap Core CSS -->
<link href="resources/lib/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="resources/lib/bootstrap/css/sb-admin.css" rel="stylesheet">

<link href="resources/lib/bootstrap/css/plugins/morris.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="resources/lib/bootstrap/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
</head>
<body>
		<div id="page-wrapper">
			<form name="Initialize-Password" id="initialize-password" method="post"
				action="initializePassword">

				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Initialize Password for Massive Mailer</h1>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-12">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Initialize Password</h3>
								</div>
								<div class="panel-body">

									<div class="row">
										<div class="col-lg-1">Password:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="password"
												id="password">
										</div>
									</div>
									<br> <br>

									<div class="row">
										<div class="col-lg-1">Password confirm:</div>
										<div class="col-lg-11">
											<input type="password" class="form-control" name="password_confirm"
												id="password_confirm">
										</div>
									</div>
									<br> <br>

									<div class="row">
										<div class="col-lg-12">
											<button type="button" class="btn btn-default"
												id="submit">Login</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

			</form>
		</div>
</body>
<!-- jQuery -->
<script type="text/javascript"
	src="resources/lib/bootstrap/js/jquery.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="resources/lib/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/js/addContact.js"></script>
<script type="text/javascript" src="resources/js/populateCountriesDropdown.js"></script>
</html>