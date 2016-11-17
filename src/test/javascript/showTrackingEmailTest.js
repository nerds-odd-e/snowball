describe('test show tracking function', function() {

	var rootId = "testContainer";
	var markup = "<table id='trackingTable'><tbody></tbody></table>";
	var mock_json = [{"notificationId":1,"subject":"test","sentDate":"2016-11-14"},{"notificationId":2,"subject":"test2","sentDate":"2016-11-14"}];

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
            expect($("#trackingTable .sentDate").eq(idx).text()).toBe(item.sentDate);
		});
	});

});
