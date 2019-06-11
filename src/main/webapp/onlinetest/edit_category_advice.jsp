<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.odde.snowball.model.onlinetest.Category" %>
<%@ page import="com.odde.snowball.model.base.Repository" %>;
<%@ page import="java.util.*" %>

<%
    String advice = (String) request.getAttribute("advice");
    List<Category> categoryList = Repository.repo(Category.class).findAll();
    pageContext.setAttribute("categoryList", categoryList);
%>

<t:with_side_menu_and_status title="Update Advice">
    <jsp:body>
        <form name="addQuestion" id="addQuestion" method="post"
            action="advice">
             <div class="form-group form-inline">
             <label for="category">Category</label>
                 <select name="category" id="category">
                    <c:forEach items="${categoryList}" var="category" varStatus="Status">
                        <option value="${category.getStringId()}">${category.getName()}</option>
                    </c:forEach>
                 </select>
             </div>
             <div class="form-group">
                 <label for="advice">Advice</label>
                 <textarea class="form-control" name="advice" id="advice" maxlength="500">${advice}</textarea>
             </div>
             <div class="form-group">
                 <label for="link">Link</label>
                 <input class="form-control" name="link" id="link" maxlength="500" style="min-width: 300px;"></input>
             </div>
             <div class="form-group">
                 <button type="submit" class="btn btn-default" name="update" id="update" value="add" >Add</button>
             </div>
        </form>
        <h1> Current Setting </h1>
        <table class="table table-responsive table-bordered">
            <tbody id="categoryTable">
            <c:forEach items="${categoryList}" var="category" varStatus="count">
                <tr>
                    <th style="width:20%">${category.getName()}</th>
                    <td>${category.getAdvice()}</td>
                    <td>${category.getLink()}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:with_side_menu_and_status>
