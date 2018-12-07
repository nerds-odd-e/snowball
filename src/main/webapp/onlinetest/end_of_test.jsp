<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String alertMsg = (String) request.getSession().getAttribute("alertMsg");
    pageContext.setAttribute("alertMsg", alertMsg);
%>

<t:with_side_menu title="End Of Test">
    <jsp:attribute name="extra_head">
        <link href="/resources/question.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>

        <div class="container-fluid">
            <p class="alertMsg">${alertMsg}</p>
            <h1 id="title">End Of Test</h1>
            <div>
                <span id="correct-count">${onlineTest.getCorrectAnswerCount()}</span>
                /
                <span id="total-count">${onlineTest.getNumberOfQuestions()}</span>問
                <p>あなたの正解率は<span><span id="correct-percentage">${onlineTest.getCorrectPercentage()}</span>%</span></p>
                <p id="message">${onlineTest.showFinalMessage()}</p>
            </div>
            <c:if test="!${onlineTest.getShowAdvice()}" >
            <div class="advice_div">
                Advice
            </div>
            </c:if>
            <div>
                <p id="category-message">${onlineTest.getCategoryMessage()}</p>
            </div>
            <p>
                Thank you for your hard work.
                It will be about two hours soon. Why do not you take a break?:)
            </p>
        </div>

    </jsp:body>
</t:with_side_menu>
