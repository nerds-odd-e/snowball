<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.OnlineTest" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.Question" %>

<%
    OnlineTest onlineTest = (OnlineTest) request.getSession().getAttribute("onlineTest");
    String alertMsg = (String) request.getSession().getAttribute("alertMsg");
    Question question = null;
    question = onlineTest.getCurrentQuestion();
    pageContext.setAttribute("alertMsg", alertMsg);
    pageContext.setAttribute("question", question);
%>

<t:basic title="Question">
    <jsp:attribute name="extra_head">
        <link href="/resources/question.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>
        <div id="page-wrapper">
            <form name="question" id="questionForm" method="post"
                action="question">
                    <input type="hidden" id="lastDoneQuestionId" name="lastDoneQuestionId" value="${onlineTest.getNumberOfAnsweredQuestions()}">
            <div class="container-fluid">
                <p class="alertMsg">${alertMsg}</p>
                <h1>Question</h1>
                 <c:if test="${not empty question }" >
                    <h2 id="description">${question.getDescription()}</h2>
                  </c:if>
                <ul>

                <c:forEach items="${question.getOptions()}" var="option" varStatus="status">
                    <li>
                        <c:if test="${question.isMultiQuestion()}">
                         <input type="checkbox" id="option${status.index + 1}" name="optionId" value="${option.getStringId()}" />${option.getDescription()}</label>
                        </c:if>
                        <c:if test="${!question.isMultiQuestion()}">
                         <input type="radio" id="option${status.index + 1}" name="optionId" value="${option.getStringId()}" />${option.getDescription()}</label>
                        </c:if>
                    </li>
                </c:forEach>
                </ul>
                <div class="col-lg-12">
                    <input type="submit" id="answer" value="Answer">
                </div>
                <div style="float:right;" id="progress">
                    <span id="currentQuestionIndex">${onlineTest.getCurrentQuestionIndex()}</span>/${onlineTest.getNumberOfQuestions()}
                </div>
            </div>
            </form>
        </div>
    </jsp:body>
</t:basic>
