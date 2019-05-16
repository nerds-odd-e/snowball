<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.OnlineTest" %>
<%@ page import="com.odde.massivemailer.model.onlinetest.Question" %>

<t:with_side_menu title="Add Category">
    <jsp:attribute name="extra_head">
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">

        <!-- Page Heading -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Add Category</h1>
            </div>
        </div>

         <form name="addContact" id="addContact" method="post" action="contacts">
        					<div class="row">
        						<div class="col-lg-12">

        							<div class="panel panel-default">
        								<div class="panel-heading">
        									<h3 class="panel-title">Category Information</h3>
        								</div>
        								<div class="panel-body">

        									<div class="row">



        										<div class="col-lg-1">Category Name:</div>
        										<div class="col-lg-11">
        											<input type="text" name="category_name"/>
        										</div>
        									</div>
        									<div class="row">
                                                <div class="col-lg-12">
                                                <button type="button" class="btn btn-default"
                                                    id="add_button" value="Add">Add</button>
                                                </div>
                                            </div>
        								</div>
        							</div>
        						</div>
        					</div>
                            </form>
        </div>
    </jsp:body>
</t:with_side_menu>