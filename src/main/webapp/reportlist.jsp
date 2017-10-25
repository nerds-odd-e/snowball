<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Report List</title>
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
					<h1 class="page-header">Report List</h1>
				</div>
			</div>
			<div id="div12">
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h3>History of sending mail</h3>
                </div>
				<div class="col-lg-12">
					<div class="panel panel-default">
                         <table class="table table-responsive table-striped">
                             <thead>
                                 <tr>
                                    <th>Email Address</th>
                                    <th>User Location</th>
                                    <th>Sent At</th>
                                    <th>Course Name</th>
                                </tr>
                            </thead>
                            <tbody id="courseTable">
                            </tbody>
                        </table>
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
<script type="text/javascript" src="resources/js/showReports.js"></script>
</html>