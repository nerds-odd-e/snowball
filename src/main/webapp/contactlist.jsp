
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contact List</title>
<!-- Bootstrap Core CSS -->
<link href="/resources/lib/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="/resources/lib/bootstrap/css/sb-admin.css" rel="stylesheet">

<link href="/resources/lib/bootstrap/css/plugins/morris.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="/resources/lib/bootstrap/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
</head>
<body>

        <jsp:include page="ui_common.jsp" />

		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Contact List</h1>
					</div>
				</div>
				<!-- /.row -->

				<input type="hidden" id="msg_sent_cnt" value="${param.repcnt}" />
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
                            <table class="table table-responsive table-striped">
                                <thead>
                                    <tr>
                                        <th>Email</th>
                                        <th>Name</th>
                                        <th>Last Name</th>
                                        <th>Company</th>
                                        <th>Location</th>
                                        <th>Consent ID</th>
                                        <th>Edit</th>
                                    </tr>
                                </thead>
                                <tbody id="contactTable">
                                </tbody>
                            </table>
						</div>
					</div>
				</div>

			<div class="modal fade" id="editContactModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Edit Contact</h4>
						</div>

						<form name="editContact" id="editContact" method="post"
							action="editContact">
							<div class="modal-body">
								<div class="row">
									<div class="col-md-2">Email</div>
									<div class="col-md-10">
										<label id="email_label"></label>
										<input type="hidden" class="form-control" name="email" id="email">
									</div>
								</div>
								<br>
								<div class="row">
									<div class="col-md-2">Name</div>
									<div class="col-md-10">
										<input type="text" class="form-control" name="name" id="name">
									</div>
								</div>
								<br>
								<div class="row">
									<div class="col-md-2">Last Name</div>
									<div class="col-md-10">
										<input type="text" class="form-control" name="lastname" id="lastname">
									</div>
								</div>
								<br>
								<div class="row">
									<div class="col-md-2">Company</div>
									<div class="col-md-10">
										<input type="text" class="form-control" name="company" id="company">
									</div>
								</div>
								<br>
								<div class="row">
                                    <div class="col-md-2">Country</div>
                                    <div class="col-md-10">
                                        <select id="countrydrp" class="form-control" name="country">
                                        </select>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-2">City</div>
                                    <div class="col-md-10">
                                        <input type="text" class="form-control" name="city" id="city">
                                    </div>
                                </div>
								<br>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
								<button type="button" class="btn btn-default" id="save_button"
									value="save">Save</button>
							</div>

						</form>

					</div>
				</div>
			</div>
		</div>


	</div>

</body>

<!-- jQuery -->
<script type="text/javascript"
	src="resources/lib/bootstrap/js/jquery.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="resources/lib/bootstrap/js/bootstrap.min.js"></script>
<!-- <script type="text/javascript" src="resources/js/sendemail.js"></script> -->
<script type="text/javascript" src="resources/js/showContact.js"></script>
<script type="text/javascript" src="resources/js/populateCountriesDropdown.js"></script>
</html>


