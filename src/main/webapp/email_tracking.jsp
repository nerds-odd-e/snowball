<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<%@ page import="com.odde.massivemailer.model.ContactPerson"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<title>ODD-E</title>

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
<style>
#selectContactTable li {
	height: 25px;
	overflow: hidden;
	text-overflow: ellipsis;
}
</style>
</head>
<body>
	<form name="sendmail" id="sendmail" method="post" action="sendMail">
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
							<li><a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
							</li>
							<li><a href="#"><i class="fa fa-fw fa-envelope"></i>
									Inbox</a></li>
							<li><a href="#"><i class="fa fa-fw fa-gear"></i>
									Settings</a></li>
							<li class="divider"></li>
							<li><a href="#"><i class="fa fa-fw fa-power-off"></i>
									Log Out</a></li>
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
						<li><a href="contactlist.jsp"><span
								class="glyphicon glyphicon-user" aria-hidden="true"></span>
								Contact List</a></li>
                        <li><a href="game_create.jsp"><span
                                class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                Emerson's Game</a></li>
                        <li class="active"><a href="email_tracking.jsp"><span
                                class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                                Email Tracking</a></li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</nav>

			<div id="page-wrapper">

				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Email Tracking</h1>
						</div>
					</div>
					<!-- /.row -->

                    <!-- table -->

                    <div class="row">
                        <div class="col-lg-12">
                            <table id="trackingTable" class="table">
                                <thead>
                                    <tr>
                                      <th>Subject</th>
                                      <th>Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
				</div>
			</div>
		</div>
	</form>
</body>

<!-- jQuery -->
<script type="text/javascript" src="resources/lib/bootstrap/js/jquery.js"></script>
<script type="text/javascript" src="resources/lib/jquery-template/jquery.loadTemplate-1.4.4.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="resources/lib/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/js/showTrackedEmail.js"></script>
<script type="text/javascript">
$(document).ready(function() {

	var trackingList = retrieveTrackingEmailListFromServer();

	renderTrackingEmailList(trackingList, $('#trackingTable'));
});
</script>
</html>