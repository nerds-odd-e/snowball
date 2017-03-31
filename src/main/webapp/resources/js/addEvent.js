$(document).ready(function() {

	disableRegisterButton();

	$("#evtTitle").keyup(function() {
		checkRegisterInputElement();
	});

	$("#register_button").click(function() {
		$("#evtTitle").val($("#evtTitle").val().trim());
		submitForm();
	});

	//var locationList = retrieveLocationListFromServer();
    //populateLocationListToDropdown(locationList);
});

function retrieveLocationListFromServer()
{
	var locationList = [];

	$.ajax({
	    type: 'GET',
	    url: 'locations',
	    dataType: 'json',
	    success: function(data) {
	        locationList = data;
	        console.log(data);
	     },
	    async: false
	});

	return locationList;
}

function populateLocationListToDropdown(data) {

    $('#locationdrp').empty();
    $.each(data, function(key, value) {
        $('#locationdrp').append($('<option></option>').val(value).html(value));
    });

}

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