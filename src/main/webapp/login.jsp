<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:basic title="Login">
    <jsp:attribute name="extra_foot">
        <script type="text/javascript" src="/resources/js/addContact.js"></script>
    </jsp:attribute>
    <jsp:body>
		<div id="page-wrapper">
			<form name="login" id="loginForm" method="post"
				action="login">

				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Login Massive Mailer</h1>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-12">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Login</h3>
								</div>
								<c:if test="${not empty param.status}" >
									<div class="alert alert-danger">
										login failed
									</div>
								</c:if>
								<div class="panel-body">

									<div class="row">
										<div class="col-lg-1">Email:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="email"
												id="email">
										</div>
									</div>
									<br> <br>

									<div class="row">
										<div class="col-lg-1">Password:</div>
										<div class="col-lg-11">
											<input type="password" class="form-control" name="password"
												id="password">
										</div>
									</div>
									<br> <br>

									<div class="row">
										<div class="col-lg-12">
											<input type="submit" id="login" value="Login">
										</div>
									</div>
									<div class="row">
									    <div class="col-lg-12">
									        <a href="signup.jsp" id="signup">Sign Up</a>
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
