<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String advice = "You should study scrum";
%>


<t:with_side_menu_and_status title="Add Question">
    <jsp:body>
			<form name="addQuestion" id="addQuestion" method="post"
				action="add_question">
				<select name="category" id="category">
				    <option value="1">Scrum</option>
				    <option value="2">Tech</option>
				    <option value="3">Team</option>
				 </select>
				 <textarea class="form-control" name="advice" id="advice" maxlength="500">You should study scrum</textarea>
				 <button type="submit" class="btn btn-default" name="update" id="update" value="add" >Add</button>
			</form>
    </jsp:body>
</t:with_side_menu_and_status>
