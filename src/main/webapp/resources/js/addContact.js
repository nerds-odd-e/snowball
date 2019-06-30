$(document).ready(function() {

	disableAddButton();

	$("#email").keyup(function() {
		checkAddInputElement();
	});

	$("#add_button").click(function() {
		$("#email").val($("#email").val().trim());
		submitForm();
	});
});

function submitForm() {
	document.forms[0].submit();
	$("#email").val("");
}

function enableAddButton() {
	$("#add_button").removeAttr('disabled');
}

function disableAddButton() {
	$("#add_button").attr('disabled', 'disabled');
}

exports.checkAddInputElement = function checkAddInputElement() {

	if (this.isChange($("#email").val())) {
		enableAddButton();
	} else {
		disableAddButton();
	}
}

exports.isChange = function isChange(value) {
	return value.replace(/^\s+|\s+$/gm,'') !== "";
}
