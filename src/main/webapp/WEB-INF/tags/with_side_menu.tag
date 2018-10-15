<%@tag description="With side menu" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="title" required="true" %>
<%@attribute name="extra_head" fragment="true" %>
<%@attribute name="extra_foot" fragment="true" %>
<t:basic title="${title}" extra_head="${extra_head}" extra_foot="${extra_foot}">
    <jsp:body>
        <jsp:include page="ui_common.jsp" />
		<div id="page-wrapper">
            <jsp:doBody/>
		</div>
    </jsp:body>
</t:basic>

