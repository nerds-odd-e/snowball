
var addEvents = {
    allNotNullElementIds: ['#courseId', '#participantIdHidden'],
    allOptionalElementIds: []
};

$(document).ready(function() {

	disableAddButton();

    populateCourses($("#courseId").val());

    $("#courses").change(function() {
        $("#courseId").val($("#courses").val());
        checkAddInputElement1('#email');
    });

	$("#email").keyup(function () {
	    checkAddInputElement1('#email');
	});

	$("#add_button").click(function () {
	    if ($("#courseId").val().length == 0) {
            $('#saveStatusLabel').text("Warning");
            $('#saveStatusBody').text("No course selected/specified.");
            $('#saveStatusModal').modal();
	    } else {
            var participantId = addContactToCourse();
            if (participantId != null) {
                document.forms[0].participantIdHidden.value = participantId;
                document.forms[0].action='courseparticipants';
                document.forms[0].method='post';
                submitForm();
            }
    	}
    });

	var status = getUrlParameter('status'),
	    msg = getUrlParameter('msg');

    if(status && msg){
        $('#saveStatusLabel').text(status);
        $('#saveStatusBody').text(msg);
        $('#saveStatusModal').modal();
    }

});

function populateCourses(courseId)
{
	$.ajax({
	    type: 'GET',
	    url: 'courses',
	    dataType: 'json',
	    success: function(data) {
	        $("#courses").children().remove();
	        $('#courses').append('<option value="">Select the course</option>');
	        $.each(data, function(idx, item) {
	                var selected = " ";
	                var _course = new Course(item.attributes);
	                if (_course.id === courseId) {selected = " selected=true";}
                    $('#courses').append('<option value=' + _course.id + selected +'>' + _course.courseName + '</option>');
                 });
	     },
	    async: false
	});
}

function Course(attributes) {
    this.id = attributes.id===undefined?'':attributes.id;
    this.courseName = attributes.courseName===undefined?'':attributes.courseName;
    this.duration = attributes.duration===undefined?'':attributes.duration;
    this.location = attributes.location===undefined?'':attributes.location;
    this.startDate = attributes.startDate===undefined?'':attributes.startDate;
    this.instructor = attributes.instructor===undefined?'':attributes.instructor;
}

function addContactToCourse() {
    var email = $("#email").val().trim();
    var courseId = $("#courseId").val().trim();
    var contactId;
    var contacts;

    try {
       contacts = fetchContactFromServer(email);
       contactId = getContactId(email, contacts);
       return contactId;
       /*if (contactId != null) {
           postToServer(courseId, contactId);
           $('#saveStatusLabel').text("Message");
           $('#saveStatusBody').text("Participant " + email + " successfully registered to selected course.");
           $('#saveStatusModal').modal();
       }*/
    }
    catch (e) {
         $('#saveStatusLabel').text("Warning");
         $('#saveStatusBody').text(e);
         $('#saveStatusModal').modal();
    }
}

function getContactId(email, contactList) {
    var contactId = null;
	$.each(contactList, function(idx, item) {
        var contact = new Contact(item);
        if (email == contact.email) {
            contactId = contact.id;
        }
     });
    if (contactId === null) {
	    throw 'No Contact Found for : ' + email;
	} else {
	    return contactId;
	}
}

function fetchContactFromServer(email) {
    var contactList = [];
	$.ajax({
	    type: 'GET',
	    url: 'contacts?email='+email,
	    dataType: 'json',
	    success: function(data) {contactList = data},
	    async: false
	});
	return contactList;
}

function postToServer(courseId, contactId) {
    var response = null;
    var formData = "courseId=" + courseId + "&participantIdHidden="+contactId;
	$.ajax({
	    type: 'POST',
	    url: 'courseparticipants',
	    data: formData,
	    success: function(data1) {response = data1},
	    error: function(data2) { response = "error"}
	});
	if (response === "error") {
	    throw "Participant registration failed..";
	}
	return response;
}

function trimElemVal(elementId){
	$(elementId).val($(elementId).val().trim());
}

function submitForm() {
	document.forms[0].submit();
}

function checkAddInputElement1(domId) {
	if (isChange($(domId).val())) {
		enableAddButton();
	} else {
		disableAddButton();
	}
}

function enableAddButton() {
	$("#add_button").removeAttr('disabled');
}

function disableAddButton() {
	$("#add_button").attr('disabled', 'disabled');
}

function Contact(attributes) {
    this.id = attributes.id===undefined?'':attributes.id;
    this.firstName = attributes.firstName===undefined?'':attributes.firstName;
    this.lastName = attributes.LastName===undefined?'':attributes.lastName;
    this.email = attributes.email===undefined?'':attributes.email;
    this.location = attributes.location===undefined?'':attributes.location;
    this.company = attributes.company===undefined?'':attributes.company;
}

function isChange(value) {
	return value.replace(/^\s+|\s+$/gm,'') !== "";
}

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

