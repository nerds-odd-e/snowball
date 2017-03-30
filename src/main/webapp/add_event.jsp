<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Event</title>
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
        <jsp:include page="ui_common.jsp" />
		<div id="page-wrapper">
			<form name="addEvent" id="addEvent" method="post"
				action="events">

				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Add Event</h1>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-12">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Event Information</h3>
								</div>
								<div class="panel-body">

									<div class="row">
										<div class="col-lg-1">Title:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="evtTitle"
												id="evtTitle">
										</div>
									</div>
									<br> <br>
									<div class="row">
										<div class="col-lg-1">Content:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="content"
												id="content">
										</div>
									</div>
									<br> <br>
									<div class="row">
										<div class="col-lg-1">Location:</div>
										<div class="col-lg-11">
											<select id="locationdrp" class="form-control" name="location">
											    <option value="Bangkok">Bangkok</option>
											    <option value="Singapore">Singapore</option>
											    <option value="Tokyo">Tokyo</option>
											    <option value="Kuala Lumpur">Kuala Lumpur</option>
											    <option value="Jakarta">Jakarta</option>
											    <option value="Seoul">Seoul</option>
											    <option value="New Delhi">New Delhi</option>
											    <option value="Bangalore">Bangalore</option>
											    <option value="Manila">Manila</option>
											    <option value="Beijing">Beijing</option>
											    <option value="Shanghai">Shanghai</option>
											    <option value="Hanoi">Hanoi</option>
											</select>
										</div>
									</div>
									<br> <br>
									<div class="row">
										<div class="col-lg-12">
											<button type="button" class="btn btn-default"
												id="register_button" value="Register" disabled>Register</button>
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
<script type="text/javascript" src="resources/js/addEvent.js"></script>
</html>