$(document).ready(function() {
	var courseDetail = retrieveCourseDetailFromServer();
	renderCourseName(courseDetail, $('#courseTable'));
});

function renderCourseName(data, $el) {
    $el.text(data.courseName);
}

function retrieveCourseDetailFromServer() {
    var courseDetail = {};

  	$.ajax({
  	    type: 'GET',
  	    url: 'course/detail?id=' + new URL(window.location.href).searchParams.get('id'),
  	    dataType: 'json',
  	    success: function(data) { courseDetail = data },
  	    async: false
  	});
  	return courseDetail;
}
