var addQuestionFunctions = require("../../../main/webapp/resources/js/addQuestion.js");

describe('Add Question Page', function() {
  const rootId = 'testContainer';
  const markup =
    "<div class='row' id='options'>test</div> <div class='row' id='checkboxes'>test</div>";

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

  it('should hide checkboxes when select single question', function() {
    addQuestionFunctions.switchOptionViewExp('single');

    expect(document.getElementById('options').style.display).toBe('block');
    expect(document.getElementById('checkboxes').style.display).toBe('none');
  });

  it('should hide options when select multiple question', function() {
    addQuestionFunctions.switchOptionViewExp('multiple');

    expect(document.getElementById('options').style.display).toBe('none');
    expect(document.getElementById('checkboxes').style.display).toBe('block');
  });
});
