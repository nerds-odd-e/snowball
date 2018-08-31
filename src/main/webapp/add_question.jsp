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
			<form name="addQuestion" id="addQuestion" method="post"
				action="addQuestion">

				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Add Question</h1>
						</div>
					</div>

                    <%
                        if (request.getParameter("status") != null) {
                            out.println(
                                    "<div class='row'><div class='col-lg-12'><div class='alert alert-info alert-dismissable'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button><i class='fa fa-info-circle'></i>&nbsp;<b><span id='email_result'>"
                                            + request.getParameter("status") + " : " + request.getParameter("msg")
                                            + "</span></b></div> </div></div>");
                        }
                    %>
					<div class="row">
						<div class="col-lg-12">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Question Information</h3>
								</div>
								<div class="panel-body">
                                    <div class="alert alert-danger" role="alert" id="message">
                                        Advice is over length
                                    </div>
									<div class="row">
										<div class="col-lg-1">Description:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="description"
												id="description">
										</div>
									</div>
									<br> <br>

									<div class="row">
										<div class="col-lg-1">Options:</div>
										<div class="col-lg-11">

                                          <div class="input-group">
                                            <span class="input-group-addon">
                                            <input type="radio" id="option1" name="check" value="check1" aria-label="Radio button for following text input">
                                            </span>
                                            <input type="text" class="form-control" name="option1" aria-label="Text input with radio button" width="100%">
                                          </div>
                                          <br>

                                          <div class="input-group">
                                            <span class="input-group-addon">
                                            <input type="radio" id="option2" name="check" value="check2" aria-label="Radio button for following text input">
                                            </span>
                                            <input type="text" class="form-control" name="option2" aria-label="Text input with radio button" width="100%">
                                          </div>
                                          <br>

                                          <div class="input-group">
                                            <span class="input-group-addon">
                                            <input type="radio" id="option3" name="check" value="check3" aria-label="Radio button for following text input">
                                            </span>
                                            <input type="text" class="form-control" name="option3" aria-label="Text input with radio button" width="100%">
                                          </div>
                                          <br>

                                          <div class="input-group">
                                            <span class="input-group-addon">
                                            <input type="radio" id="option4" name="check" value="check4" aria-label="Radio button for following text input">
                                            </span>
                                            <input type="text" class="form-control" name="option4" aria-label="Text input with radio button" width="100%">
                                          </div>
                                          <br>

                                          <div class="input-group">
                                            <span class="input-group-addon">
                                            <input type="radio" id="option5" name="check" value="check5" aria-label="Radio button for following text input">
                                            </span>
                                            <input type="text" class="form-control" name="option5" aria-label="Text input with radio button" width="100%">
                                          </div>
                                        </div>
										<div class="col-lg-11">
										</div>
									</div>
									<br> <br>

									<div class="row">
										<div class="col-lg-1">Advice:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="advice"
												id="advice">
										</div>
									</div>
									<br> <br>
									<div class="row">
										<div class="col-lg-12">
											<button type="submit" class="btn btn-default" name="add_button"
												id="add_button" value="add" >Add</button>
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
</html>