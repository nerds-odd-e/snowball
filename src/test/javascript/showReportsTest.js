describe('test show report function', function() {

	var rootId = "testContainer";
	var markup = "<ul id='reportTable' class='list-inline' ></ul>";
	var mock_json = [{"course_count":2,"email":"darai0512@odd-e.com","location":"Tokyo, Japan","sent_at":1508901010}]

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

	it('should render empty report list when server JSON is empty',function() {
		var mock_json = [];

		renderReportList(mock_json, $("#reportTable"));
		expect($("#reportTable .col-md-11").length).toBe(mock_json.length);
	});

	it('should render complete report lists when server JSON contains some elements',function() {
		renderReportList(mock_json, $("#reportTable"));
		expect($("#reportTable .email").length).toBe(mock_json.length);

		$.each(mock_json, function(index, obj)
		{
			expect($("#reportTable .email").eq(index).text()).toBe(obj.email);
			expect($("#reportTable .location").eq(index).text()).toBe(obj.location);
			expect($("#reportTable .sent-at").eq(index).text()).toBe(unixTimeToDateString(obj.sent_at));
			expect(parseInt($("#reportTable .course-count").eq(index).text())).toBe(obj.course_count);
		})
	});
});
