describe('test show contact function', function() {

	
	var rootId = "testContainer";
	var markup = "<ul id='contactTable' class='list-inline' ></ul>";
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
				renderContactList(mock_json);
				expect($("#contactTable .col-md-1").length).toBe(mock_json.length);
			});
	

	it('should render complete contact lists when server JSON contains some elements',function() {

		var mock_json = [{email:"a@a.com", id:1, name:"Tom"},{email:"b@b.com", id:2, name:"Jerry"}];
		renderContactList(mock_json);
		expect($("#contactTable .col-md-1").length).toBe(mock_json.length);

		$.each(mock_json, function(index, obj)
		{
			expect($("#contactTable li").eq((index*2)).text()).toBe(obj.id+"");
			expect($("#contactTable li").eq((index*2)+1).text()).toBe(obj.email);
		})
		

	});
});