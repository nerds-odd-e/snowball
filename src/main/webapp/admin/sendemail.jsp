<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="com.odde.snowball.model.ContactPerson"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<t:with_side_menu_and_status title="Edit Template">
    <jsp:attribute name="extra_head">
        <style>
        #selectContactTable li {
            height: 25px;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        </style>
    </jsp:attribute>
    <jsp:attribute name="extra_foot">
        <script type="text/javascript" src="/resources/lib/jquery-template/jquery.loadTemplate-1.4.4.js"></script>
        <script type="text/javascript" src="/resources/js/sendemail.js"></script>
        <script type="text/javascript" src="/resources/js/selectContact.js"></script>
        <script type="text/javascript" src="/resources/js/showTemplate.js"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $('#select_contact').click(function () {
                    var contactList = retrieveContactListFromServer();
                    selectedContact = '';
                    renderContactSelectionList(contactList, $('#selectContactTable'));
                });
                $('#add_contact_button').click(function () {
                    whenAddButtonIsClicked();
                });
            });
        </script>
    </jsp:attribute>
    <jsp:body>

        <form name="sendmail" id="sendmail" method="post">
					<input type="hidden" id="msg_sent_cnt" value="${param.repcnt}" />
					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Edit Template</h3>
								</div>
								<div class="panel-body">
									<div class="row">
										<div class="col-lg-1">Template:</div>
										<div class="col-lg-11">
											<select name="templateList" id="templateList"></select>
											
										</div>
									</div>

									<br />
									<div class="row">
										<div class="col-lg-1">To:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="recipient"
												id="recipient"> 
											<span id="select_contact" data-toggle="modal" data-target="#selectContactModal"
											class="glyphicon glyphicon-plus add-contact-button"
											aria-hidden="true"></span>
											
										</div>
									</div>
									<br />
									<div class="row">
										<div class="col-lg-1">Subject:</div>
										<div class="col-lg-11">
											<input type="text" class="form-control" name="subject"
												id="subject">
										</div>
									</div>
									<br />
									<div class="row">
										<div class="col-lg-12">
											<textarea class="form-control" rows="20" name="content"
												id="content"></textarea>
										</div>
									</div>
									<br />
									<div class="row">
										<div class="col-lg-1">
											<button type="button" class="btn btn-default"
												id="send_button" value="send" disabled="disabled">Send</button>
										</div>
										<div class="col-lg-2">
											<button type="button" class="btn btn-default"
												id="update_button" value="update" >Update</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
		<%@ include file="select_contact.jsp" %>
	</form>
    </jsp:body>
</t:with_side_menu_and_status>

