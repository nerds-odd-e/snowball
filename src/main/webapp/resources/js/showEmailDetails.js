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
	selector.append(new NotificationSummary(json).createRow());
}


function NotificationSummary(item)
{
    this.getString = function(attr) {
        return attr===undefined?'':attr;
    };
    this.createRow = function() {
        return '<tr><td class="subject">'+this.subject+'</td><td class="sent_at">'+this.sent_at+'</td><td class="total_open_count">'+this.total_open_count+'</td></tr></a>'
    }
    this.subject=this.getString(item.subject);
    this.sent_at=this.getString(item.sent_at);
    this.total_open_count=this.getString(item.total_open_count);
}

function renderEmailDetailsList(json, selector)
{
	$.each(json.emails, function(idx, item) {
        selector.append(new NotificationDetail(item).createRow());
	})
}

function NotificationDetail(item)
{
    this.getString = function(attr) {
        return attr===undefined?'':attr;
    };
    this.createRow = function() {
        return '<tr"><td class="email">'+this.email+'</td><td class="open_count">'+this.open_count+'</td></tr></a>';
    }
    this.email=this.getString(item.email);
    this.open_count=this.getString(item.open_count);
}