<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.odde.massivemailer.model.onlinetest.CategoryAdvice" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.Category" %>
<%@ page import="java.util.*" %>

<%
    String advice = (String) request.getAttribute("advice");
    List<CategoryAdvice> categoryAdviceList = CategoryAdvice.findAll();
    pageContext.setAttribute("categoryAdviceList", categoryAdviceList);
    pageContext.setAttribute("category", Category.values());
%>

<t:with_side_menu_and_status title="Update Advice">
    <jsp:body>
        <form name="addQuestion" id="addQuestion" method="post"
            action="advice">
             <div class="form-group form-inline">
             <label for="category">Category</label>
                 <select name="category" id="category">
                     <option value="1">Scrum</option>
                     <option value="2">Tech</option>
                     <option value="3">Team</option>
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
            <c:forEach items="${categoryAdviceList}" var="categoryAdvice" varStatus="count">
                <tr>
                    <th style="width:20%">${category[categoryAdvice.getString("category_id")].getName()}</th>
                    <td id="scrumDescription"+count>${categoryAdvice.getString("advice")}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:with_side_menu_and_status>
