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
		var firstName = item.name===undefined?'':item.name;
		var lastName = item.lastname===undefined?'':item.lastname;
		var company = item.company===undefined?'':item.company;
		selector.append('<li class="col-md-3 email-address" style="text-align: left">'+item.email+'</li>');
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
		var firstName = item.name===undefined?'':item.name;
		var lastName = item.lastname===undefined?'':item.lastname;
		var company = item.company===undefined?'':item.company;
		selector.append('<li class="col-md-1 email-checkbox" style="text-align: center"><input type="checkbox" onclick="whenContactIsSelected(' + idx + ')" id="' + idx + '" value="' + item.email + '" /></li>');
		selector.append('<li class="col-md-3 email-address" style="text-align: left">'+item.email+'</li>');
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

	$('#name').val(item.name);
	$('#lastname').val(item.lastname);
	$('#company').val(item.company);
	$('#email').val(item.email);
	$('#email_label').text(item.email);
}

function openEditContactModal()
{
	$('#editContactModal').modal();
}

function submitEditContact() {
	$("#editContact").submit();
}
