describe('test show tracking function', function() {

	var rootId = "testContainer";
	var markup = "<table id='trackingTable'><tbody></tbody></table>";
	var firstNotification = {"notificationId":1,"subject":"test","sentDate":"2016-11-14"};
	var mock_json = [ firstNotification ,{"notificationId":2,"subject":"test2","sentDate":"2016-11-14"}];

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

	it('should render empty string for missing attributes',function() {
	    delete firstNotification.subject;
		renderTrackingEmailList(mock_json, $("#trackingTable tbody"));

        expect($("#trackingTable .subject").eq(0).text()).toBe("");
        expect($("#trackingTable .sentDate").eq(0).text()).toBe(mock_json[0].sentDate);
	});

	describe("Notifiction", function() {
	    it('should return corret table row', function() {
	        var row = new Notification(firstNotification).createRow();
	        expect(row.indexOf("notification_id="+firstNotification.notificationId) > 0).toBe(true);
	    });
	});

});
