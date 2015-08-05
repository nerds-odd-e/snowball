function retrieveContactListFromServer()
{
	var contactList = [];
	
	var getContacts = function() {
		$.getJSON("showContact", function(data) {
			contactList = data;
		});
		
		return contactList;
	};

	return getContacts();
}

function renderContactList(json, selector)
{
	$.each(json, function(idx, item) {
		selector.append('<li class="col-md-1 email-id" style="text-align: center">'+item.id+'</li> <li class="col-md-11 email-address" style="text-align: center">'+item.email+'</li>');
	})
}

function renderContactSelectionList(json, selector)
{
	var contactId = '<li class="col-md-1 email-id" style="text-align: center">';
	var contactEmail = '<li class="col-md-10 email-address" style="text-align: center">';
	var contactCheckbox = '<li class="col-md-1 email-checkbox" style="text-align: center"><input type="checkbox" id=""/></li>';
	$.each(json, function(idx, item) {
		
		selector.append(contactCheckbox + contactId + item.id + '</li>' + contactEmail + item.email+ '</li>');
	})
}