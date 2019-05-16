<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    String question = (String) request.getSession().getAttribute("question");
    pageContext.setAttribute("question", question);
%>
<t:basic title="Tokkun Question">
    <jsp:attribute name="extra_head">
    </jsp:attribute>
    <jsp:body>
        <div id="page-wrapper">
            <h1>Tokkun</h1>
            <span id="title">Question</span>
            <span id="question">${question}</span>
        </div>
    </jsp:body>
</t:basic>