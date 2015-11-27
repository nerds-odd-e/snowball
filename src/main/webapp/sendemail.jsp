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
						<li class="active"><a href="sendemail.jsp"><span
								class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
								Send Mail</a></li>
						<li><a href="add_contact.jsp"><span
								class="glyphicon glyphicon-user" aria-hidden="true"></span> Add
								Contact</a></li>
						<li><a href="contactlist.jsp"><span
								class="glyphicon glyphicon-user" aria-hidden="true"></span>
								Contact List</a></li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</nav>

			<div id="page-wrapper">

				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Send Mail</h1>
						</div>
					</div>
					<!-- /.row -->
					<%
						if (request.getParameter("status") != null) {
							out.println(
									"<div class='row'><div class='col-lg-12'><div class='alert alert-info alert-dismissable'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><i class='fa fa-info-circle'></i>&nbsp;<b>"
											+ request.getParameter("status") + " : " + request.getParameter("msg")
											+ "</b></div> </div></div>");
						}
					%>
					<input type="hidden" id="msg_sent_cnt" value="${param.repcnt}" />
					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Send Email</h3>
								</div>
								<div class="panel-body">
									<div class="row">
										<div class="col-lg-1">Template:</div>
										<div class="col-lg-11">
											<select name="templateList" id="templateList"></select>
											
										</div>
									</div>

									<br />
									<div class="row">
										<div class="col-lg-1">To:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="recipient"
												id="recipient"> 
											<span id="select_contact" data-toggle="modal" data-target="#selectContactModal"
											class="glyphicon glyphicon-plus add-contact-button"
											aria-hidden="true"></span>
											
										</div>
									</div>
									<br />
									<div class="row">
										<div class="col-lg-1">Subject:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="subject"
												id="subject">
										</div>
									</div>
									<br />
									<div class="row">
										<div class="col-lg-12">
											<textarea class="form-control" rows="20" name="content"
												id="content"></textarea>
										</div>
									</div>
									<br />
									<div class="row">
										<div class="col-lg-12">
											<button type="button" class="btn btn-default"
												id="send_button" value="send" disabled="disabled">Send</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="select_contact.jsp" %>
	</form>
</body>

<!-- jQuery -->
<script type="text/javascript" src="resources/lib/bootstrap/js/jquery.js"></script>
<script type="text/javascript" src="resources/lib/jquery-template/jquery.loadTemplate-1.4.4.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="resources/lib/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/js/sendemail.js"></script>
<script type="text/javascript" src="resources/js/showContact.js"></script>
<script type="text/javascript" src="resources/js/selectContact.js"></script>
<script type="text/javascript" src="resources/js/showTemplate.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$('#select_contact').click(function () {
			var contactList = retrieveContactListFromServer();
			selectedContact = '';
			renderContactSelectionList(contactList, $('#selectContactTable'));
		});

		$('#add_contact_button').click(function () {
			whenAddButtonIsClicked();
		});

		var templateList = retrieveTemplateListFromServer();
		renderTemplateList(templateList, $('#templateList'));



	});
</script>
</html>