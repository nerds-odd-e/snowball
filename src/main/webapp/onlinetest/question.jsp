<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.Quiz" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.Question" %>

<%
    Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
    Question question = null;
    question = quiz.getNextQuestion();
    pageContext.setAttribute("question", question);
%>

<t:basic title="Question">
    <jsp:body>
        <div id="page-wrapper">
            <form name="question" id="questionForm" method="post"
                action="question">
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
                <div id="progress">
                    "1/3"
                </div>
            </div>
            </form>
        </div>
    </jsp:body>
</t:basic>
