<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:with_side_menu_and_status title="Add Question">
    <jsp:body>

			<form name="addQuestion" id="addQuestion" method="post"
				action="addQuestion">

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
                                            <input type="radio" id="option1" name="check" value="1" aria-label="Radio button for following text input">
                                            </span>
                                            <input type="text" class="form-control" name="option1" aria-label="Text input with radio button" width="100%">
                                          </div>
                                          <br>

                                          <div class="input-group">
                                            <span class="input-group-addon">
                                            <input type="radio" id="option2" name="check" value="2" aria-label="Radio button for following text input">
                                            </span>
                                            <input type="text" class="form-control" name="option2" aria-label="Text input with radio button" width="100%">
                                          </div>
                                          <br>

                                          <div class="input-group">
                                            <span class="input-group-addon">
                                            <input type="radio" id="option3" name="check" value="3" aria-label="Radio button for following text input">
                                            </span>
                                            <input type="text" class="form-control" name="option3" aria-label="Text input with radio button" width="100%">
                                          </div>
                                          <br>

                                          <div class="input-group">
                                            <span class="input-group-addon">
                                            <input type="radio" id="option4" name="check" value="4" aria-label="Radio button for following text input">
                                            </span>
                                            <input type="text" class="form-control" name="option4" aria-label="Text input with radio button" width="100%">
                                          </div>
                                          <br>

                                          <div class="input-group">
                                            <span class="input-group-addon">
                                            <input type="radio" id="option5" name="check" value="5" aria-label="Radio button for following text input">
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
			</form>
    </jsp:body>
</t:with_side_menu_and_status>
