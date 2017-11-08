describe('test show course function', function() {

	
	var rootId = "testContainer";
	var markup = "<ul id='courseTable' class='list-inline' ></ul>";
	var mock_json = [{"id":1, "attributes":{"title":"TestTitle1","description":"TestContent1", "location" : "Singapore"}},{"id":2,"attributes":{"title":"TestTitle2","description":"TestContent2", "location" : "Singapore"}}];
	
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
	
	it('should render empty course list when server JSON is empty',function() {

		var mock_json = [];
		
		renderCourseList(mock_json, $("#courseTable"));
		expect($("#courseTable .col-md-12").length).toBe(mock_json.length);
	});
	

	it('should render complete course lists when server JSON contains some elements',function() {

		renderCourseList(mock_json, $('#courseTable'));
		expect($("#courseTable .title").length).toBe(mock_json.length);
		$.each(mock_json, function(index, obj)
		{
			expect($("#courseTable .title").eq(index).text()).toBe(obj.attributes.title);
			expect($("#courseTable .content").eq(index).text()).toBe(obj.attributes.description);
			expect($("#courseTable .location").eq(index).text()).toBe(obj.attributes.location);
		})	
	});
	
});