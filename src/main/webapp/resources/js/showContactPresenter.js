const ShowContactPresenter = function(showContactView) {
	this.showContactView = showContactView;
};

ShowContactPresenter.prototype.showContact = function() {
	this.showContactView.renderContact();
};

module.exports = {
  ShowContactPresenterExp: ShowContactPresenter,
}