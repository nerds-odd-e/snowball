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
    <form name="sendmail" id="sendmail" method="post" action="/sendMail">
        <table>
            <tr>
                <td>
                  <div class="textCol">
                    <label for="recipient">To: </label>
                   </div>
                </td>
                <td>
                  <div class="valueCol">
                    <input type="text" name="recipient" id="recipient" class="inputText" />
                  </div>
                </td>
            </tr>
            <tr>
                <td>
                  <div class="textCol">
                    <label for="recipient">Subject: </label>
                  </div>
                </td>
                <td>
                 <div class="valueCol">
                    <input type="text" name="subject" id="subject" class="inputText" />
                </div>
                </td>
            </tr>            
            <tr>
                <td>
                  <div>
                    <label for="recipient">body: </label>
                  </div>
                </td>
                <td>
                  <div>
                        <textarea rows="20" name="content" id="content" class="inputText"></textarea>
                  </div>
                </td>
            </tr>   
            <tr>
                <td></td>
                <td>
                  <div>
                      <input type="button" class="send_button" id="send_button"
                            value="send" disabled="disabled">
                  </div>
                </td>
            </tr>                    
        </table>
	</form>
</body>
</html>