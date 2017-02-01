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
		var content = item.attributes.content===undefined?'':item.attributes.content;
		selector.append('<li class="col-md-3" style="text-align: left">'+event+'</li>');
		selector.append('<li class="col-md-2" style="text-align: left">'+content+'</li>');
	})
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}


