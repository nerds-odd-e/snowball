$(document).ready(function() {
//alert('1');
	var courseList = retrieveCourseListFromServer();
	renderCourseList(courseList, $('#courseTable'));

	$("#send_button").click(function() {
		sendEmailToCourseParticipant();
	});

	$("#preview_button").click(function() {
    		sendEmailToAdmin();
    });
});

function retrieveCourseListFromServer()
{
	var courseList = [];
	
	$.ajax({
	    type: 'GET',
	    url: 'courselist',
	    dataType: 'json',
	    success: function(data) {courseList = data },
	    async: false
	});
	//alert('2');
	//alert(courseList);
	//alert('3')
	return courseList;
}

function Course(attributes) {
    this.id = attributes.id===undefined?'':attributes.id;
    this.coursename = attributes.coursename===undefined?'':attributes.coursename;
    this.duration = attributes.duration===undefined?'':attributes.duration;
    this.location = attributes.location===undefined?'':attributes.location;
    this.startdate = attributes.startdate===undefined?'':attributes.startdate;
    this.instructor = attributes.instructor===undefined?'':attributes.instructor;
}

function createTableData(cssClasses, value) {
    var tableRow = $('<tr>');
    return '<td class="' + cssClasses + '" style="text-align: left; line-height: 200%;">' + value + '</td>';
}

function createButtonElement(buttonId, buttonName, clickEvent) {
    return '<button class="btn btn-default" id="' + buttonId + '" name="' + buttonName + '" onclick=\'' + clickEvent + '\'>' + buttonName +'</button>';
}
'aLERT(\'SEND\')'
function renderCourseList(json, selector)
{
    selector.html('');
	$.each(json, function(idx, item) {
	    var sendEvent = 'alert(\'Send\')';
        var course = new Course(item.attributes);
        var tableContent = [
          ['course name', course.id + ' - ' + course.coursename],
          ['duration', course.duration],
          ['location', course.location],
          ['startdate', course.startdate],
          ['instructor', course.instructor],
          ['', createButtonElement('send_button', 'Send Pre-Course Email', sendEvent)],
          ['', createButtonElement('preview_button', 'Preview Pre-Course Email', sendEvent)],
        ];
        generateTableRow(selector, tableContent);
    })
}

function generateTableRow(selector, content) {
    var tr = $('<tr>');
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