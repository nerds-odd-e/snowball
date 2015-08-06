var ShowContactPresenter = function(showContactView) {
	this.showContactView = showContactView;
};

ShowContactPresenter.prototype.showContact = function() {
	this.showContactView.getContact();
};