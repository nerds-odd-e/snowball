
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

        <jsp:include page="ui_common.jsp" />

		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Event List</h1>
					</div>
				</div>
				<!-- /.row -->

				<input type="hidden" id="msg_sent_cnt" value="${param.repcnt}" />
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<ul class="list-inline">
									<li class="col-md-6" style="text-align: left">Title</li>
									<li class="col-md-6" style="text-align: left">Content</li>
								</ul>
							</div>

							<div class="panel-body">

								<ul id="eventTable" class="list-inline">

								</ul>
							</div>
						</div>
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
<script type="text/javascript" src="resources/js/showEvent.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	var msg = getParameterByName('msg');
	if(msg) {
		alert(msg);
	}
	var eventList = retrieveEventListFromServer();
	renderEventList(eventList, $('#eventTable'));

});
</script>
</html>


