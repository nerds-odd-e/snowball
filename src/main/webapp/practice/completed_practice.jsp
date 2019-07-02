<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:with_side_menu title="End of Practice">
    <jsp:attribute name="extra_head">
        <link href="/resources/question.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>

        <div class="container-fluid">
            <h1 id="title">You have completed the practice</h1>
        </div>
    </jsp:body>
</t:with_side_menu>
