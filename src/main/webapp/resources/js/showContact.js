function retrieveContactListFromServer()
{
	var contactList = [];	
	
	$.ajax({
	    type: 'GET',
	    url: 'contacts',
	    dataType: 'json',
	    success: function(data) {contactList = data },
	    async: false
	});
	
	return contactList;
}

function renderContactList(json, selector)
{
	$.each(json, function(idx, item) {
		var firstName = item.attributes.firstname===undefined?'':item.attributes.firstname;
		var lastName = item.attributes.lastname===undefined?'':item.attributes.lastname;
		var company = item.attributes.company===undefined?'':item.attributes.company;
		var location = item.attributes.location===undefined?'':item.attributes.location;
		selector.append('<li class="col-md-2 email-address" style="text-align: left">'+item.attributes.email+'</li>');
		selector.append('<li class="col-md-2" style="text-align: left">'+firstName+'</li>');
		selector.append('<li class="col-md-2" style="text-align: left">'+lastName+'</li>');
		selector.append('<li class="col-md-2 company" style="text-align: left">'+company+'</li>');
		selector.append('<li class="col-md-2 location" style="text-align: left">'+location+'</li>');
		selector.append('<li class="col-md-1" style="text-align: left; padding-bottom: 1%"><input id=\'edit_button\' type=\'button\' name=\'edit\' value=\'edit\' onclick=\'showEditContactDetail('+JSON.stringify(item)+')\'/></li>');
	})
}

function renderContactSelectionList(json, selector)
{
	selector.html('');
	$.each(json, function(idx, item) {
		var firstName = item.attributes.firstname===undefined?'':item.attributes.firstname;
		var lastName = item.attributes.lastname===undefined?'':item.attributes.lastname;
		var company = item.attributes.company===undefined?'':item.attributes.company;
		var location = item.attributes.location===undefined?'':item.attributes.location;
		selector.append('<li class="col-md-1 email-checkbox" style="text-align: center"><input type="checkbox" onclick="whenContactIsSelected(' + idx + ')" id="' + idx + '" value="' + item.attributes.email + '" /></li>');
		selector.append('<li class="col-md-3 email-address" style="text-align: left">'+item.attributes.email+'</li>');
		selector.append('<li class="col-md-2 contact-name" style="text-align: left">'+firstName+'</li>');
		selector.append('<li class="col-md-3 contact-lname" style="text-align: left">'+lastName+'</li>');
		selector.append('<li class="col-md-3 contact-cname" style="text-align: left">'+company+'</li>');
		selector.append('<li class="col-md-3 contact-location" style="text-align: left">'+location+'</li>');
	})
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}


function showEditContactDetail(item)
{
	openEditContactModal();
	insertDataIntoContactModal(item);
}

function insertDataIntoContactModal(item){

	$('#name').val(item.attributes.firstname);
	$('#lastname').val(item.attributes.lastname);
	$('#company').val(item.attributes.company);
	$('#location').val(item.attributes.location);
	$('#email').val(item.attributes.email);
	$('#email_label').text(item.attributes.email);
}

function openEditContactModal()
{
	$('#editContactModal').modal();
}

function submitEditContact() {
	$("#editContact").submit();
}
