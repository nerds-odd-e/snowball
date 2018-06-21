describe('test show contact function', function() {

	
	var rootId = "testContainer";
	var markup = "<ul id='contactTable' class='list-inline' ></ul><ul id='forgotten_table' class='list-inline' ></ul>";
	var mock_json = [{"id":1,"attributes":{"company":"odde","email":"a@a.com","firstname":"Tom","lastname":"Jerry","location":"Singapore"}},{"id":2,"attributes":{"company":"odde2","email":"b@b.com","firstname":"Jerry","lastname":"Tom","location":"Singapore"}}];
	var mock_json_forgotten = [{"id":1,"attributes":{"company":"odde","email":"a@a.com","firstname":"Tom","lastname":"Jerry","location":"Singapore", "forgotten": 0}},{"id":2,"attributes":{"company":"odde2","email":"b@b.com","firstname":"Jerry","lastname":"Tom","location":"Singapore", "forgotten": 1}}];
	
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
			expect($("#contactTable .email-address").eq(index).text()).toBe(obj.attributes.email);
			expect($("#contactTable .company").eq(index).text()).toBe(obj.attributes.company);
			expect($("#contactTable .location").eq(index).text()).toBe(obj.attributes.location);

		})	

	});




	it('should render contact lists forgotten and normal separately when server JSON contains some elements',function() {
            var normal = mock_json_forgotten.filter(function (item) { return item.attributes.forgotten == 0 });
            var forgotten = mock_json_forgotten.filter(function (item) { return item.attributes.forgotten == 1 });
    		renderContactList(normal, $("#contactTable"));
    		renderContactList(forgotten, $("#forgotten_table"));
    		expect($("#contactTable .email-address").length).toBe(1);
    		expect($("#forgotten_table .email-address").length).toBe(1);
            expect($("#forgotten_table .email-address").css('color')).toBe('rgb(255, 0, 0)');

    	});
	
	it('should render contact list with checkbox and number of checkbox must be equal to the number of contacts', function(){
		renderContactSelectionList(mock_json, $('#contactTable'));
		expect($("#contactTable .email-checkbox").length).toBe(mock_json.length);
	});
});