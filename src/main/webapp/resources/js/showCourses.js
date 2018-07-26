$(document).ready(function() {
	var courseList = retrieveCourseListFromServer();
	renderCourseList(courseList, $('#courseTable'));
});

function retrieveCourseListFromServer()
{
	var courseList = [];
	
	$.ajax({
	    type: 'GET',
	    url: 'courses',
	    dataType: 'json',
	    success: function(data) {courseList = data },
	    async: false
	});
	return courseList;
}

function sendEmail(courseId, actionId)
{
	var status = null;
    var action = (actionId == 1)?'send':'preview';

	$.ajax({
	    type: 'POST',
	    url: 'previewemail?courseId=' + courseId + '&action=' + action,
	    dataType: 'text',
	    success: function(data) { $("#div12").html(data); },
	    async: false
	});

	return status;
}

function Course(attributes) {
    this.id = attributes.id===undefined?'':attributes.id;
    this.coursename = attributes.coursename===undefined?'':attributes.coursename;
    this.duration = attributes.duration===undefined?'':attributes.duration;
    this.location = attributes.location===undefined?'':attributes.location;
    this.startdate = attributes.startdate===undefined?'':attributes.startdate;
    this.instructor = attributes.instructor===undefined?'':attributes.instructor;
}

function createTableData(cssClasses, value, courseId) {
    var tableRow = $('<tr>');
    if (courseId) {
        return '<td class="' + cssClasses + '" style="text-align: left; line-height: 200%;"><a href="course_detail.jsp?id=' + courseId +'" id="course_detail_link_'+ courseId +'">' + value + '</a></td>';
    }
    return '<td class="' + cssClasses + '" style="text-align: left; line-height: 200%;">' + value + '</td>';
}

function createButtonElement(buttonId, buttonName, clickEvent) {
    return '<button class="btn btn-default" id="' + buttonId + '" name="' + buttonName + '" onclick=\'' + clickEvent + '\'>' + buttonName +'</button>';
}

function renderCourseList(json, selector)
{
    selector.html('');
	$.each(json, function(idx, item) {
        var course = new Course(item.attributes);
	    var sendEvent = 'sendEmail(' + course.id + ', 1 )';
	    var previewEvent = 'sendEmail(' + course.id + ', 2 )';
        var tableContent = [
          ['course name', course.id + ' - ' + course.coursename, course.id],
          ['duration', course.duration, null],
          ['location', course.location, null],
          ['startdate', course.startdate, null],
          ['instructor', course.instructor, null],
          ['', createButtonElement('send_button', 'precourse ' + course.coursename, sendEvent), null],
          ['', createButtonElement('preview_button', 'preview ' + course.coursename, previewEvent), null],
        ];
        generateTableRow(selector, tableContent);
    })
}

function generateTableRow(selector, content) {
    var tr = $('<tr>');
    selector.append(tr);
    content.forEach(function(element) {
        tr.append(createTableData(element[0], element[1], element[2]));
    });
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}