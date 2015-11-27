describe('test show template function', function() {

	
	var rootId = "testContainer";
	var markup = "<select name='templateList' id='templateList'></select>";
	var mock_json = [{"Id":1,"TemplateName":"Default Template 1","Subject":"Greeting {FirstName}","Content":"Hi, {FirstName} {LastName} from {Company}"},{"Id":2,"TemplateName":"Greeting Template","Subject":"Greeting {FirstName}","Content":"Hi, {FirstName} {LastName} from {Company}"}];
	
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
	
	it('should render only no template when server JSON is empty',function() {

		var mock_json = [];
		
		renderTemplateList(mock_json, $("#templateList"));
		expect(document.getElementById("templateList").options.length).toBe(1);
		
	});
	

	it('should render template lists when server JSON contains some elements',function() {

		var selectObject = document.getElementById("templateList");

		renderTemplateList(mock_json, $("#templateList"));
		expect(selectObject.options.length).toBe(mock_json.length + 1);

		expect(selectObject.options[0].value).toBe("0");
		expect(selectObject.options[0].text).toBe("No Template");

		$.each(mock_json, function(index, obj)
		{
			expect(selectObject.options[index+1].value).toBe(obj.Id.toString());
			expect(selectObject.options[index+1].text).toBe(obj.TemplateName);
		})	

	});
});