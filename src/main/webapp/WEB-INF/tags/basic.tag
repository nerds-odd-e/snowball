<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="title" required="true" %>
<%@attribute name="extra_head" fragment="true" %>
<%@attribute name="extra_foot" fragment="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${title}</title>
<!-- Bootstrap Core CSS -->
<link href="/resources/lib/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="/resources/lib/bootstrap/css/sb-admin.css" rel="stylesheet">

<link href="/resources/lib/bootstrap/css/plugins/morris.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="/resources/lib/bootstrap/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
  <jsp:invoke fragment="extra_head"/>
</head>
<body>
    <jsp:doBody/>
</body>
<!-- jQuery -->
<script type="text/javascript"
	src="/resources/lib/bootstrap/js/jquery.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="/resources/lib/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/js/addContactBatch.js"></script>
<script type="text/javascript" src="/resources/js/populateCountriesDropdown.js"></script>
<script type="text/javascript" src="/resources/js/showContact.js"></script>
<script type="text/javascript" src="/resources/js/showParticipants.js"></script>
<script type="text/javascript" src="/resources/js/showCourses.js"></script>
<jsp:invoke fragment="extra_foot"/>
</html>
