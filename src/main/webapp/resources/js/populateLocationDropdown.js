$(document).ready(function() {
	var locationList = retrieveLocationListFromServer();
    populateLocationListToDropdown(locationList);

});


function retrieveLocationListFromServer()
{
	var locationList = [];

	$.ajax({
	    type: 'GET',
	    url: 'locations',
	    dataType: 'json',
	    success: function(data) { locationList = data },
	    async: false
	});

	return locationList;
}

function populateLocationListToDropdown(data) {

    $('#locationdrp').empty();
    $.each(data, function(key, value) {
        $('#locationdrp').append($('<option></option>').val(value).html(value));
    });

}