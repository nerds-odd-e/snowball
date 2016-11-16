function retrieveEmailDetailsListFromServer()
{
	var emailDetailsList = [];

	$.ajax({
	    type: 'GET',
	    url: 'listEmailDetails',
	    dataType: 'json',
	    success: function(data) {emailDetailsList = data },
	    async: false
	});

	return emailDetailsList;
}

function renderEmailDetailsSummary(json, selector)
{
    var item = json;
    var subject = item.subject===undefined?'':item.subject;
	var sent_at = item.sent_at===undefined?'':item.sent_at;
	var total_open_count = item.total_open_count===undefined?'':item.total_open_count;
	selector.append('<tr><td class="subject">'+subject+'</td><td class="sent_at">'+sent_at+'</td><td class="total_open_count">'+total_open_count+'</td></tr></a>');
}

function renderEmailDetailsList(json, selector)
{
	$.each(json.emails, function(idx, item) {
		var email = item.email===undefined?'':item.email;
		var open_count = item.open_count===undefined?'':item.open_count;
		selector.append('<tr"><td class="email">'+email+'</td><td class="open_count">'+open_count+'</td></tr></a>');
	})
}