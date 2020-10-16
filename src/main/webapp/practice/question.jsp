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
                action="/practice/answer">
                    <input type="hidden" id="currentQuestionId" name="currentQuestionId" value="${currentQuestion.stringId()}">
            <div class="container-fluid">
                <p class="alertMsg">${alertMsg}</p>
                <h1>Question</h1>
                <h2 id="description">${currentQuestion.getDescription()}</h2>
                <ul>

                <c:forEach items="${currentQuestion.getOptions()}" var="option" varStatus="status">
                    <li>
                        <c:if test="${currentQuestion.isMultiQuestion()}">
                         <input type="checkbox" id="option${status.index + 1}" name="optionId" value="${option.stringId()}" />${option.getDescription()}</label>
                        </c:if>
                        <c:if test="${!currentQuestion.isMultiQuestion()}">
                         <input type="radio" id="option${status.index + 1}" name="optionId" value="${option.stringId()}" />${option.getDescription()}</label>
                        </c:if>
                    </li>
                </c:forEach>
                </ul>
                <div class="col-lg-12">
                    <input type="submit" id="answer" value="Answer">
                </div>
                <div style="float:right;" id="progress">
                    <span id="currentQuestionIndex">${progress}</span>
                </div>
            </div>
            </form>
        </div>
    </jsp:body>
</t:basic>
