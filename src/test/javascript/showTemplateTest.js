var mock_json = [{"id":1, "attributes":{"templateName":"Default Template 1","subject":"Greeting {FirstName}","content":"Hi, {FirstName} {LastName} from {Company}"}},{"id":2, "attributes":{"templateName":"Greeting Template","subject":"Greeting {LastName}","content":"Hello, {FirstName} {LastName} from {Company}"}}];

describe('test show template function', function() {

	
	var rootId = "testContainer";
	var markup = "<select name='templateList' id='templateList'></select>";

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
			expect(selectObject.options[index+1].value).toBe(obj.id.toString());
			expect(selectObject.options[index+1].text).toBe(obj.attributes.TemplateName);
		})	

	});
});

describe('checkOnChangeTemplate function', function(){
    var rootId = "testContainer";
	var markup = "<select id='templateList'> " +
	                "<option id='blank' value='0'>Blank</option>" +
			        "<option id='template_1' value='1'>Default Template 1</option>" +
			      "</select>" +
			      "<input type='text' id='content'> " +
                  "<input type='text' id='subject'> ";

    templateList = mock_json;
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

	it('subject and content should be changed when template 1 is selected',function(){
		ApplyTemplateToUI(1);

        expect(document.getElementById("subject").value).toBe("Greeting {FirstName}");
		expect(document.getElementById("content").value).toBe("Hi, {FirstName} {LastName} from {Company}");
	});

	it('subject and content should be changed when template 2 is selected',function(){
        ApplyTemplateToUI(2);

        expect(document.getElementById("subject").value).toBe("Greeting {LastName}");
        expect(document.getElementById("content").value).toBe("Hello, {FirstName} {LastName} from {Company}");
    });


});


describe('checkUpdateTemplateInputElement function', function(){
	var rootId = "testContainer";
	var markup = "<input type='text' id='content'> " +
	"<input type='text' id='subject'> " +
            "<button type='button' id='update_button' value='update'>Update</button>";
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

	it('should disable update button when all fields are blank',function(){
	    document.getElementById("subject").value = "";
        document.getElementById("content").value = "";
	    spyOn(window,'disableUpdateTemplateButton');
        checkUpdateTemplateInputElement();
        expect(window.disableUpdateTemplateButton).toHaveBeenCalled();
	});

	it('should enable update button when all fields are not blank',function(){
        document.getElementById("subject").value = "Greeting!!!";
        document.getElementById("content").value = "Hello World.";
        spyOn(window,'enableUpdateTemplateButton');
        checkUpdateTemplateInputElement();
        expect(window.enableUpdateTemplateButton).toHaveBeenCalled();
	});
});