const showParticipantsFunctions = require("../../../main/webapp/resources/js/showParticipants.js");

describe('test show email details function', function() {
  const rootId = 'testContainer';
  const markup =
    "<table id='summarySection'><tbody></tbody></table><table id='listTable'><tbody></tbody></table>";
  const firstSentMailVisit = { email: 'test@t', open_count: 5 };

  const mock_json = {
    courseName: 'CSD Tokyo',
    participants: [
      {
        email: 'tom@example.com',
        name: 'tom'
      }
    ]
  };

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

  it('should render participant rows', function() {
    showParticipantsFunctions.renderParticipantRowsExp(mock_json, $('#listTable tbody'));

    $.each(mock_json.participants, function(idx, item) {
      expect(
        $('#listTable .email')
          .eq(idx)
          .text()
      ).toBe(item.email);
      expect(
        $('#listTable .name')
          .eq(idx)
          .text()
      ).toBe(item.name);
    });
  });
});
