<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.odde.massivemailer.model.Quiz" %>
<%@ page import="com.odde.massivemailer.model.Question" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Question</title>
<!-- Bootstrap Core CSS -->
<link href="resources/lib/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="resources/lib/bootstrap/css/sb-admin.css" rel="stylesheet">

<link href="resources/lib/bootstrap/css/plugins/morris.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="resources/lib/bootstrap/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
</head>
<body>

<%
    Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
    Question question = null;
    question = quiz.getNextQuestion();
    pageContext.setAttribute("question", question);

%>


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
    </div>
    </form>
</div>
</body>
<!-- jQuery -->
<script type="text/javascript"
	src="resources/lib/bootstrap/js/jquery.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="resources/lib/bootstrap/js/bootstrap.min.js"></script>
</html>