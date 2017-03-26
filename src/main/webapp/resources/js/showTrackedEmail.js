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
		selector.append(new Notification(item).createRow());
	})
}

function Notification(item) {
    this.getString = function(attr) {
        return attr===undefined?'':attr;
    };

    this.createRow = function() {
        return '<tr class="clickable-row" data-href="email_tracking_details.jsp?notification_id='+this.notificationId+'"><td class="subject">'+this.subject+'</td><td class="sentDate">'+this.sentDate+'</td></tr></a>';
    }

    this.subject = this.getString(item.attributes.subject);
    this.sentDate = this.getString(item.attributes.sent_at);
    this.notificationId = this.getString(item.attributes.notification_id);
}