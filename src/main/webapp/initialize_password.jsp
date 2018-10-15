<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	pageContext.setAttribute("request", request);
%>
<c:set var="error_message">
    <%if("error".equals(request.getParameter("error"))){%>
        <div class="alert alert-danger">
            <p>Error!</p>
        </div>
    <%}%>
</c:set>

<t:basic title="Login">
    <jsp:attribute name="extra_foot">
        <script type="text/javascript" src="/resources/js/addContact.js"></script>
    </jsp:attribute>
    <jsp:body>
		<div id="page-wrapper">

			<form name="Initialize-Password" id="initialize-password" method="post"
				action="initialPassword">

				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Initialize Password for Massive Mailer</h1>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-12">
						    ${error_message}
							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Initialize Password</h3>
								</div>
								<div class="panel-body">

                                    <input type="hidden" class="form-control" name="token" id="token" value="${request.getParameter("token")}" >
									<div class="row">
										<div class="col-lg-1">Password:</div>
										<div class="col-lg-11">
											<input type="password" class="form-control" name="password"
												id="password">
										</div>
									</div>
									<br> <br>

									<div class="row">
										<div class="col-lg-1">Password confirm:</div>
										<div class="col-lg-11">
											<input type="password" class="form-control" name="password_confirm"
												id="password_confirm">
										</div>
									</div>
									<br> <br>

									<div class="row">
										<div class="col-lg-12">
											<button type="submit" class="btn btn-default"
												id="submit">Login</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

			</form>
		</div>
    </jsp:body>
</t:basic>
