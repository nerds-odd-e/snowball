const sendEmailFunctions = require("../../../main/webapp/resources/js/sendEmail.js");

describe('isBlank function', function() {
  it('should return true when pass blank value', function() {
    expect(sendEmailFunctions.isBlankExp('  ')).toBe(true);
  });

  it('should return false when pass not blank value', function() {
    expect(sendEmailFunctions.isBlankExp('mail@mail.com')).toBe(false);
  });
});

describe('checkSendEmailInputElement function', function() {
  const rootId = 'testContainer';
  const markup =
    "<input type='text' id='recipient'> " +
    "<input type='text' id='content'> " +
    "<input type='text' id='subject'> " +
    "<button type='button' id='send_button' value='send'>Send</button>";

  beforeEach(function() {
    const container = document.createElement('div');
    container.setAttribute('id', rootId);
    document.body.appendChild(container);
    container.innerHTML = markup;
  });

  afterEach(function() {
    const container = document.getElementById(rootId);
    container.parentNode.removeChild(container);
  });

  it('should disable send button when all fields are blank', function() {
    sendEmailFunctions.checkSendEmailInputElementExp();
    expect(document.getElementById('send_button').disabled).toBe(true);
  });

  it('should enable send button when all fields are not blank', function() {
    document.getElementById('recipient').value = 'mail@mail.com';
    document.getElementById('subject').value = 'Greeting!!!';
    document.getElementById('content').value = 'Hello World.';

    sendEmailFunctions.checkSendEmailInputElementExp();

    expect(document.getElementById('send_button').disabled).toBe(false);
  });
});

describe('isRecipientEmailFormat function', function() {
  it('should pass when input one email', function() {
    expect(sendEmailFunctions.isRecipientEmailFormatExp('mail@mail.com')).toBe(true);
  });

  it('should pass when input two email', function() {
    expect(sendEmailFunctions.isRecipientEmailFormatExp('mail@mail.com;mail1@mail.com')).toBe(true);
  });

  it('should pass when input two email with semi colon', function() {
    expect(sendEmailFunctions.isRecipientEmailFormatExp('mail@mail.com;mail1@mail.com;')).toBe(true);
  });
});

describe('isRecipientCompanyFormat function', function() {
  it('should false when does not input company name format1', function() {
    expect(sendEmailFunctions.isRecipientCompanyFormatExp('company:')).toBe(false);
  });

  it('should false when does not input company name format2', function() {
    expect(sendEmailFunctions.isRecipientCompanyFormatExp('company:"')).toBe(false);
  });

  it('should false when does not input company name format3', function() {
    expect(sendEmailFunctions.isRecipientCompanyFormatExp('company:""')).toBe(false);
  });

  it('should true when input company format 1', function() {
    expect(sendEmailFunctions.isRecipientCompanyFormatExp('company:"AAA"')).toBe(true);
  });

  it('should true when input company format 2', function() {
    expect(sendEmailFunctions.isRecipientCompanyFormatExp('company:"AAA')).toBe(true);
  });

  it('should true when input company format 3', function() {
    expect(sendEmailFunctions.isRecipientCompanyFormatExp('company:AAA')).toBe(true);
  });

  it('should true when input company format 4', function() {
    expect(sendEmailFunctions.isRecipientCompanyFormatExp('company:A"AA')).toBe(true);
  });

  it('should true when input company format 5', function() {
    expect(sendEmailFunctions.isRecipientCompanyFormatExp('company:AAA"')).toBe(false);
  });

  it('should true when input company format 6', function() {
    expect(sendEmailFunctions.isRecipientCompanyFormatExp('company:AAA"A"')).toBe(false);
  });
  it('should true when input company format 7', function() {
    expect(sendEmailFunctions.isRecipientCompanyFormatExp('company:"AA"BB')).toBe(true);
  });
  it('should true when input company format 8', function() {
    expect(sendEmailFunctions.isRecipientCompanyFormatExp('company:"AA"BB"')).toBe(false);
  });
});

describe('checkAddContactInputElement function', function() {
  const rootId = 'testContainer';
  const markup =
    "<input type='text' id='add_new_contact'> " +
    "<button type='button' id='add_contact' value='send'>Add Contact</button>";

  beforeEach(function() {
    const container = document.createElement('div');
    container.setAttribute('id', rootId);
    document.body.appendChild(container);
    container.innerHTML = markup;
  });

  afterEach(function() {
    const container = document.getElementById(rootId);
    container.parentNode.removeChild(container);
  });

  it('should disable add contact button when add contact field is blank', function() {
    sendEmailFunctions.checkAddContactInputElementExp();
    expect(document.getElementById('add_contact').disabled).toBe(true);
  });

  it('should enable add contact button when field is not blank', function() {
    document.getElementById('add_new_contact').value = 'mail@mail.com';

    sendEmailFunctions.checkAddContactInputElementExp();

    expect(document.getElementById('add_contact').disabled).toBe(false);
  });
});