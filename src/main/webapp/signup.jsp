<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.OnlineTest" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.Question" %>
<%@ page import="java.util.*" %>

<t:with_side_menu title="Sign Up">
    <jsp:body>
<div id="page-wrapper">
			<form name="login" id="loginForm" method="post"
				action="login">

				<div class="container-fluid">

					<div class="row">
						<div class="col-lg-12">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Login</h3>
								</div>

								<div class="panel-body">

									<div class="row">
										<div class="col-lg-1">ID:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="id"
												id="id">
										</div>
									</div>
								    <div class="row">
                                        <div class="col-lg-1">email:</div>
                                        <div class="col-lg-11">
                                    		<input type="text" class="form-control" name="email" id="email">
                                    	</div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-1">email:</div>
                                        <div class="col-lg-11">
                                    		<input type="text" class="form-control" name="password" id="password">
                                    	</div>
                                    </div>   <div class="row">
                                        <div class="col-lg-1">email:</div>
                                        <div class="col-lg-11">
                                    		<input type="text" class="form-control" name="password_confirm" id="password_confirm">
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
</t:with_side_menu>
