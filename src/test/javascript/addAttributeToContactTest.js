describe("Add Firstname and Lastname to Existing Contact",function(){
	
	var rootId = "testContainer";
	var markup="";

	beforeEach(function() {		
		var modal = document.createElement('div');
		modal.setAttribute('id', "editContactModal");
		document.body.appendChild(modal);
		
		var emailField = document.createElement("input");
		emailField.type = "hidden";
		emailField.setAttribute('id',"email");
		document.body.appendChild(emailField);
		
		var emailLabel = document.createElement("label");
		emailLabel.label = "testExistEmail@test.com";
		emailLabel.setAttribute('id',"email_label");
		document.body.appendChild(emailLabel);
		
		var lastNameField = document.createElement("input");
		lastNameField.type = "text";
		lastNameField.setAttribute('id',"lastName");
		document.body.appendChild(lastNameField);
		
		var nameField = document.createElement("input");
		nameField.type = "text";
		nameField.setAttribute('id',"name");
		document.body.appendChild(nameField);
		
		var saveButton = document.createElement("button");
		saveButton.setAttribute('id',"save_button");
		document.body.appendChild(saveButton);
		
	});

	afterEach(function() {		
		var email_cmpt = document.getElementById('email');
		var name_cmpt = document.getElementById('name');
		var lastName_cmpt = document.getElementById('lastName');
		document.body.removeChild(email_cmpt);
		document.body.removeChild(name_cmpt);
		document.body.removeChild(lastName_cmpt);
	});

	
	it("should show email as the same as the one that was being clicked", function(){
		var mock_json_contact_item = {"id":1,"attributes":{"email":"john@gmail.com","location":"Singapore/Singapore"}};
		spyOn(window,'openEditContactModal');
		showEditContactDetail(mock_json_contact_item);
		expect($('#name').val()).toBe("");
		expect($('#lastName').val()).toBe("");
		expect($('#email').val()).toBe(mock_json_contact_item.email);
		expect($('#email_label').text()).toBe(mock_json_contact_item.email);
	});
	
	it("should show name and lastName as the same as the one that was being clicked", function(){
		var mock_json_contact_item = {"id":1,"attributes":{"company":"","email":"john@gmail.com","firstName":"John","lastName":"Winyu","location":"Singapore/Singapore"}};
		spyOn(window,'openEditContactModal');
		showEditContactDetail(mock_json_contact_item);
		expect($('#name').val()).toBe(mock_json_contact_item.firstName);
		expect($('#lastName').val()).toBe(mock_json_contact_item.lastName);
		expect($('#countrydrp').val()).toBe(mock_json_contact_item.countrydrp);
		expect($('#city').val()).toBe(mock_json_contact_item.city);
		expect($('#email').val()).toBe(mock_json_contact_item.email);
		expect($('#email_label').text()).toBe(mock_json_contact_item.email);
	});
	
});