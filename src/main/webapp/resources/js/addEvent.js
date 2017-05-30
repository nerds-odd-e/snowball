var needsCheckedElementIds = ['#coursename', '#courseDuration', '#courseStartDate', '#address', '#courseDetails','#instructor'];
$(document).ready(function() {

	disableSaveButton();

	$(needsCheckedElementIds.join(',')).keyup(function() {
		needsCheckedElementIds.forEach(function(elementId){
            checkSaveInputElement(elementId);
        });
	});

	$("#save_button").click(function() {
	    needsCheckedElementIds.forEach(function(elementId){
	    	$(elementId).val($(elementId).val().trim());
	    });
		submitForm();
	});

	var status = getUrlParameter('status'),
	    msg = getUrlParameter('msg');

    if(status && msg){
        $('#saveStatusLabel').text(status);
        $('#saveStatusBody').text(msg);
        $('#saveStatusModal').modal();
    }

});

function submitForm() {
	document.forms[0].submit();
	$(needsCheckedElementIds.join(',')).val("");
}

function checkSaveInputElement(domId) {

	if (isChange($(domId).val())) {
		enableSaveButton();
	} else {
		disableSaveButton();
	}
}

function isChange(value) {
	return value.replace(/^\s+|\s+$/gm,'') !== "";
}

function enableSaveButton() {
	$("#save_button").removeAttr('disabled');
}

function disableSaveButton() {
	$("#save_button").attr('disabled', 'disabled');
}

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

