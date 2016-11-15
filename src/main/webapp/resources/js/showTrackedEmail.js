function retrieveTrackingEmailListFromServer()
{
	var trackingList = [];

	$.ajax({
	    type: 'GET',
	    url: 'listEmails',
	    dataType: 'json',
	    success: function(data) {trackingList = data },
	    async: false
	});

	return trackingList;
}

function renderTrackingEmailList(json, selector)
{
	$.each(json, function(idx, item) {
		var subject = item.subject===undefined?'':item.subject;
		var sentdate = item.sentdate===undefined?'':item.sentdate;
		selector.append('<tr><td class="subject">'+subject+'</td><td class="sentdate">'+sentdate+'</td></tr>');
	})
}