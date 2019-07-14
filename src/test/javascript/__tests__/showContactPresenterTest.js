const ShowContactPresenterFunctions = require("../../../main/webapp/resources/js/showContactPresenter.js");
const ShowContactViewFunctions = require("../../../main/webapp/resources/js/showContactView.js");

describe('Show Contact Selection Page', function() {
  it('should call renderContact when showContact', function() {
    const mockView = new ShowContactViewFunctions.ShowContactViewExp();
    const presenter = new ShowContactPresenterFunctions.ShowContactPresenterExp(mockView);
    mockView.renderContact = jest.fn();
    mockView.renderContact.mockReturnValue({});

    mockView.email = jest.fn();
    mockView.email.mockReturnValue('a@b.com')

    presenter.showContact();

    expect(mockView.renderContact).toHaveBeenCalled();
  });
});
