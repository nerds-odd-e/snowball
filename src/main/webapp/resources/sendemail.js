$(document).ready(
		function() {

			disableButton();

			$("#recipient, #content, #subject").keyup(function() {
				checkInputElement();
			});

			$("#send_button").click(function() {
				$("#recipient").val($("#recipient").val().trim());
				$("#subject").val($("#subject").val().trim());
				$("#content").val($("#content").val().trim());
				submitForm();
			});

			function submitForm() {
				$("#sendmail").submit();
			}
			function checkInputElement() {
				if (isBlank($("#recipient")) || isBlank($("#content"))
						|| isBlank($("#subject"))) {
					disableButton();
				} else {
					enableButton();
				}
			}

			function isBlank(ele) {
				return ele.val().trim() === "";
			}
			function enableButton() {
				$("#send_button").removeAttr('disabled');
			}

			function disableButton() {
				$("#send_button").attr('disabled', 'disabled');
			}
		});