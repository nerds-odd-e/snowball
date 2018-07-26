function render() {
    var contactList = retrieveContactListFromServer();
	var normal = contactList.filter(function (item) { return item.attributes.forgotten !== "true"});
    var forgotten = contactList.filter(function (item) { return item.attributes.forgotten === "true"});
	renderContactList(normal, $('#contactTable'), false);
	renderContactList(forgotten, $('#forgotten_table'), true);

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
	    url: 'contacts',
	    dataType: 'json',
	    success: function(data) {contactList = data },
	    async: false
	});
	
	return contactList;
}

function Contact(attributes) {
    this.firstName = attributes.firstname===undefined?'':attributes.firstname;
    this.lastName = attributes.lastname===undefined?'':attributes.lastname;
    this.company = attributes.company===undefined?'':attributes.company;
    this.location = attributes.location===undefined?'':attributes.location;
    this.email = attributes.email===undefined?'':attributes.email;
    this.forgotten = attributes.forgotten===undefined?0:attributes.forgotten;
    this.consentId = attributes.consent_id===undefined?'':attributes.consent_id;
}

function createTableData(cssClasses, value) {
    var tableRow = $('<tr>');
    return '<td class="' + cssClasses + '" style="text-align: left; line-height: 200%;">' + value + '</td>';
}

function createButtonElement(buttonId, buttonName, clickEvent) {
    return '<button class="btn btn-default" id="' + buttonId + '" name="' + buttonName + '" onclick=\'' + clickEvent + '\'>' + buttonName +'</button>';
}

function renderContactList(json, selector, isForgotten)
{

    selector.html('');
	$.each(json, function(idx, item) {
        var contact = new Contact(item.attributes);
        var tableContent = [
          ['email-address', contact.email],
          ['', contact.firstName],
          ['', contact.lastName],
          ['company', contact.company],
          ['location', contact.location],
          ['consent_id', contact.consentId],
          ['', createButtonElement('edit_button', 'edit', 'showEditContactDetail(' + JSON.stringify(item) + ')')]
        ];
        if (!isForgotten) {
          tableContent.push(['', createButtonElement('forget_button', 'forget', 'forgetContact(' + JSON.stringify(contact) + ')')])
        }
        generateContactTableRow(selector, tableContent, contact.forgotten);
    })
}

function forgetContact(contact) {
    var contactEmail = "email=" + contact.email;
    $.ajax({
	    type: 'DELETE',
	    url: 'contacts?' + contactEmail,
	    success: function(data) {render();},
	    async: false
	});
}

function renderContactSelectionList(json, selector)
{
	selector.html('');
	$.each(json, function(idx, item) {
		var contact = new Contact(item.attributes);
        var tableContent = [
            ['email-checkbox', "<input type=\"checkbox\" onclick=\"whenContactIsSelected(" + idx + ")\" id=\"" + idx + "\" value=\"" + item.attributes.email + "\" />"],
            ['email-address', contact.email],
            ['contact-name', contact.firstName],
            ['contact-lname', contact.lastName],
            ['contact-cname', contact.company],
            ['contact-location', contact.location]
        ];
        generateContactTableRow(selector, tableContent, contact.forgotten);
	})
}

function generateContactTableRow(selector, content, isForgotten) {
    var style = isForgotten ? '  style="color: red" ' : '';
    var tr = $('<tr' + style + '>' );
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


function showEditContactDetail(item)
{
	openEditContactModal();
	insertDataIntoContactModal(item);
}

function insertDataIntoContactModal(item){

    var location = item.attributes.location;

    var positionOfslash = location.split("/");
    var country = positionOfslash[0];
    var city = positionOfslash[1];

	$('#name').val(item.attributes.firstname);
	$('#lastname').val(item.attributes.lastname);
	$('#company').val(item.attributes.company);
	$('#countrydrp').val(country);
	$('#city').val(city);
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
