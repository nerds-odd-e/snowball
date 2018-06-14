describe('test show tracking function', function() {

	var rootId = "testContainer";
	var markup = "<table id='trackingTable'><tbody></tbody></table>";
	var firstNotification = {"attributes":{"sent_mail_id":1,"subject":"test","sent_at":"2016-11-14"}};
	var mock_json = [ firstNotification ,{"attributes":{"sent_mail_id":2,"subject":"test2","sent_at":"2016-11-14"}}];

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

	it('should render empty string for missing attributes',function() {
	    delete firstNotification.attributes.subject;
		renderTrackingEmailList(mock_json, $("#trackingTable tbody"));

        expect($("#trackingTable .subject").eq(0).text()).toBe("");
        expect($("#trackingTable .sentDate").eq(0).text()).toBe(mock_json[0].attributes.sent_at);
	});

	describe("Notifiction", function() {
	    it('should return corret table row', function() {
	        var row = new Notification(firstNotification).createRow();
	        expect(row.indexOf("sent_mail_id="+firstNotification.attributes.sent_mail_id) > 0).toBe(true);
	    });
	});

});
