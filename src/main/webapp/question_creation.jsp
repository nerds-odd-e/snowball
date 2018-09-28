<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.odde.massivemailer.model.Question" %>
<%@ page import="com.odde.massivemailer.model.QuestionOption" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contact List</title>
<!-- Bootstrap Core CSS -->
<link href="/massive_mailer/resources/lib/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="/massive_mailer/resources/lib/bootstrap/css/sb-admin.css" rel="stylesheet">

<link href="/massive_mailer/resources/lib/bootstrap/css/plugins/morris.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="/massive_mailer/resources/lib/bootstrap/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="ui_common.jsp" />
    <div id="page-wrapper">
    <form action="/massive_mailer/question/creation" method="POST">
        <div class="form-group">
            <label for="category">category</label>
            <select name = "category">
                <option value="Scrum">Scrum</option>
                <option value="Technical practice">Technical practice</option>
                <option value="Organization">Organization</option>
                <option value="Scaling">Scaling</option>
            </select>
        </div>
        <div class="form-group">
            <label for="body">question</label>
            <input id="input_question_body" type="text" name="body">
        </div>
        <div class="form-group">
            <label for="answer_1">answer 1</label>
            <input type="text" name="answer_1">
        </div>
        <div class="form-group">
            <label for="answer_2">answer 2</label>
            <input type="text" name="answer_2">
        </div>
        <div class="form-group">
            <label for="answer_3">answer 3</label>
            <input type="text" name="answer_3">
        </div>
        <div class="form-group">
            <label for="answer_4">answer 4</label>
            <input type="text" name="answer_4">
        </div>
        <div class="form-group">
            <label for="answer_5">answer 5</label>
            <input type="text" name="answer_5">
        </div>
        <div class="form-group">
            <label for="answer_6">answer 6</label>
            <input type="text" name="answer_6">
        </div>
        <div class="form-group">
            <label for="advice">advice</label>
            <input id="input_question_advice" type="text" name="advice">
        </div>
        <div class="form-group">
            <input id="save_button" type="submit" class="btn btn-warning">
        </div>
    </form>
    <div class="row">
   <%
       for (Question question : (List<Question>)request.getAttribute("questions")) {
   %>
       <div class="question col-sm-3 text-center">
            <p class="category"><%= question.get("category") %></p>
            <h3 class="body"><%= question.get("body") %></h3>
            <ul class="answers">
                <%
                       for (QuestionOption questionOption : (List<QuestionOption>)question.getQuestionOptions()) {
                %>
                    <li><%= questionOption.get("body") %></li>
                 <% } %>
            </ul>
            <p><%= question.get("advice") %></p>

       </div>
   <%
       }
   %>
   </div>
   </div>
</body>
<!-- jQuery -->
<script type="text/javascript"
	src="resources/lib/bootstrap/js/jquery.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="resources/lib/bootstrap/js/bootstrap.min.js"></script>
</html>