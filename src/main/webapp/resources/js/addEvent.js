$(document).ready(function() {

	disableRegisterButton();

	$("#evtTitle").keyup(function() {
		checkRegisterInputElement();
	});

	$("#register_button").click(function() {
		$("#evtTitle").val($("#evtTitle").val().trim());
		submitForm();
	});

});

function submitForm() {
	document.forms[0].submit();
	$("#evtTitle").val("");
}


function checkRegisterInputElement() {

	if (isChange($("#evtTitle").val())) {
		enableRegisterButton();
	} else {
		disableRegisterButton();
	}
}

function isChange(value) {
	return value.replace(/^\s+|\s+$/gm,'') !== "";
}

function enableRegisterButton() {
	$("#register_button").removeAttr('disabled');
}

function disableRegisterButton() {
	$("#register_button").attr('disabled', 'disabled');
}