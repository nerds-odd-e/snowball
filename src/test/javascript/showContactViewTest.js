describe('Contact View', function() {
	var testContainer, showContactView;
	
	beforeEach(function() {
		showContactView = new ShowContactView();
		testContainer = $('<div id="testContainer"></div>');
		$("<ul><li><input type='checkbox'></li><li><input type='text' id='email' value='a@b.com'/></li></ul>").appendTo(testContainer);
		testContainer.appendTo("body");
	});
	
	afterEach(function(){
		testContainer.remove();
	});
	
	
	it('should get value of email element',function(){
		expect(showContactView.email()).toEqual('a@b.com');
	});
	
});