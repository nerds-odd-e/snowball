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

function renderEventList(json, selector) {
	$.each(json, function(idx, item) {
		selector.append(createListElement('title', 'title', getEventDetails(item.attributes.title, '')));
		selector.append(createListElement('content', 'content', getEventDetails(item.attributes.description, '&nbsp;')));
		selector.append(createListElement('location', 'location', getEventDetails(item.attributes.location, '&nbsp;')));
	})
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function getEventDetails(value, defaultValue) {
    return (value === undefined || value === '') ? defaultValue : value;
}

function createListElement(cssClass, id, value) {
    return '<li class="col-md-4 ' + cssClass + '" id="' + id + '" style="text-align: left">' + value + '</li>';
}

