$(document).ready(function(){
	$("#editContact").click(function() {
		$('#editContactModal').show();
		$('#emailLabel').val($('#emailField').val());
	});
	
	$('#saveButton').bind( "click", function() {
		saveContactWithAttributeAfterEdit();
	});

});

function saveContactWithAttributeAfterEdit() {
	console.log('test');
}
