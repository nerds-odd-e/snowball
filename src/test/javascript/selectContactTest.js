var default_existing_contact = "someone@gmail.com;somebody@gmail.com;";

describe('test whenAddButtonIsClicked function', function(){
		
	var rootId = "testContainer";
	var markup = "<input type='text' class='form-control' name='recipient' id='recipient' value='"+default_existing_contact+"'>";
	
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
	
	it('should append selected contacts to the existing in the textbox when user clieck Add button with any contact(s) selected', function(){
		
		var mocked_selected_contact = "newbody@gmail.com;";
		
		spyOn(window, 'getCurrentSelectedContactValue').and.returnValue(mocked_selected_contact);
		
		whenAddButtonIsClicked();

		expect($('#recipient').val()).toBe(default_existing_contact+mocked_selected_contact);
	});
});

describe('test whenContactIsSelected function', function(){
	
	var rootId = "testContainer";
	var markup = 	"<input type='checkbox' id='select_1' value='email1@gmail.com' checked> " +
					"<input type='checkbox' id='select_2' value='email2@gmail.com'> " +
					"<input type='checkbox' id='select_3' value='email3@gmail.com' checked>";
	
	beforeEach(function(){
		var container = document.createElement('div');
		container.setAttribute('id', rootId);
		document.body.appendChild(container);
		container.innerHTML = markup;
	});
	
	afterEach(function() {
		var container = document.getElementById(rootId);
		container.parentNode.removeChild(container);
		setCurrentSelectedContactValue("");
	});
	
	it('should append only selected contacts to the temporary selectedContact variable when user select each contact list', function(){
		
		whenContactIsSelected('select_1');
		whenContactIsSelected('select_2');
		whenContactIsSelected('select_3');

		expect(getCurrentSelectedContactValue()).toBe("email1@gmail.com;email3@gmail.com;");
	});
	
	it('should remove existing email from selectedContact variable when user unselected contacts', function(){
		
		
		whenContactIsSelected('select_1');
		whenContactIsSelected('select_2');
		whenContactIsSelected('select_3');
		
		$('#select_1').prop('checked',false);
		whenContactIsSelected('select_1');

		expect(getCurrentSelectedContactValue()).toBe("email3@gmail.com;");
		
		
//		whenContactIsSelected('select_1');
//		whenContactIsSelected('select_2');
//		whenContactIsSelected('select_3');
//		$('#select_1').prop('checked',false);
//		whenContactIsSelected('select_1');
//
//		expect(getCurrentSelectedContactValue()).toBe("email3@gmail.com;");
	});
});