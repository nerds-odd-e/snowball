<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:with_side_menu title="End Of Test">
    <jsp:body>

        <div class="container-fluid">
            <h1 id="title">End Of Test</h1>
            <p id="correct-count">1</p><p id="total-count">${onlineTest.getNumberOfQuestions()}</p>
            <p>
                Thank you for your hard work.
                It will be about two hours soon. Why do not you take a break?:)
            </p>
        </div>

    </jsp:body>
</t:with_side_menu>
