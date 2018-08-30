function render() {
  var questionList = retrieveQuestionListFromServer();
	renderQuestionList(questionList, $('#questionTables'));

}

$(document).ready(function() {
	render();
});

function retrieveQuestionListFromServer()
{
	var questionList = {
	'question1':
	  {
	   'description':'what is 1+1?',
	   'options':{
	     'option1':'must be 1!0',
         'option2':'of course 2.',
         'option3':'must be 3!',
         'option4':'of course 4.',
         'option5':'of course 5.'
        },
       'advice':'you should read a math book',
       'correct_answer': 'option2'
	  }
	};

	return questionList;
}

function renderQuestionList(json, selector)
{

    selector.html('');
	$.each(json, function(idx, item) {
        var question = new Question(item);
        var tables = generateQuestionTables(question);
        selector.append(tables);
    })
}

function generateQuestionTables(question) {
    var tables = '<div class="panel panel-default">'
                 + '<table class="table table-responsive table-bordered">'
                 + '<thead>'
                 + '<th>' + question.description + '</th>';
                 + '</thead><tbody id="questionTable"><tr>';

}

function Question(attributes) {
    this.description = attributes.description===undefined?'':attributes.description;
    this.options = attributes.options===undefined?'':attributes.options;
    this.advice = attributes.advice===undefined?'':attributes.advice;
    this.correct_answer = attributes.correct_answer===undefined?'':attributes.correct_answer;
}

function createTableData(value) {
    return '<div class="panel panel-default">'
           +'<table class="table table-responsive table-bordered"><thead><th>Question 1aeaea</th></thead></table>';




}

function createButtonElement(buttonId, buttonName, clickEvent) {
    return '<button class="btn btn-default" id="' + buttonId + '" name="' + buttonName + '" onclick=\'' + clickEvent + '\'>' + buttonName +'</button>';
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
        generateContactTableRow(selector, tableContent);
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
