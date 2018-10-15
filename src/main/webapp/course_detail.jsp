<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	pageContext.setAttribute("request", request);
%>
<t:with_side_menu title="Course Detail">
    <jsp:attribute name="extra_foot">
        <script type="text/javascript" src="/resources/js/showCourseDetail.js"></script>
        <script type="text/javascript">
        $(document).ready(function() {
            var courseDetailData = retrieveCourseDetailFromServer();
            renderParticipantRows(courseDetailData, $('#courseTable'));
        });
        </script>
    </jsp:attribute>
    <jsp:body>
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
                                        <c:if test="${not empty param.errors}" >
                                            <div class="alert alert-danger">
                                                Check the following participants.
                                            </div>
                                        </c:if>
                                        <div class="col-lg-1">Participants:</div>
                                        <div class="col-lg-11">
                                            <textarea name="participants" id="participants" rows="5" cols="80">${param.errors}</textarea>
                                            <input type="hidden" name="participantIdHidden" value=""/>
                                            <input type="hidden" id="courseId" name="courseId" value='${request.getParameter("id")}'/>
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
                            <div id="message" style="">${request.getParameter("message") == null? "" : request.getParameter("message")}</div>
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


    </jsp:body>
</t:with_side_menu>

