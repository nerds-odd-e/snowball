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
		selector.append(new Notification(item).createRow());
	})
}

function Notification(item) {
    this.getString = function(attr) {
        return attr===undefined?'':attr;
    };

    this.createRow = function() {
        return '<tr class="clickable-row" data-href="email_tracking_details.jsp?sent_mail_id='+this.sentMailId+'"><td class="subject">'+this.subject+'</td><td class="sentDate">'+this.sentDate+'</td></tr></a>';
    }

    this.subject = this.getString(item.attributes.subject);
    this.sentDate = this.getString(item.attributes.sent_at);
    this.sentMailId = this.getString(item.attributes.sent_mail_id);
}