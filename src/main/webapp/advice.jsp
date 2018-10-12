<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.odde.massivemailer.model.Quiz" %>
<%@ page import="com.odde.massivemailer.model.Question" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Advice</title>
<!-- Bootstrap Core CSS -->
<link href="resources/lib/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="resources/lib/bootstrap/css/sb-admin.css" rel="stylesheet">

<link href="resources/lib/bootstrap/css/plugins/morris.css"
	rel="stylesheet">

<link href="resources/question.css" rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="resources/lib/bootstrap/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
</head>
<body>

<%
    Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
    Question question = quiz.getCurrentQuestion();
	Long correctOption = Long.parseLong((String)quiz.getCurrentQuestion().getCorrectOption());
	Long selectedOption = Long.parseLong((String)request.getAttribute("selectedOption"));
	final String correctClass = "correct";
	final String incorrectClass = "selected incorrect";
	final String questionText = quiz.getCurrentQuestion().getDescription();
	final String adviceText = quiz.getCurrentQuestion().getAdvice();
	pageContext.setAttribute("question", question);
	pageContext.setAttribute("selectedOption", selectedOption);
	pageContext.setAttribute("correctOption", correctOption);



%>

<div id="page-wrapper">
    <div class="container-fluid">
        <h1>Advice</h1>
        <h2 id="questionText"><%= questionText %></h2>
        <ul>

       <c:forEach items="${question.getOptions()}" var="option">
            <li>
                 <c:set var="optionId">${option.getLongId()}</c:set>
                 <c:choose>
                    <c:when test="${selectedOption == optionId}">
                       <input type="radio" name="optionId" value="${option.getLongId()}" checked disabled/><label class="selected incorrect"/>${option.getDescription()}</label>
                    </c:when>
                    <c:when test="${correctOption == optionId}">
                        <input type="radio" name="optionId" value="${option.getLongId()}" disabled/><label class="correct"/>${option.getDescription()}</label>
                    </c:when>

                    <c:otherwise >
                        <input type="radio" name="optionId" value="${option.getLongId()}" disabled/><label> ${option.getDescription()}</label>
                    </c:otherwise>
                 </c:choose>
            </li>
       </c:forEach>

        </ul>
        <div id="advice" class="jumbotron"><%= adviceText %></div>
        <form action="advice" method="post">
            <input type="submit" id="next" value="Next" class="btn-primary btn"/>
        </form>
    </div>
</div>
</body>
<!-- jQuery -->
<script type="text/javascript"
	src="resources/lib/bootstrap/js/jquery.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="resources/lib/bootstrap/js/bootstrap.min.js"></script>
</html>