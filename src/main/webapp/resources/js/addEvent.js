
var addEvents = {
    allNotNullElementIds: ['#courseName', '#courseDuration', '#courseStartDate', '#address', '#courseDetails'],
    allOptionalElementIds: ['#instructor']
};

$(document).ready(function() {

	disableSaveButton();
	disableAddButton();

	$(addEvents.allNotNullElementIds.join(',')).keyup(function() {
		addEvents.allNotNullElementIds.forEach(function(elementId){
            checkSaveInputElement(elementId);
        });
	});

	$("#save_button").click(function() {
	    addEvents.allNotNullElementIds.forEach(trimElemVal);
	    addEvents.allOptionalElementIds.forEach(trimElemVal);
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

function trimElemVal(elementId){
	$(elementId).val($(elementId).val().trim());
}

function submitForm() {
	document.forms[0].submit();
	$(addEvents.allNotNullElementIds.join(',')).val("");
	$(addEvents.allOptionalElementIds.join(',')).val("");
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

function enableAddButton() {
	$("#add_button").removeAttr('disabled');
}

function disableAddButton() {
	$("#add_button").attr('disabled', 'disabled');
}

function getUrlParameter(sParam) {
    var sPageURL = $.decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}

module.exports = {
  isChangeExp: isChange,
  checkSaveInputElementExp: checkSaveInputElement,
  enableSaveButtonExp: enableSaveButton,
  disableSaveButtonExp: disableSaveButton,
  getUrlParameterExp: getUrlParameter
}