<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:with_side_menu title="Contact List">
    <jsp:body>
			<form name="batchContacts" id="batchContacts" method="post" action="batchContacts">
				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Add Contact Batch</h1>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-12">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">File Information</h3>
								</div>
								<div class="panel-body">
                                <div class="row" style="padding:0 20px">
                                    <input type="file" name="myfile" id="batchFile" accept=".csv"/>
                                    <input type="text" name="data" id="data" style="display:none"/>
								</div>
								</div>
						</div>
					</div>
					</form>
				</div>

			</form>
    </jsp:body>
</t:with_side_menu>
