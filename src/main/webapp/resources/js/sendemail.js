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
	var recipientIsBlank = isBlank($("#recipient").val());
	var contentIsBlank = isBlank($("#content").val());
	var subjectIsBlank = isBlank($("#subject").val());
	
	if (recipientIsBlank || contentIsBlank || subjectIsBlank) {
		disableButton();
	} else {
		enableButton();
	}
}

function isBlank(value) {
	return value.replace(/^\s+|\s+$/gm,'') === "";
}

function enableButton() {
	$("#send_button").removeAttr('disabled');
}

function disableButton() {
	$("#send_button").attr('disabled', 'disabled');
}