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
		var sent_at = item.sent_at===undefined?'':item.sent_at;
		var notification_id = item.notification_id===undefined?'':item.notification_id;
		selector.append('<tr class="clickable-row" data-href="email_tracking_details.jsp?notification_id='+notification_id+'"><td class="subject">'+subject+'</td><td class="sent_at">'+sent_at+'</td></tr></a>');
	})
}