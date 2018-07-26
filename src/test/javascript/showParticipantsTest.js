describe('test show email details function', function() {

	var rootId = "testContainer";
	var markup = "<table id='summarySection'><tbody></tbody></table><table id='listTable'><tbody></tbody></table>";
	var firstSentMailVisit = {"email": "test@t","open_count": 5};

	var mock_json = {
	    courseName: "CSD Tokyo",
	    participants: [
            {
                email: "tom@example.com",
                name: "tom"
            }
	    ]
	};

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

	it('should render participant rows',function() {
        renderParticipantRows(mock_json, $("#listTable tbody"));

        $.each(mock_json.participants, function(idx, item) {
            expect($("#listTable .email").eq(idx).text()).toBe(item.email);
            expect($("#listTable .name").eq(idx).text()).toBe(item.name);
        });
    });
});
