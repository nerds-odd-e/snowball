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

<t:basic title="Question">
    <jsp:body>
        <div id="page-wrapper">
            <form name="question" id="questionForm" method="post"
                action="question">
                    <input type="hidden" id="numberOfAnsweredQuestions" name="numberOfAnsweredQuestions" value="${onlineTest.getNumberOfAnsweredQuestions()}">
            <div class="container-fluid">
                <h1>Question</h1>
                 <c:if test="${not empty question }" >
                    <h2 id="description">${question.getDescription()}</h2>
                  </c:if>
                <ul>
                <c:forEach items="${question.getOptions()}" var="option">
                    <li>
                        <input type="radio" name="optionId" value="${option.getLongId()}" checked/>${option.getDescription()}</label>
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
