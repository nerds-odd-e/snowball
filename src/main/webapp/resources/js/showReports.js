$(document).ready(function() {
	var reportList = retrievereportListFromServer();
	renderReportList(reportList, $('#reportTable'));
});

function retrievereportListFromServer()
{
	var reportList = '';

	$.ajax({
	    type: 'GET',
	    url: 'reportlist',
	    dataType: 'json',
	    success: function(data) { reportList = data },
	    async: false
	});
	return reportList;
}

function renderReportList(content, selector) {
    var tr = $('<tr>');
    selector.append(tr);
    if (!content) {
        return;
    }
    content.forEach(function(element) {
        tr.append('<td class="email" style="text-align: left; line-height: 200%;">' + element.email + '</td>');
        tr.append('<td class="location" style="text-align: left; line-height: 200%;">' + element.location + '</td>');
        tr.append('<td class="sent-at" style="text-align: left; line-height: 200%;">' + unixTimeToDateString(element.sent_at) + '</td>');
        tr.append('<td class="course-count" style="text-align: left; line-height: 200%;">' + element.course_count + '</td>');
    });
}

function unixTimeToDateString(unixtime) {
    return new Date(unixtime*1000).toLocaleString();
}
