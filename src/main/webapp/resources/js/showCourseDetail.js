$(document).ready(function() {
	var courseDetail = retrieveCourseDetailFromServer();
	renderCourseName(courseDetail, $('#courseName'));
});

function renderCourseName(data, $el) {
    if (!data) return;
    $el.text(data.courseName);
}

function retrieveCourseDetailFromServer() {
    var courseDetail = {};
    var url = new URL(window.location.href)
    if (!url.searchParams) {
        return;
    }

  	$.ajax({
  	    type: 'GET',
  	    url: 'course/detail?id=' + url.searchParams.get('id'),
  	    dataType: 'json',
  	    success: function(data) { courseDetail = data },
  	    async: false
  	});
  	return courseDetail;
}
