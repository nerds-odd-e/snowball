function retrieveCourseDetailFromServer()
{
	var courseDetailData = {};
    var course_id = getUrlParameter('id');

	$.ajax({
	    type: 'GET',
	    url: 'course/detail/?id='+ course_id,
	    dataType: 'json',
	    success: function(data) {courseDetailData = data },
	    async: false
	});

	return courseDetailData;
}

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

function renderParticipantRows(json, selector)
{
	$.each(json.participants, function(idx, item) {
        selector.append(ParticipantRow(item).createRow());
	})
}

function ParticipantRow(participant) {

    return {
        createRow: function() {
            var tableRow = $('<tr class="participant">');
            var $emailElements = $('<td class="email" style="text-align: left; line-height: 200%;">' + participant.email + '</td>');
            var $nameElements = $('<td class="name" style="text-align: left; line-height: 200%;">' + participant.name + '</td>');
            tableRow.append($emailElements).append($nameElements);
            return tableRow;
        }
    }
}
