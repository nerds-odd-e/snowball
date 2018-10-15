<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
	pageContext.setAttribute("request", request);
%>
<t:with_side_menu title="Course List">
    <jsp:body>
			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Course List</h1>
					</div>
				</div>
				<!-- /.row -->

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
                                        <th>Course Name</th>
                                        <th>Duration</th>
                                        <th>Location</th>
                                        <th>Start Date</th>
                                        <th>Instructor</th>
                                        <th>Email to Participants</th>
                                        <th>Email to Admin</th>
                                    </tr>
                                </thead>
                                <tbody id="courseTable">
                                </tbody>
                            </table>
						</div>
					</div>
				</div>

			</div>

    </jsp:body>
</t:with_side_menu>

