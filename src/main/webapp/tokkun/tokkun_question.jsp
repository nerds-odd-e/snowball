<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.OnlineTest" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.Question" %>

<%
    Question question = (Question) request.getSession().getAttribute("question");
    pageContext.setAttribute("question", question);


    System.out.println(request);
    OnlineTest onlineTest = new OnlineTest(1);
    Question question1 = null;
    question1 = onlineTest.getCurrentQuestion();
    pageContext.setAttribute("question1", question1);
%>

<t:basic title="Tokkun Question">
    <jsp:attribute name="extra_head">
    </jsp:attribute>
    <jsp:body>
        <div id="page-wrapper">
            <h1>Tokkun</h1>
            <span id="title">Question</span>
            <span id="question">${question.getDescription()}</span>
        </div>
        <h2 id="description">test_A</h2>
    </jsp:body>
</t:basic>