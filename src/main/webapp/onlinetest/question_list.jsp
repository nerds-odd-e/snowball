<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page import="com.odde.massivemailer.model.onlinetest.OnlineTest" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.Question" %>
<%
    OnlineTest onlineTest = (OnlineTest) request.getSession().getAttribute("onlineTest");
    Question question = null;
    question = onlineTest.getCurrentQuestion();
    pageContext.setAttribute("question", question);
%>
<t:with_side_menu title="Question List">
    <jsp:body>
			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Question List</h1>
					</div>
				</div>
				<!-- /.row -->

				<input type="hidden" id="msg_sent_cnt" value="${param.repcnt}" />
					<div class="col-lg-12" id="questionTables">
						<div class="panel panel-default">
                            <table class="table table-responsive table-bordered">
                                <thead>
                                    <th>Question 1</th>
                                </thead>
                                <tbody id="questionTable">
                                    <tr>
                                        <th>Description</th>
                                        <td id="description">${question.getDescription()}</td>
                                    </tr>
                            <c:forEach items="${question.getOptions()}" var="option" varStatus="status">
                                <c:if test="${status.index}= '1'">
                                    <tr id="option2row" class="bg-success">
                                        <th>Option2</th>
                                        <td id="option2">${option.getLongId()}</td>
                                    </tr>
                                 </c:if>
                                <c:if test="${status.index}= '0'">
                                    <tr>
                                        <th>Option1</th>
                                        <td id="option1">${option.getLongId()}</td>
                                    </tr>
                                 </c:if>
                            </c:forEach>
                                    <tr>
                                        <th>Advice</th>
                                        <td id="advice">you should read a math book</td>
                                    </tr>
                                </tbody>
                            </table>
						</div>
					</div>

			<div class="modal fade" id="editContactModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Edit Contact</h4>
						</div>


					</div>
				</div>
			</div>
		</div>
    </jsp:body>
</t:with_side_menu>




