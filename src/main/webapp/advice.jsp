<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
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
	final String correctClass = "correct";
	final String incorrectClass = "selected incorrect";
%>

<div id="page-wrapper">
    <div class="container-fluid">
        <h1>Advice</h1>
        <h2 id="description">What is scrum?</h2>
        <ul>
            <li>

                <input for="option1" type="radio" name="optionId" <%= selectedOption.equals("1") ? "checked" : "" %> disabled/>
                <label id="option1" class="<%= selectedOption.equals("1") ? incorrectClass : "" %>  <%= correctOption.equals("1") ? correctClass : "" %> ">Scrum is Rugby</label>
            </li>
            <li>
                <input for="option2" type="radio" name="optionId" <%= selectedOption.equals("2") ? "checked" : "" %> disabled/>
                <label id="option2" class="<%= selectedOption.equals("2") ? incorrectClass : "" %> <%= correctOption.equals("2") ? correctClass : "" %>">Scrum is Baseball</label>
            </li>
            <li>
                <input for="option3" type="radio" name="optionId" <%= selectedOption.equals("3") ? "checked" : "" %> disabled/>
                <label id="option3" class="<%= selectedOption.equals("3") ? incorrectClass : "" %> <%= correctOption.equals("3") ? correctClass : "" %>">Scrum is Soccer</label>
            </li>
            <li>
                <input for="option4" type="radio" name="optionId" <%= selectedOption.equals("4") ? "checked" : "" %> disabled/>
                <label id="option4" class="<%= selectedOption.equals("4") ? incorrectClass : "" %> <%= correctOption.equals("4") ? correctClass : "" %>">Scrum is Sumo</label>
            </li>
            <li>
                <input for="option5" type="radio" name="optionId" <%= selectedOption.equals("5") ? "checked" : "" %> disabled/>
                <label id="option5" class="<%= selectedOption.equals("5") ? incorrectClass : "" %> <%= correctOption.equals("5") ? correctClass : "" %>" >None of the above</label>
            </li>
        </ul>
        <div id="advice" class="jumbotron">Scrum is a framework for agile development.
        </div>
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