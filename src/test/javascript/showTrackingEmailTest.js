describe('test show tracking function', function() {

	var rootId = "testContainer";
	var markup = "<table id='trackingTable'><tbody></tbody></table>";
	var mock_json = [{"subject":"test","date":"2016-11-14"},{"subject":"test2","date":"2016-11-14"}];

	beforeEach(function(){
		var container = document.createElement('div');
		container.setAttribute('id', rootId);
		document.body.appendChild(container);
		container.innerHTML = markup;
	});

	afterEach(function() {
		var container = document.getElementById(rootId);
		container.parentNode.removeChild(container);
	});

	it('should render email contains tracking list',function() {
		renderTrackingEmailList(mock_json, $("#trackingTable tbody"));

	    $.each(mock_json, function(idx, item) {
		    expect($("#trackingTable .subject").eq(idx).text()).toBe(item.subject);
            expect($("#trackingTable .date").eq(idx).text()).toBe(item.date);
		});
	});

});
