
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contact List</title>
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
	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-ex1-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.html">Massive Mailer</a>
		</div>
		<!-- Top Menu Items -->
		<ul class="nav navbar-right top-nav">
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown"><i class="fa fa-envelope"></i> Terry <b
					class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="#"><i class="fa fa-fw fa-user"></i> Profile</a></li>
					<li><a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a></li>
					<li><a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a></li>
					<li class="divider"></li>
					<li><a href="#"><i class="fa fa-fw fa-power-off"></i> Log
							Out</a></li>
				</ul></li>
		</ul>
		<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
		<div class="collapse navbar-collapse navbar-ex1-collapse">
			<ul class="nav navbar-nav side-nav">
				<li><a href="sendemail.jsp"><span
						class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
						Send Mail</a></li>
				<li><a href="add_contact.jsp"><span
						class="glyphicon glyphicon-user" aria-hidden="true"></span> Add
						Contact</a></li>
				<li class="active"><a href="contactlist.jsp"><span
						class="glyphicon glyphicon-user" aria-hidden="true"></span>
						Contacts List</a></li>
                <li><a href="game_create.jsp"><span
                        class="glyphicon glyphicon-user" aria-hidden="true"></span>
                        Emerson's Game</a></li>
                <li><a href="email_tracking.jsp"><span
                        class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                        Email Tracking</a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse --> </nav>

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
							<div class="panel-heading">
								<ul class="list-inline">
									<li class="col-md-3" style="text-align: left">Email</li>
									<li class="col-md-2" style="text-align: left">Name</li>
									<li class="col-md-3" style="text-align: left">Last Name</li>
									<li class="col-md-3" style="text-align: left">Company</li>
									<li class="col-md-1" style="text-align: left; padding-bottom: 1%">Edit</li>
								</ul>
							</div>

							<div class="panel-body">

								<ul id="contactTable" class="list-inline">

								</ul>
							</div>
							<div></div>
						</div>
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
<script type="text/javascript">
$(document).ready(function() {
	var msg = getParameterByName('msg');
	if(msg) {
		alert(msg);
	}
	var contactList = retrieveContactListFromServer();
	renderContactList(contactList, $('#contactTable'));
	
	$("#save_button").click(function() {
		submitEditContact();
	});
});
</script>
</html>

