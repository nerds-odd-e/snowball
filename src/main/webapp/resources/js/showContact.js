function render() {
  var contactList = retrieveContactListFromServer();
	renderContactList(contactList, $('#contactTable'), false);

}

$(document).ready(function() {
	render();
	$("#save_button").click(function() {
		submitEditContact();
	});
});



function retrieveContactListFromServer()
{
	var contactList = [];

	$.ajax({
	    type: 'GET',
	    url: '/contacts',
	    dataType: 'json',
	    success: function(data) {contactList = data },
	    async: false
	});

	return contactList;
}

function Contact(attributes) {
    this.firstName = attributes.firstName===undefined?'':attributes.firstName;
    this.lastName = attributes.lastName===undefined?'':attributes.lastName;
    this.company = attributes.company===undefined?'':attributes.company;
    this.city = attributes.city===undefined?'':attributes.city;
    this.country = attributes.country===undefined?'':attributes.country;
    this.email = attributes.email===undefined?'':attributes.email;
    this.location = this.city + ", " + this.country
}

function createTableData(cssClasses, value) {
    var tableRow = $('<tr>');
    return '<td class="' + cssClasses + '" style="text-align: left; line-height: 200%;">' + value + '</td>';
}

function createButtonElement(buttonId, buttonName, clickEvent) {
    return '<button class="btn btn-default" id="' + buttonId + '" name="' + buttonName + '" onclick=\'' + clickEvent + '\'>' + buttonName +'</button>';
}

function renderContactList(json, selector)
{

    selector.html('');
	  $.each(json, function(idx, item) {
        var contact = new Contact(item);
        var tableContent = [
          ['email-address', contact.email],
          ['', contact.firstName],
          ['', contact.lastName],
          ['company', contact.company],
          ['location', contact.location],
          ['', createButtonElement('edit_button', 'edit', 'showEditContactDetail(' + JSON.stringify(item) + ')')]
        ];
        generateContactTableRow(selector, tableContent);
    })
}

function renderContactSelectionList(json, selector)
{
	selector.html('');
	$.each(json, function(idx, item) {
		var contact = new Contact(item);
        var tableContent = [
            ['email-checkbox', "<input type=\"checkbox\" onclick=\"whenContactIsSelected(" + idx + ")\" id=\"" + idx + "\" value=\"" + item.email + "\" />"],
            ['email-address', contact.email],
            ['contact-name', contact.firstName],
            ['contact-lname', contact.lastName],
            ['contact-cname', contact.company],
            ['contact-location', contact.location]
        ];
        generateContactTableRow(selector, tableContent);
	})
}

function generateContactTableRow(selector, content) {
    var tr = $('<tr>' );
    selector.append(tr);
    content.forEach(function(element) {
        tr.append(createTableData(element[0], element[1]));
    });
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}


exports.showEditContactDetail = function showEditContactDetail(item)
{
	openEditContactModal();
	insertDataIntoContactModal(item);
}

function insertDataIntoContactModal(item){
	$('#name').val(item.firstName);
	$('#lastName').val(item.lastName);
	$('#company').val(item.company);
	$('#countrydrp').val(item.country);
	$('#city').val(item.city);
	$('#email').val(item.email);
	$('#email_label').text(item.email);
}

exports.openEditContactModal = function openEditContactModal()
{
	$('#editContactModal').modal();
}

function submitEditContact() {
	$("#editContact").submit();
}
