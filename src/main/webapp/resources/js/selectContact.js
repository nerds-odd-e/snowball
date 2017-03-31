var selectedContact = "";

function getCurrentSelectedContactValue()
{
	return selectedContact;
}

function setCurrentSelectedContactValue(data)
{
	selectedContact = "";
}

function whenContactIsSelected(checkBoxID) {
	var checkboxObj = $("#" + checkBoxID);
	var email = checkboxObj.val();
	var isChecked = checkboxObj.prop('checked');

	if (isChecked) {
		selectedContact += email + ";";
	} else {
		selectedContact = selectedContact.replace(email+";","");
	}
}

function whenAddButtonIsClicked() {
	
	var existingContact = $('#recipient').val();
	$('#recipient').val(existingContact + getCurrentSelectedContactValue());
	$('#selectContactModal').modal('hide');
}