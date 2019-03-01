<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:basic title="QuestionCategory">
    <jsp:body>
        <form name="question" id="questionForm" method="get" action="/onlinetest/launchQuestion">
            <input type="checkbox" name="optionId" value="hoge" checked /><label class="selected_incorrect"/>hoge</label>
            <input type="submit" id="start_test" value="start" class="btn-primary btn"/>
        </form>
    </jsp:body>
</t:basic>