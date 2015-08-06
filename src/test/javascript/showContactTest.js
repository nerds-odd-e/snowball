describe('test show contact function', function() {

	
	var rootId = "testContainer";
	var markup = "<ul id='contactTable' class='list-inline' ></ul>";
	var mock_json = [{email:"a@a.com", id:1, name:"Tom"},{email:"b@b.com", id:2, name:"Jerry"}];
	
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
		expect($("#contactTable .col-md-11").length).toBe(mock_json.length);

		$.each(mock_json, function(index, obj)
		{
			expect($("#contactTable li").eq(index).text()).toBe(obj.email);
		})	

	});
	
//	it('should render contact list with checkbox and number of checkbox must be equal to the number of contacts', function(){
//		renderContactSelectionList(mock_json, $("#contactTable"));
//		expect($("#contactTable .email-checkbox").length).toBe(mock_json.length);
//	});
});