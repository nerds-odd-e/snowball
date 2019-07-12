const selectContactFunctions = require("../../../main/webapp/resources/js/selectContact.js");
const default_existing_contact = 'someone@gmail.com;somebody@gmail.com;';

describe('test whenAddButtonIsClicked function', function() {
  const rootId = 'testContainer';
  const markup = `<input type='text' class='form-control' name='recipient' id='recipient' value='${default_existing_contact}'>`;

  beforeEach(function() {
    const container = document.createElement('div');
    container.setAttribute('id', rootId);
    document.body.appendChild(container);
    container.innerHTML = markup;

    $.fn.modal = jest.fn(function() {
      $(this).show()
    })
  });

  afterEach(function() {
    const container = document.getElementById(rootId);
    container.parentNode.removeChild(container);
  });

  it('should append selected contacts to the existing in the textbox when user clieck Add button with any contact(s) selected', function() {
    const mocked_selected_contact = 'newbody@gmail.com;';

    $.getCurrentSelectedContactValue = jest.fn();
    $.getCurrentSelectedContactValue.mockReturnValue(mocked_selected_contact);

    selectContactFunctions.whenAddButtonIsClickedExp();

    expect(document.getElementById('recipient').value).toBe(default_existing_contact + mocked_selected_contact);
  });
});

describe('test whenContactIsSelected function', function() {
  const rootId = 'testContainer';
  const markup =
    "<input type='checkbox' id='select_1' value='email1@gmail.com' checked> " +
    "<input type='checkbox' id='select_2' value='email2@gmail.com'> " +
    "<input type='checkbox' id='select_3' value='email3@gmail.com' checked>";

  beforeEach(function() {
    const container = document.createElement('div');
    container.setAttribute('id', rootId);
    document.body.appendChild(container);
    container.innerHTML = markup;
  });

  afterEach(function() {
    const container = document.getElementById(rootId);
    container.parentNode.removeChild(container);
    selectContactFunctions.setCurrentSelectedContactValueExp('');
  });

  it('should append only selected contacts to the temporary selectedContact variable when user select each contact list', function() {
    selectContactFunctions.whenContactIsSelectedExp('select_1');
    selectContactFunctions.whenContactIsSelectedExp('select_2');
    selectContactFunctions.whenContactIsSelectedExp('select_3');

    expect(selectContactFunctions.getCurrentSelectedContactValueExp()).toBe('email1@gmail.com;email3@gmail.com;');
  });

  it('should remove existing email from selectedContact variable when user unselected contacts', function() {
    selectContactFunctions.whenContactIsSelectedExp('select_1');
    selectContactFunctions.whenContactIsSelectedExp('select_2');
    selectContactFunctions.whenContactIsSelectedExp('select_3');

    $('#select_1').prop('checked', false);
    selectContactFunctions.whenContactIsSelectedExp('select_1');

    expect(selectContactFunctions.getCurrentSelectedContactValueExp()).toBe('email3@gmail.com;');
  });
});
