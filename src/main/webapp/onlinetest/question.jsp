<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:basic title="Question">
    <jsp:attribute name="extra_head">
        <link href="/resources/question.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>
        <div id="page-wrapper">
            <form name="question" id="questionForm" method="post"
                action="/onlinetest/answer">
                    <input type="hidden" id="currentQuestionId" name="currentQuestionId" value="${requestScope.currentQuestion.getStringId()}">
            <div class="container-fluid">
                <p class="alertMsg">${requestScope.alertMsg}</p>
                <h1>Question</h1>
                <h2 id="description">${requestScope.currentQuestion.getDescription()}</h2>
                <ul>

                <c:forEach items="${requestScope.currentQuestion.options()}" var="option" varStatus="status">
                    <li>
                        <c:if test="${requestScope.currentQuestion.isMultiQuestion()}">
                         <input type="checkbox" id="option${status.index + 1}" name="optionId" value="${option.getStringId()}" />${option.getDescription()}</label>
                        </c:if>
                        <c:if test="${!requestScope.currentQuestion.isMultiQuestion()}">
                         <input type="radio" id="option${status.index + 1}" name="optionId" value="${option.getStringId()}" />${option.getDescription()}</label>
                        </c:if>
                    </li>
                </c:forEach>
                </ul>
                <div class="col-lg-12">
                    <input type="submit" id="answer" value="Answer">
                </div>
                <div style="float:right;" id="progress">
                    <span id="currentQuestionIndex">${requestScope.progress}</span>
                </div>
            </div>
            </form>
        </div>
    </jsp:body>
</t:basic>
