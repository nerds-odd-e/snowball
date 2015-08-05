/**
 * 
 */
$(document).ready(function() {

	retrieveContactListFromServer();

});

function retrieveContactListFromServer()
{
	$.getJSON("showContact", renderContactList);
}

function renderContactList(json)
{
	var $contactTable = $('#contactTable');
	$.each(json, function(idx, item) {
		$contactTable.append('<li class="col-md-1" style="text-align: center">'+item.id+'</li> <li class="col-md-11" style="text-align: center">'+item.email+'</li>');
	})
}