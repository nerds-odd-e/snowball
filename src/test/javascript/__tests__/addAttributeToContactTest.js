var showContactFunctions = require("../../../main/webapp/resources/js/showContact.js");
var { queryByTestId, wait } = require("@testing-library/dom");

describe("Add Firstname and Lastname to Existing Contact",function() {
	var rootId = "testContainer";
	var markup="";
  var pageDOM;

	function getPageDOM() {
		var modal = document.createElement('div');
		modal.setAttribute('id', "editContactModal");
		document.body.appendChild(modal);

		var emailField = document.createElement("input");
		emailField.type = "hidden";
		emailField.setAttribute('id',"email");
		emailField.setAttribute('data-testid',"email");
		document.body.appendChild(emailField);

		var emailLabel = document.createElement("label");
		emailLabel.label = "testExistEmail@test.com";
		emailLabel.setAttribute('id',"email_label");
		emailLabel.setAttribute('data-testid',"email_label");
		document.body.appendChild(emailLabel);

		var lastNameField = document.createElement("input");
		lastNameField.type = "text";
		lastNameField.setAttribute('id',"lastName");
		lastNameField.setAttribute('data-testid',"lastName");
		document.body.appendChild(lastNameField);

		var nameField = document.createElement("input");
		nameField.type = "text";
		nameField.setAttribute('id',"name");
		nameField.setAttribute('data-testid',"name");
		document.body.appendChild(nameField);

		var saveButton = document.createElement("button");
		saveButton.setAttribute('id',"save_button");
		document.body.appendChild(saveButton);

		return document;
	};

	beforeEach(function() {
	  pageDOM = getPageDOM();
		global.openEditContactModal = jest.fn();
  });

	afterEach(function() {
		var email_cmpt = document.getElementById('email');
		var name_cmpt = document.getElementById('name');
		var lastName_cmpt = document.getElementById('lastName');
		var email_label_cmpt = document.getElementById('email_label');
		document.body.removeChild(email_cmpt);
		document.body.removeChild(email_label_cmpt);
		document.body.removeChild(name_cmpt);
		document.body.removeChild(lastName_cmpt);
	});

	it("should show email as the same as the one that was being clicked", async function() {
		var mock_json_contact_item = {"id":1,"email":"john@gmail.com","location":"Singapore/Singapore"};
		showContactFunctions.showEditContactDetail(mock_json_contact_item);
		await wait(function() {
		  expect(queryByTestId(pageDOM, 'name')).toHaveTextContent('');
		  expect(queryByTestId(pageDOM, 'lastName')).toHaveTextContent('');
		  expect(queryByTestId(pageDOM, 'email').value).toEqualCaseInsensitive(mock_json_contact_item.email);
		  expect(queryByTestId(pageDOM, 'email_label').innerHTML).toEqualCaseInsensitive(mock_json_contact_item.email);
		});
	});

	it("should show name and lastName as the same as the one that was being clicked", async function() {
		var mock_json_contact_item = {"id":1,"company":"","email":"john@gmail.com","firstName":"John","lastName":"Winyu","location":"Singapore/Singapore"};
		showContactFunctions.showEditContactDetail(mock_json_contact_item);
		await wait(function() {
		  expect(queryByTestId(pageDOM, 'name').value).toEqualCaseInsensitive(mock_json_contact_item.firstName);
		  expect(queryByTestId(pageDOM, 'lastName').value).toEqualCaseInsensitive(mock_json_contact_item.lastName);
		  expect(queryByTestId(pageDOM, 'email').value).toEqualCaseInsensitive(mock_json_contact_item.email);
		  expect(queryByTestId(pageDOM, 'email_label').innerHTML).toEqualCaseInsensitive(mock_json_contact_item.email);
		  expect($('#countrydrp').value).toEqualCaseInsensitive(mock_json_contact_item.countrydrp);
          expect($('#city').value).toEqualCaseInsensitive(mock_json_contact_item.city);
		});
	});
});
