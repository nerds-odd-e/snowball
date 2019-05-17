<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="java.util.*" %>

<t:basic title="Sign Up">
    <jsp:body>
<div id="page-wrapper">
			<form name="signup" id="signupForm" method="post" action="signup">

				<div class="container-fluid">

					<div class="row">
						<div class="col-lg-12">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Sign up</h3>
								</div>
								<c:if test="${not empty param.status}" >
                                    <div class="alert alert-danger">
                                       Signup failed
                                	</div>
                                </c:if>

								<div class="panel-body">

									<div class="row">
										<div class="col-lg-1">UserName:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="userName"
												id="userName">
										</div>
									</div>
								    <div class="row">
                                        <div class="col-lg-1">Email:</div>
                                        <div class="col-lg-11">
                                    		<input type="email" class="form-control" name="email" id="email">
                                    	</div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-1">Password:</div>
                                        <div class="col-lg-11">
                                    		<input type="password" class="form-control" name="password" id="password">
                                    	</div>
                                    </div>   <div class="row">
                                        <div class="col-lg-1">Password confirm:</div>
                                        <div class="col-lg-11">
                                    		<input type="password" class="form-control" name="password_confirm" id="password_confirm">
                                    	</div>
                                    </div>
                                    <br> <br>

                                    <div class="row">
                                        <div class="col-lg-12">
                                        <input type="submit" id="signup" value="signup">
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
