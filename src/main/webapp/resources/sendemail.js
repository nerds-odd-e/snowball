$(document).ready(function() {

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

});

function submitForm() {
	$("#sendmail").submit();
}

function checkInputElement() {
	if (isBlank($("#recipient").val()) || isBlank($("#content").val())
			|| isBlank($("#subject").val())) {
		disableButton();
	} else {
		enableButton();
	}
}

function isBlank(value) {
	return value.trim() === "";
}

function enableButton() {
	$("#send_button").removeAttr('disabled');
}

function disableButton() {
	$("#send_button").attr('disabled', 'disabled');
}