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
/**
  private Long notificationId;
    private Date sentDate;

*/
function renderTrackingEmailList(json, selector)
{
	$.each(json, function(idx, item) {
		var subject = item.subject===undefined?'':item.subject;
		var sentDate = item.sentDate===undefined?'':item.sentDate;
		var notificationId = item.notificationId===undefined?'':item.notificationId;
		selector.append('<tr class="clickable-row" data-href="email_tracking_details.jsp?notification_id='+notificationId+'"><td class="subject">'+subject+'</td><td class="sentDate">'+sentDate+'</td></tr></a>');
	})
}