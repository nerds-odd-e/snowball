const addEventFunctions = require("../../../main/webapp/resources/js/addEvent.js");

describe('isChange function', function() {
  it('should return true when pass blank value', function() {
    expect(addEventFunctions.isChangeExp('  ')).toBe(false);
  });

  it('should return false when pass not blank value', function() {
    expect(addEventFunctions.isChangeExp('Course Title')).toBe(true);
  });
});

describe('checkSaveInputElement function', function() {
  const rootId = 'testContainer';
  const markup =
    "<input type='text' id='courseName' name='courseName'> " +
    "<input type='text' id='courseDuration' name='courseDuration'> " +
    "<button type='button' id='save_button' value='Save' disabled>Save</button>";

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

  it('should disable save button when course name field is changed', function() {
    addEventFunctions.checkSaveInputElementExp('#courseName');
    expect(document.getElementById('save_button').disabled).toBe(true);
  });

  it('should enable save button when field is not changed', function() {
    document.getElementById('courseName').value = 'Course Name';

    addEventFunctions.checkSaveInputElementExp('#courseName');

    expect(document.getElementById('save_button').disabled).toBe(false);
  });
});

describe('getUrlParameter function', function() {
  beforeEach(function() {
    $.decodeURIComponent = jest.fn();
    $.decodeURIComponent.mockReturnValue('status=failed&msg=testMessage');
  });

  it('should return param value for existing param', function() {
    const paramValue = addEventFunctions.getUrlParameterExp('msg');
    expect(paramValue).toBe('testMessage');
  });

  it('should not return param value for non-existing param', function() {
    const paramValue = addEventFunctions.getUrlParameterExp('test');
    expect(paramValue).toBeUndefined();
  });
});

describe('enableSaveButton function', function() {
  const rootId = 'testContainer';
  const markup = "<button type='button' id='save_button' value='Save' disabled>Save</button>";

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

  it('should enable save button', function() {
    addEventFunctions.enableSaveButtonExp();
    expect(document.getElementById('save_button').disabled).toBe(false);
  });
});

describe('disableSaveButton function', function() {
  const rootId = 'testContainer';
  const markup = "<button type='button' id='save_button' value='Save' disabled>Save</button>";

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

  it('should disable save button', function() {
    addEventFunctions.disableSaveButtonExp();
    expect(document.getElementById('save_button').disabled).toBe(true);
  });
});
