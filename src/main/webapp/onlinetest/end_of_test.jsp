<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:with_side_menu title="End Of Test">
    <jsp:body>

        <div class="container-fluid">
            <h1 id="title">End Of Test</h1>
            <div>
                <span id="correct-count">${correctlyAnsweredCount}</span>
                /
                <span id="total-count">${onlineTest.getNumberOfQuestions()}</span>問
                <p>あなたの正解率は<span><span id="correct-percentage">${onlineTest.getCorrectPercentage(correctlyAnsweredCount)}</span>%</span></p>
                <p id="message">${onlineTest.showFinalMessage()}</p>
            </div>
            <p>
                Thank you for your hard work.
                It will be about two hours soon. Why do not you take a break?:)
            </p>
        </div>

    </jsp:body>
</t:with_side_menu>
