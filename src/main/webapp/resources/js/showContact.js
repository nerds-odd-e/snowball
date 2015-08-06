function retrieveContactListFromServer()
{
	var contactList = [];	
	
	$.ajax({
	    type: 'GET',
	    url: 'showContact',
	    dataType: 'json',
	    success: function(data) {contactList = data },
	    async: false
	});
	
	return contactList;
}

function renderContactList(json, selector)
{
	$.each(json, function(idx, item) {
		selector.append('<li class="col-md-1 email-id" style="text-align: center">'+item.id+'</li> <li class="col-md-11 email-address" style="text-align: center">'+item.email+'</li>');
	})
}

function renderContactSelectionList(json, selector)
{
	//selector.loadTemplate("/massive_mailer/contactSelectionTemplate.html", json);
	
	
	var contactCheckbox = '<li class="col-md-2 email-checkbox" style="text-align: center"><input type="checkbox" id=""/></li>';
	var contactEmail = '<li class="col-md-4 email-address" style="text-align: center">';
	var contactName = '<li class="col-md-3 contact-name" style="text-align: center">';
	var contactLastName = '<li class="col-md-3 contact-lname" style="text-align: center">';
	
	
	selector.html('');
	$.each(json, function(idx, item) {
		
		selector.append(contactCheckbox + contactEmail + item.email+ '</li>');
	})
}