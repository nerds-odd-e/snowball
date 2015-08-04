/**
 * 
 */
$(document).ready(function() {
	$.getJSON("showContact", function(json) {
		var $contactTable = $('#contactTable');
		$.each(json, function(idx, item) {
			$contactTable.append('<li class="col-md-1" style="text-align: center">'+item.id+'</li> <li class="col-md-11" style="text-align: center">'+item.name+'</li>');
		})
	});
});