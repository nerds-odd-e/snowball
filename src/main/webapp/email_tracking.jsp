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
<style>
#selectContactTable li {
	height: 25px;
	overflow: hidden;
	text-overflow: ellipsis;
}

tr:hover {
   background: #efefef;
   cursor: pointer;
}
td a {
   display: block;
   border: 1px solid black;
   padding: 16px;
}
</style>
</head>
<body>
	<form name="sendmail" id="sendmail" method="post" action="sendMail">
            <jsp:include page="ui_common.jsp" />
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

	renderTrackingEmailList(trackingList, $('#trackingTable tbody'));

	$(".clickable-row").click(function() {
            window.document.location = $(this).data("href");
     });
});
</script>
</html>