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
		    <!-- Modal -->
            <div class="modal fade" id="saveStatusModal" tabindex="-1" role="dialog" aria-labelledby="saveStatusModal">
              <div class="modal-dialog" role="document">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="saveStatusLabel"></h4>
                  </div>
                  <div class="modal-body" id="saveStatusBody">

                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                  </div>
                </div>
              </div>
            </div>
			<form name="addParticipant" id="addParticipant" method="post">

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
									    <div class="col-lg-12">
                                            <div class="col-lg-1">Course Name:</div>
                                            <div class="col-lg-11">
                                                    <select id="courses" class="form-control" name="courseId">
                                                    <option>Select the course</option>
                                                </select>
                                              <input type="hidden" id="courseId" name="courseId" value='<%=request.getParameter("courseId")%>'/>

                                            </div>
										</div>
									</div>
                                    <br> <br>
                                    <div class="row">
                                        <div class="col-lg-1">Participants:</div>
                                        <div class="col-lg-11">
                                              <input type="text" name="email" id="email">
                                              <input type="hidden" name="participantIdHidden" value=""/>
                                              <button type="button" class="btn btn-default" id="add_button" value="Add" disabled>Add</button>
                                        </div>
                                    </div>

									<br> <br>

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
<script type="text/javascript" src="resources/js/enrollParticipant.js"></script>
<script type="text/javascript" src="resources/js/populateLocationDropdown.js"></script>
</html>