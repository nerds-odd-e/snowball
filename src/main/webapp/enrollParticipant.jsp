<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
	pageContext.setAttribute("request", request);
%>
<t:with_side_menu title="Enroll Participant">
    <jsp:attribute name="extra_foot">
        <script type="text/javascript" src="resources/js/enrollParticipant.js"></script>
    </jsp:attribute>
    <jsp:body>
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
                                              <input type="hidden" id="courseId" name="courseId" value='${request.getParameter("courseId")}'/>

                                            </div>
										</div>
									</div>
                                    <br> <br>
                                    <div class="row">
                                        <div class="col-lg-1">Participants:</div>
                                        <div class="col-lg-11">
                                              <input type="text" name="email" id="email">
                                              <input type="hidden" name="participantIdHidden" value=""/>
                                              <button type="button" name="add_button" class="btn btn-default" id="add_button" value="Add" disabled>Add</button>
                                        </div>
                                    </div>

									<br> <br>

								</div>
							</div>
						</div>
					</div>
				</div>

			</form>
    </jsp:body>
</t:with_side_menu>
