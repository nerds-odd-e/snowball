describe('Contact View', function() {
	var testContainer, showContactView, contactElement;
	
	beforeEach(function() {
		showContactView = new ShowContactView();
		testContainer = $('<div id="testContainer"></div>');
		var checkboxField = "<li><input type='checkbox'></li>";
		var emailField = "<li><input type='text' id='email' value='a@b.com'/></li>";
		var nameField = "<li><input type='text' id='name' value='terry'/></li>";
		var lastNameField = "<li><input type='text' id='lastname' value='odd-e'/></li>";
		contactElement ="<ul>"+checkboxField + emailField + nameField + lastNameField + "</ul>"
		$(contactElement).appendTo(testContainer);

//		var data = [{email:"a@b.com"}];
//		$.get('contact.tmpl.html', function(templates) {
//			//$('#contactTemplate').appendTo(testContainer);
//			$('#contactTemplate').tmpl(data).appendTo(testContainer);
//		});
		testContainer.appendTo("body");
		
	
	});
	
	afterEach(function(){
		testContainer.remove();
	});
	
	
	it('should get value of email element',function(){
		expect(showContactView.email()).toEqual('a@b.com');
	});
	
	it('should get value of name element',function(){
		expect(showContactView.name()).toEqual('terry');
	});

	it('should get value of lastname element',function(){
		expect(showContactView.lastname()).toEqual('odd-e');
	});
	
	it('should render contact list element',function(){
		expect(showContactView.renderContact()).toEqual(contactElement);
	});
		
	
});