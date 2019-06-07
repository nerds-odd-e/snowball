<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:with_side_menu_and_status title="Add Contact">
    <jsp:attribute name="extra_foot">
        <script type="text/javascript" src="/resources/js/addContact.js"></script>
    </jsp:attribute>
    <jsp:body>

                    <form name="addContact" id="addContact" method="post" action="contacts">
					<div class="row">
						<div class="col-lg-12">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Contact Information</h3>
								</div>
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
										<div class="col-lg-1">Name:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="firstName"
												id="firstName">
										</div>
									</div>
									<br> <br>

									<div class="row">
										<div class="col-lg-1">Last Name:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="lastName"
												id="lastName">
										</div>
									</div>
									<br> <br>

									<div class="row">
										<div class="col-lg-1">Company:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="company"
												id="company">
										</div>
									</div>
									<br> <br>

									<div class="row">
										<div class="col-lg-1">Country:</div>
										<div class="col-lg-11">
											<select id="countrydrp" class="form-control" name="country">
                                            </select>
										</div>
									</div>
									<br> <br>
									<div class="row">
										<div class="col-lg-1">City:</div>
										<div class="col-lg-11">
											<input id="city" class="form-control" name="city" />
										</div>
									</div>
									<br> <br>
									<div class="row">
										<div class="col-lg-12">
											<button type="button" class="btn btn-default"
												id="add_button" value="add" disabled>Add</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
                    </form>
    </jsp:body>
</t:with_side_menu_and_status>
