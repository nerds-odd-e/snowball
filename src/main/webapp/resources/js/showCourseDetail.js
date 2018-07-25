$(document).ready(function() {
	var courseList = retrieveCourseListFromServer();
	renderCourseList(courseList, $('#courseTable'));
});

function renderCourseName(json, $el) {
    var data = JSON.parse(json);
    $('#courseName').text(data.courseName);
}
