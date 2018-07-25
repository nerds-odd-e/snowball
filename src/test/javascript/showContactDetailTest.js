describe('test show contact function', function() {

	var rootId = "testContainer";
	var markup = '<h1 class="page-header">Course Detail <span id="courseName"></span></h1>';

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

	it('should render course name',function() {

        var mock_json = '{"courseName": "CSD Tokyo"}';

		renderCourseName(mock_json, $('#courseName'));
		expect($("#courseName").text()).toBe('CSD Tokyo');
	});
});