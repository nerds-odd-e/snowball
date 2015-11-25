describe('test show contact function', function() {

	
	var rootId = "testContainer";
	var markup = "<ul id='contactTable' class='list-inline' ></ul>";
	var mock_json = [{"id":1,"attributes":{"Company":"","Email":"a@a.com","FirstName":"Tom","LastName":"Jerry"}},{"id":2,"attributes":{"Company":"","Email":"b@b.com","FirstName":"Jerry","LastName":"Tom"}}];
	
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
	
	it('should render empty contact list when server JSON is empty',function() {

		var mock_json = [];
		
		renderContactList(mock_json, $("#contactTable"));
		expect($("#contactTable .col-md-11").length).toBe(mock_json.length);
	});
	

	it('should render complete contact lists when server JSON contains some elements',function() {

		renderContactList(mock_json, $("#contactTable"));
		expect($("#contactTable .email-address").length).toBe(mock_json.length);

		
		
		$.each(mock_json, function(index, obj)
		{
			expect($("#contactTable .email-address").eq(index).text()).toBe(obj.attributes.Email);
			expect($("#contactTable .company").eq(index).text()).toBe(obj.attributes.Company);
		})	

	});
	
	it('should render contact list with checkbox and number of checkbox must be equal to the number of contacts', function(){
		renderContactSelectionList(mock_json, $('#contactTable'));
		expect($("#contactTable .email-checkbox").length).toBe(mock_json.length);
	});
});