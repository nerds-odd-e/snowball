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
		
		var lastnameField = document.createElement("input");
		lastnameField.type = "text";
		lastnameField.setAttribute('id',"lastname");
		document.body.appendChild(lastnameField);
		
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
		var lastname_cmpt = document.getElementById('lastname');
		document.body.removeChild(email_cmpt);
		document.body.removeChild(name_cmpt);
		document.body.removeChild(lastname_cmpt);
	});

	
	it("should show email as the same as the one that was being clicked", function(){
		var mock_json_contact_item = {"id":1,"attributes":{"email":"john@gmail.com","location":"Singapore/Singapore"}};
		spyOn(window,'openEditContactModal');
		showEditContactDetail(mock_json_contact_item);
		expect($('#name').val()).toBe("");
		expect($('#lastname').val()).toBe("");
		expect($('#email').val()).toBe(mock_json_contact_item.attributes.email);
		expect($('#email_label').text()).toBe(mock_json_contact_item.attributes.email);
	});
	
	it("should show name and lastname as the same as the one that was being clicked", function(){
		var mock_json_contact_item = {"id":1,"attributes":{"company":"","email":"john@gmail.com","firstname":"John","lastname":"Winyu","location":"Singapore/Singapore"}};
		spyOn(window,'openEditContactModal');
		showEditContactDetail(mock_json_contact_item);
		expect($('#name').val()).toBe(mock_json_contact_item.attributes.firstname);
		expect($('#lastname').val()).toBe(mock_json_contact_item.attributes.lastname);
		expect($('#countrydrp').val()).toBe(mock_json_contact_item.attributes.countrydrp);
		expect($('#city').val()).toBe(mock_json_contact_item.attributes.city);
		expect($('#email').val()).toBe(mock_json_contact_item.attributes.email);
		expect($('#email_label').text()).toBe(mock_json_contact_item.attributes.email);
	});
	
});