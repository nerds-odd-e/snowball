$(document).ready(function() {

	disableUpdateTemplateButton();

	$("#content, #subject").keyup(function() {
		checkUpdateTemplateInputElement();
	});


	$("#update_button").click(function() {
		$("#subject").val($("#subject").val().trim());
		$("#content").val($("#content").val().trim());
		submitForm();
	});

});

function submitForm() {
	$("#updatetemplate").submit();
}

function checkUpdateTemplateInputElement() {
	var contentIsBlank = isBlank($("#content").val());
	var subjectIsBlank = isBlank($("#subject").val());
	
	if (contentIsBlank || subjectIsBlank) {
		disableUpdateTemplateButton();
	} else {
		enableUpdateTemplateButton();
	}
}


function isBlank(value) {
	return value.replace(/^\s+|\s+$/gm,'') === "";
}

function enableUpdateTemplateButton() {
	$("#update_button").removeAttr('disabled');
}

function disableUpdateTemplateButton() {
	$("#update_button").attr('disabled', 'disabled');
}

