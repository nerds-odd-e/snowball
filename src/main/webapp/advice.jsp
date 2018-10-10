<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	String correctOption = (String) request.getAttribute("correctOption");
	String selectedOption = (String) request.getAttribute("selectedOption");
	String[] options = (String[]) request.getAttribute("options");
	final String correctClass = "correct";
	final String incorrectClass = "selected incorrect";
	final String questionText= (String) request.getAttribute("questionText");
	final String adviceText= (String) request.getAttribute("adviceText");
%>

<div id="page-wrapper">
    <div class="container-fluid">
        <h1>Advice</h1>
        <h2 id="questionText"><%= questionText %></h2>
        <ul>
            <%
               for (int i=1; i <= options.length; ++i) {
            %>
            <li>
                <input for="option<%= i %>" type="radio" name="optionId" <%= selectedOption.equals(String.valueOf(i)) ? "checked" : "" %> disabled/>
                <label id="option<%= i %>" class="<%= selectedOption.equals(String.valueOf(i)) ? incorrectClass : "" %> <%= correctOption.equals(String.valueOf(i)) ? correctClass : "" %>" ><%= options[i - 1] %></label>
            </li>
            <%
               }
            %>
        </ul>
        <div id="advice" class="jumbotron"><%= adviceText %></div>
        <form action="question" method="post">
            <input type="submit" id="next" value="Next" class="btn-primary btn"/>
            <input id="from" type="hidden" name="from" value="advice"/>
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