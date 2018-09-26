$(document).ready(function() {

	$("#save_button").click(function() {
		submitForm();
	});

});

function submitForm() {
	// document.forms[0].submit();
	$("#input_question_body").val("");
}