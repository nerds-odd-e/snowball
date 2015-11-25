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
		var firstName = item.attributes.FirstName===undefined?'':item.attributes.FirstName;
		var lastName = item.attributes.LastName===undefined?'':item.attributes.LastName;
		var company = item.attributes.Company===undefined?'':item.attributes.Company;
		selector.append('<li class="col-md-3 email-address" style="text-align: left">'+item.attributes.Email+'</li>');
		selector.append('<li class="col-md-2" style="text-align: left">'+firstName+'</li>');
		selector.append('<li class="col-md-3" style="text-align: left">'+lastName+'</li>');
		selector.append('<li class="col-md-3" style="text-align: left">'+company+'</li>');
		selector.append('<li class="col-md-1" style="text-align: left; padding-bottom: 1%"><input type=\'button\' name=\'edit\' value=\'edit\' onclick=\'showEditContactDetail('+JSON.stringify(item)+')\'/></li>');
	})
}

function renderContactSelectionList(json, selector)
{
	selector.html('');
	$.each(json, function(idx, item) {
		var firstName = item.attributes.FirstName===undefined?'':item.attributes.FirstName;
		var lastName = item.attributes.LastName===undefined?'':item.attributes.LastName;
		var company = item.attributes.Company===undefined?'':item.attributes.Company;
		selector.append('<li class="col-md-1 email-checkbox" style="text-align: center"><input type="checkbox" onclick="whenContactIsSelected(' + idx + ')" id="' + idx + '" value="' + item.attributes.Email + '" /></li>');
		selector.append('<li class="col-md-3 email-address" style="text-align: left">'+item.attributes.Email+'</li>');
		selector.append('<li class="col-md-2 contact-name" style="text-align: left">'+firstName+'</li>');
		selector.append('<li class="col-md-3 contact-lname" style="text-align: left">'+lastName+'</li>');
		selector.append('<li class="col-md-3 contact-cname" style="text-align: left">'+company+'</li>');
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

	$('#name').val(item.attributes.FirstName);
	$('#lastname').val(item.attributes.LastName);
	$('#company').val(item.attributes.Company);
	$('#email').val(item.attributes.Email);
	$('#email_label').text(item.attributes.Email);
}

function openEditContactModal()
{
	$('#editContactModal').modal();
}

function submitEditContact() {
	$("#editContact").submit();
}
