<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:basic title="Question Category">
    <jsp:body>
    <div id="page-wrapper">
        <form name="question" id="questionForm" method="get" action="/onlinetest/launchQuestion">
        <div class="container-fluid">
        <h1>Select Category</h1>
        <li><input type="checkbox" name="optionId" value="Scrum" disabled checked /><label />Scrum</label></li>
        <li><input type="checkbox" name="optionId" value="Tech" disabled checked /><label />Tech</label></li>
        <li><input type="checkbox" name="optionId" value="Team" disabled checked /><label />Team</label></li>
        <input type="reset" value="clear"/>
            <input type="submit" id="start_test" value="start"/>
         </div>
        </form>
    </div>
    </jsp:body>
</t:basic>