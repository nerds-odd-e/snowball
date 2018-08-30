
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Question List</title>
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
                                        <th>Description</th>
                                        <th>Option1</th>
                                        <th>Option2</th>
                                        <th>Advice</th>
                                    </tr>
                                </thead>
                                <tbody id="questionTable">
                                    <tr>
                                        <td id="description">what is 1+1?</td>
                                        <td id="option1">must be 3!</td>
                                        <td id="option2" class="bg-success">of course 2.</td>
                                        <td id="advice">you should read a math book</td>
                                    </tr>
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


