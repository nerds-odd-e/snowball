$(document).ready(function() {
	var countryList = retrieveCountryListFromServer();
    populateCountryListToDropdown(countryList);

});


function retrieveCountryListFromServer()
{
	var countryList = [];

	$.ajax({
	    type: 'GET',
	    url: '/countries',
	    dataType: 'json',
	    success: function(data) { countryList = data },
	    async: false
	});

	return countryList;
}

function populateCountryListToDropdown(data) {

    $('#countrydrp').empty();
    $.each(data, function(key, value) {
        $('#countrydrp').append($('<option></option>').val(value).html(value));
    });

}