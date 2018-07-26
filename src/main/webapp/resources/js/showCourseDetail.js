$(document).ready(function() {
	var courseDetail = retrieveCourseDetailFromServer();
	renderCourseName(courseDetail, $('#courseTable'));
});

function renderCourseName(data, $el) {
    if (!data) return;
    $el.text(data.courseName);
}

function retrieveCourseDetailFromServer() {
    var courseDetail = {};

    var params = new URL(window.location.href).searchParams;
    if (!params || !params.length) {
        return;
    }
  	$.ajax({
  	    type: 'GET',
  	    url: 'course/detail?id=' + new URL(window.location.href).searchParams.get('id'),
  	    dataType: 'json',
  	    success: function(data) { courseDetail = data },
  	    async: false
  	});
  	return courseDetail;
}
