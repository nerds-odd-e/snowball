function retrieveTrackingEmailListFromServer()
{
	var trackingList = [];

//	$.ajax({
//	    type: 'GET',
//	    url: 'tracking',
//	    dataType: 'json',
//	    success: function(data) {trackingList = data },
//	    async: false
//	});

	return trackingList;
}

function renderTrackingEmailList(json, selector)
{
	$.each(json, function(idx, item) {
		var subject = item.subject===undefined?'':item.subject;
		var date = item.date===undefined?'':item.date;
		selector.append('<tr><td class="subject">'+subject+'</td><td class="date">'+date+'</td></tr>');
	})
}