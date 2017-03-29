function retrieveEventListFromServer()
{
	var eventList = [];

	$.ajax({
	    type: 'GET',
	    url: 'events',
	    dataType: 'json',
	    success: function(data) {eventList = data },
	    async: false
	});

	return eventList;
}

function renderEventList(json, selector)
{
	$.each(json, function(idx, item) {
		var title = item.attributes.title===undefined?'':item.attributes.title;
		var content = (item.attributes.description===undefined || item.attributes.description==='')?'&nbsp;':item.attributes.description;
		var location = (item.attributes.location===undefined || item.attributes.location==='')?'&nbsp;':item.attributes.location;

		selector.append('<li class="col-md-4 title" id="title" style="text-align: left">'+title+'</li>');
		selector.append('<li class="col-md-4 content" id="content" style="text-align: left">'+content+'</li>');
		selector.append('<li class="col-md-4 location" id="location" style="text-align: left">'+location+'</li>');
	})
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

