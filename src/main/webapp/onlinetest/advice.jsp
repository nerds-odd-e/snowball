<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:basic title="Advice">
    <jsp:attribute name="extra_head">
        <link href="/resources/question.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>

        <div id="page-wrapper">
            <div class="container-fluid">
                <h1>Advice</h1>
                <h2 id="questionText">${currentQuestion.getDescription()}</h2>
                <ul>

               <c:forEach items="${currentQuestion.getOptions()}" var="option">
                    <li>
                         <c:set var="optionId">${option.stringId()}</c:set>
                            <c:if test="${selectedOption.contains(optionId)}">
                                <c:if test="${option.isCorrect()}">
                                    <input type="checkbox" name="optionId" value="${option.stringId()}" checked disabled/><label class="selected_correct"/>${option.getDescription()}</label>
                               </c:if>
                               <c:if test="${!option.isCorrect()}">
                                    <input type="checkbox" name="optionId" value="${option.stringId()}" checked disabled/><label class="selected_incorrect"/>${option.getDescription()}</label>
                               </c:if>
                            </c:if>
                            <c:if test="${!selectedOption.contains(optionId)}">
                                <c:if test="${option.isCorrect()}">
                                    <input type="checkbox" name="optionId" value="${option.stringId()}" disabled/><label class="unselected_correct"/>${option.getDescription()}</label>
                                </c:if>
                                <c:if test="${!option.isCorrect()}">
                                    <input type="checkbox" name="optionId" value="${option.stringId()}" disabled/><label class="unselected_incorrect"/> ${option.getDescription()}</label>
                                </c:if>
                            </c:if>
                    </li>
               </c:forEach>

                </ul>
                <div id="advice" class="jumbotron">${currentQuestion.getAdvice()}</div>
                <form action="/onlinetest/question" method="get">
                    <input type="submit" id="next" value="Next" class="btn-primary btn"/>
                </form>
                <div style="float:right;" id="progress">
                    <span id="numberOfAnsweredQuestions">${progress}</span>
                </div>
            </div>
        </div>
    </jsp:body>
</t:basic>
