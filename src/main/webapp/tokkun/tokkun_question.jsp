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
        <div class="container-fluid">
            <div id="page-wrapper">
                <h1>Tokkun</h1>
                <span id="title">Question</span>
                <span id="question">${question.getDescription()}</span>
            </div>
            <h2 id="description">test_A</h2>
            <form name="tokkun" id="tokkunForm" method="post"
                action="/tokkun/question">
                <c:forEach items="${question.getOptions()}" var="option" varStatus="status">
                    <li>
                        <c:if test="${question.getIsMultiQuestion()}">
                         <input type="checkbox" id="option${status.index + 1}" name="optionId" value="${option.getLongId()}" />${option.getDescription()}</label>
                        </c:if>
                        <c:if test="${!question.getIsMultiQuestion()}">
                         <input type="radio" id="option${status.index + 1}" name="optionId" value="${option.getLongId()}" />${option.getDescription()}</label>
                        </c:if>
                    </li>
                </c:forEach>

                <div class="col-lg-12">
                    <input type="submit" id="answer" value="Answer">
                </div>
            </form>
        </div>
    </div>
    </jsp:body>
</t:basic>