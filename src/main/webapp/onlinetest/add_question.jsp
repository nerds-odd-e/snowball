<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.odde.massivemailer.model.onlinetest.Category" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.Category" %>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    pageContext.setAttribute("errorMessage", errorMessage);
    pageContext.setAttribute("categories", Category.findAll());
%>



<t:with_side_menu_and_status title="Add Question">

    <jsp:attribute name="extra_foot">
        <script type="text/javascript" src="/resources/js/addQuestion.js"></script>
    </jsp:attribute>

    <jsp:body>

			<form name="addQuestion" id="addQuestion" method="post"
				action="add_question">

					<div class="row">
						<div class="col-lg-12">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Question Information</h3>
								</div>
								<div class="panel-body">
								<c:if test="${not empty errorMessage}">
                                    <div id="message" class="alert alert-danger" role="alert">
                                        ${errorMessage}
                                    </div>
                                </c:if>
                                    <div class="row">
                                        <div class="col-lg-1">Type:</div>
                                        <div class="col-lg-11">
                                        	<select name="type" id="typeList" onchange="switchOptionView(this.value)">
                                        	    <option value="single" id="single">Single Choice</option>
                                        	    <option value="multiple" id="multiple">Multiple Choice</option>
                                        	</select>
                                        </div>
                                    </div>
                                    <br> <br>

                                    <div class="row">
                                        <div class="col-lg-1">Category:</div>
                                        <div class="col-lg-11">
                                        	<select name="category" id="categoryList">
                                        	    <c:forEach items="${categories}" var="category" varStatus="Status">
                                        	        <option value="${category.getId()}">${category.getName()}</option>
                                        	    </c:forEach>
                                        	</select>
                                        </div>
                                    </div>
                                    <br> <br>

									<div class="row">
										<div class="col-lg-1">Description:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="description"
												id="description" maxlength="200">
										</div>
									</div>
									<br> <br>

									<div class="row" id="options">
										<div class="col-lg-1">Options:</div>
										<div class="col-lg-11">

										<c:forEach var="i" begin="1" end="6" step="1">
                                            <div class="input-group">
                                                <span class="input-group-addon">
                                                <input type="radio" id="option${i}" name="check" value="${i}" aria-label="Radio button for following text input">
                                                </span>
                                                <input type="text" class="form-control" name="option${i}" aria-label="Text input with radio button" width="100%" maxlength="100">
                                              </div>
                                          <br>
                                        </c:forEach>
                                        </div>
									    <br> <br>
									</div>


									<div class="row" id="checkboxes">
										<div class="col-lg-1">Options:</div>
										<div class="col-lg-11">

										<c:forEach var="i" begin="1" end="6" step="1">
                                            <div class="input-group">
                                                <span class="input-group-addon">
                                                <input type="checkbox" id="checkbox${i}" name="check" value="${i}" aria-label="Checkbox for following text input">
                                                </span>
                                                <input type="text" class="form-control" name="checkbox${i}" aria-label="Text input with checkbox" width="100%" maxlength="100">
                                              </div>
                                          <br>
                                        </c:forEach>
                                        </div>
									    <br> <br>
									</div>


									<div class="row">
										<div class="col-lg-1">Advice:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="advice"
												id="advice" maxlength="500">
										</div>
									</div>
									<br> <br>
									<div class="row">
										<div class="col-lg-1">Public:</div>
										<div class="col-lg-11">
											<input type="checkbox" name="is-public"
												id="is-public" value="1">
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
