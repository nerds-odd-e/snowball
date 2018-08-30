describe('test question list function', function() {


	var rootId = "testContainer";
	var markup = "<ul id='questionTable' class='list-inline' ></ul>";
	var mock_json = [{"id":1,"attributes":{"company":"odde","email":"a@a.com","firstname":"Tom","lastname":"Jerry","location":"Singapore"}},{"id":2,"attributes":{"company":"odde2","email":"b@b.com","firstname":"Jerry","lastname":"Tom","location":"Singapore"}}];

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

	it('should render empty question list when server JSON is empty',function() {

		var mock_json = [];

		renderQuestionList(mock_json, $("#questionTables"));
		expect($("#questionTables").length).toBe(mock_json.length);
	});
});
