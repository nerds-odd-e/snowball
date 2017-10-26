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
			<form name="addEvent" id="addEvent" method="post"
				action="courses">

				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Create Course</h1>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-12">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Course Information</h3>
								</div>
								<div class="panel-body">

									<div class="row">
									    <div class="col-lg-12">
                                            <div class="col-lg-1">Name:</div>
                                            <div class="col-lg-11">
                                                <input type="text" class="form-control" name="coursename" id="coursename">
                                            </div>
										</div>
									</div>
									<br> <br>
									<div class="row">
                                        <div class="col-lg-6">
                                            <div class="row">
                                                <div class="col-lg-2">Duration:</div>
                                                <div class="col-lg-10">
                                                    <input type="number" min="1" placeholder="number of days" class="form-control" name="duration" id="courseDuration">
                                                </div>
                                            </div>
                                            <br> <br>
                                            <div class="row">
                                                <div class="col-lg-2">Start Date:</div>
                                                <div class="col-lg-10">
                                                    <input type="date" class="form-control" name="startdate" id="courseStartDate">
                                                 </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-6">
                                            <div class="col-lg-2">Address:</div>
                                            <div class="col-lg-10">
                                                <textarea name="address" id="address" style="width:100%; height:89px;"></textarea>
                                             </div>
                                        </div>

                                        <br> <br>

                                    </div>
                                    <br> <br>
									<div class="row">
										<div class="col-lg-1">Course Details:</div>
										<div class="col-lg-11">
										    <textarea name="coursedetails" id="courseDetails" style="width:100%; height:89px;"></textarea>
										</div>
									</div>
									<br> <br>
                                    <div class="row">
                                    	<div class="col-lg-1">Country:</div>
                                    	<div class="col-lg-11">
                                    	    <select id="countrydrp" class="form-control" name="country">
                                    	        <option value="China">China</option>
                                    	    </select>
                                        </div>
                                    </div>
									<br> <br>
                                    <div class="row">
                                    	<div class="col-lg-1">City:</div>
                                    	<div class="col-lg-11">
                                    		    <input type="text" id="city" class="form-control" name="city">
                                    	    </select>
                                        </div>
                                    </div>
									<br> <br>
									<div class="row">
										<div class="col-lg-1">Instructor:</div>
										<div class="col-lg-11">
											  <input type="text" class="form-control" name="instructor" id="instructor">
										</div>
									</div>
									<br> <br>
									<div class="row">
										<div class="col-lg-12">
											<button type="button" class="btn btn-default"
												id="save_button" value="Save" disabled>Save</button>
										</div>
									</div>
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
<script type="text/javascript" src="resources/js/addEvent.js"></script>
<script type="text/javascript" src="resources/js/populateLocationDropdown.js"></script>
</html>