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


function checkAddInputElement() {
	
	if (isChange($("#email").val())) {
		enableAddButton();
	} else {
		disableAddButton();
	}
}

function isChange(value) {
	return value.replace(/^\s+|\s+$/gm,'') !== "";
}

function enableAddButton() {
	$("#add_button").removeAttr('disabled');
}

function disableAddButton() {
	$("#add_button").attr('disabled', 'disabled');
}