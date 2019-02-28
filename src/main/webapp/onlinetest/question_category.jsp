<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:basic title="QuestionCategory">
    <jsp:body>
        <form name="question" id="questionForm" method="post" action="question">
            <input type="checkbox" name="optionId" value="hoge" checked /><label class="selected_incorrect"/>hoge</label>
        </form>
    </jsp:body>
</t:basic>