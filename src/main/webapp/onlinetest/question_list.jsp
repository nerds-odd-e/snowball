<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.OnlineTest" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.Question" %>
<%@ page import="java.util.*" %>

<%
    List<Question> questions = Question.getAll();
	pageContext.setAttribute("questions", questions);
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
						    <c:forEach items="${questions}" var="question" varStatus="count">
                                <thead>
                                    <th>Question ${count.index+1}</th>
                                </thead>
                                <tbody id="questionTable">
                                    <tr>
                                        <th>Description</th>
                                        <td id="description">${question.getDescription()}</td>
                                    </tr>
                                    <c:forEach items="${question.getOptions()}" var="option" varStatus="status">
                                    <c:set var="optionId">${option.getLongId()}</c:set>
                                        <c:if test="${question.getCorrectOption() == optionId}">
                                            <tr id="option${status.index+1}row" class="bg-success">
                                                <th>Option${status.index+1}</th>
                                                <td id="option${status.index+1}">${option.getDescription()}</td>
                                            </tr>
                                         </c:if>
                                        <c:if test="${question.getCorrectOption() != optionId}">
                                            <tr>
                                                <th>Option${status.index+1}</th>
                                                <td id="option${status.index+1}">${option.getDescription()}</td>
                                            </tr>
                                         </c:if>
                                    </c:forEach>
                                    <tr>
                                        <th>Advice</th>
                                        <td id="advice" style="border:1px solid #ddd">${question.getAdvice()}</td>
                                    </tr>
                                </tbody>
                            </c:forEach>
                            </table>
						</div>
					</div>

			<div class="modal fade" id="editContactModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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




