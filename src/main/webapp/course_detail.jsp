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
						<h1 class="page-header">Course Detail <span id="courseName"></span></h1>
					</div>
				</div>
				<!-- /.row -->

			<form action="enroll_participants" name="addParticipant" id="addParticipant" method="post">
				<div class="container-fluid">
					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Enroll Participant</h1>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-12">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Course Enrollment Details</h3>
								</div>
								<div class="panel-body">
                                    <div class="row">
                                        <div class="col-lg-1">Participants:</div>
                                        <div class="col-lg-11">
                                              <textarea name="participants" id="participants" rows="5" cols="80">${param.errors}</textarea>
                                              <input type="hidden" name="participantIdHidden" value=""/>
                                              <input type="hidden" id="courseId" name="courseId" value='<%=request.getParameter("id")%>'/>
                                              <button type="button" name="add_button" class="btn btn-default" id="add_button" value="Add">Add</button>
                                        </div>
                                    </div>

									<br> <br>

								</div>
							</div>
						</div>
					</div>
				</div>
			</form>

				<input type="hidden" id="courseId" value="${param.repcnt}" />
				<div id="div12">

				</div>
				<div class="row">
					<div class="col-lg-12">
						<h3>Send Upcoming Course Notification</h3>
                        <form action="sendAllCourses" method="post">
                            <input type="submit" id="send_button" value="Send" />
                            <div id="message" style=""><%= request.getParameter("message") == null? "" : request.getParameter("message") %></div>
                        </form>
                    </div>
					<div class="col-lg-12">
						<div class="panel panel-default">
                            <table class="table table-responsive table-striped">
                                <thead>
                                    <tr>
                                        <th>Email</th>
                                        <th>Participant Name</th>
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
<script type="text/javascript" src="resources/js/showCourseDetail.js"></script>
<script type="text/javascript" src="resources/js/showParticipants.js"></script>
<script type="text/javascript">
$(document).ready(function() {

	var courseDetailData = retrieveCourseDetailFromServer();

	renderParticipantRows(courseDetailData, $('#courseTable'));
});
</script>
</html>