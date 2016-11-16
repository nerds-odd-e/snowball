describe('test show email details function', function() {

	var rootId = "testContainer";
	var markup = "<table id='summarySection'><tbody></tbody></table><table id='listTable'><tbody></tbody></table>";
	var mock_json = {"subject":"Hello!", "sent_at": "2016-11-16", "total_open_count": 8, "emails": [{"email": "test@t","open_count": 5}, {"email": "test2@t","open_count": 3}]};

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

	it('should render email summary',function() {
		renderEmailDetailsSummary(mock_json, $("#summarySection tbody"));

	    expect($("#summarySection .subject").eq(0).text()).toBe(mock_json.subject);
	    expect($("#summarySection .sent_at").eq(0).text()).toBe(mock_json.sent_at);
	    total_open_count = isNaN($("#summarySection .total_open_count").eq(0).text()) ? 0 : Number($("#summarySection .total_open_count").eq(0).text());
	    expect(total_open_count).toBe(Number(mock_json.total_open_count));
	});

	it('should render email details',function() {
        renderEmailDetailsList(mock_json, $("#listTable tbody"));

        $.each(mock_json.emails, function(idx, item) {
            expect($("#listTable .email").eq(idx).text()).toBe(item.email);
            open_count = isNaN($("#listTable .open_count").eq(idx).text()) ? 0 : Number($("#listTable .open_count").eq(idx).text());
            expect(open_count).toBe(Number(item.open_count));
        })
    });

});
