<%@tag description="template with side menu and status" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@attribute name="title" required="true" %>
<%@attribute name="extra_head" fragment="true" %>
<%@attribute name="extra_foot" fragment="true" %>

<c:set var="message">
    <%
        if (request.getParameter("status") != null) {
        %>
          <div class='row'>
              <div class='col-lg-12'>
                  <div class='alert alert-info alert-dismissable'>
                      <button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>
                          <i class='fa fa-info-circle'></i>&nbsp;<b>
                          <span id='email_result'>
            <%out.println(request.getParameter("status"));%> : <% out.println(request.getParameter("msg"));%>
          </span></b></div> </div></div>
        <%
        }
    %>
</c:set>

<t:with_side_menu title="${title}" extra_head="${extra_head}" extra_foot="${extra_foot}">
    <jsp:body>
        <div class="container-fluid">
            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">${title}</h1>
                </div>
            </div>
            ${message}

            <jsp:doBody/>
        </div>
    </jsp:body>
</t:with_side_menu>
