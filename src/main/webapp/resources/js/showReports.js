$(document).ready(function() {
	var reportList = retrievereportListFromServer();
	renderreportList(reportList, $('#reportTable'));
});

function retrievereportListFromServer()
{
	var reportList = [];
	
	$.ajax({
	    type: 'GET',
	    url: 'reportlist',
	    dataType: 'json',
	    success: function(data) { reportList = data },
	    async: false
	});
	return reportList;
}

function renderreportList(json, selector)
{
}
