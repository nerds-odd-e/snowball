function retrieveTemplateListFromServer()
{
	var templateList = [];

	$.ajax({
	    type: 'GET',
	    url: 'templates',
	    dataType: 'json',
	    success: function(data) {templateList = data },
	    async: false
	});

	return templateList;
}