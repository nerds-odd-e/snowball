<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Massive Email Button</title>
<link rel="stylesheet" type="text/css" href="resources/decor.css">
<script type="text/javascript" src="resources/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="resources/sendemail.js"></script>
</head>
<body>
	<form name="main_emailform" id="main_emailform" method="post"
		action="/sendMail">
		<div class="emailform">
			<div class="float_full">
				To: <input type="text" name="recipient" id="recipient" />
			</div>
			<div class="float_full">
				Subject: <input type="text" name="subject" id="subject" />
			</div>
			<div class="float_full">
				<textarea rows="5" cols="20" name="content" id="content"></textarea>
			</div>
			<div class="float_full">
				<input type="button" class="send_button" id="send_button"
					value="send" disabled="disabled">
			</div>
		</div>
	</form>
</body>
</html>