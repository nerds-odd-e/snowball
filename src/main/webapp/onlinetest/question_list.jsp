<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:with_side_menu title="Question List">
    <jsp:body>
			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Question List</h1>
					</div>
				</div>
				<!-- /.row -->

				<input type="hidden" id="msg_sent_cnt" value="${param.repcnt}" />
					<div class="col-lg-12" id="questionTables">
						<div class="panel panel-default">
                            <table class="table table-responsive table-bordered">
                                <thead>
                                    <th>Question 1</th>
                                </thead>
                                <tbody id="questionTable">
                                    <tr>
                                        <th>Description</th>
                                        <td id="description">what is 1+1?</td>
                                    </tr>
                                    <tr>
                                        <th>Category</th>
                                        <td id="category">Scrum</td>
                                    </tr>
                                    <tr>
                                        <th>Option1</th>
                                        <td id="option1">must be 3!</td>
                                    </tr>
                                    <tr id="option2row" class="bg-success">
                                        <th>Option2</th>
                                        <td id="option2">of course 2.</td>
                                    </tr>
                                    <tr>
                                        <th>Option3</th>
                                        <td id="option3">must be 3!</td>
                                    </tr>
                                    <tr>
                                        <th>Option4</th>
                                        <td id="option4">of course 4.</td>
                                    </tr>
                                    <tr>
                                        <th>Option5</th>
                                        <td id="option5">of course 5.</td>
                                    </tr>
                                    <tr>
                                        <th>Advice</th>
                                        <td id="advice">you should read a math book</td>
                                    </tr>
                                </tbody>
                            </table>
						</div>
					</div>

			<div class="modal fade" id="editContactModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Edit Contact</h4>
						</div>


					</div>
				</div>
			</div>
		</div>
    </jsp:body>
</t:with_side_menu>




