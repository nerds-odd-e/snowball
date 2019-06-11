<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="com.odde.snowball.model.ContactPerson"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<t:with_side_menu title="ODD-E">
    <jsp:attribute name="extra_head">
        <style>
        #selectContactTable li {
            height: 25px;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        tr:hover {
           background: #efefef;
           cursor: pointer;
        }
        td a {
           display: block;
           border: 1px solid black;
           padding: 16px;
        }
        </style>
    </jsp:attribute>
    <jsp:attribute name="extra_foot">
        <!-- jQuery -->
        <script type="text/javascript" src="/resources/lib/jquery-template/jquery.loadTemplate-1.4.4.js"></script>
        <script type="text/javascript" src="resources/js/showTrackedEmail.js"></script>
        <script type="text/javascript">
        $(document).ready(function() {
            var trackingList = retrieveTrackingEmailListFromServer();
            renderTrackingEmailList(trackingList, $('#trackingTable tbody'));
            $(".clickable-row").click(function() {
                    window.document.location = $(this).data("href");
             });
        });
        </script>
    </jsp:attribute>
    <jsp:body>
            <form name="sendmail" id="sendmail" method="post" action="/sendMail">
				<div class="container-fluid">

					<!-- Page Heading -->
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Email Tracking</h1>
						</div>
					</div>
					<!-- /.row -->

                    <!-- table -->

                    <div class="row">
                        <div class="col-lg-12">
                            <table id="trackingTable" class="table">
                                <thead>
                                    <tr>
                                      <th>Subject</th>
                                      <th>Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
				</div>
			</div>
	</form>

    </jsp:body>
</t:with_side_menu>
