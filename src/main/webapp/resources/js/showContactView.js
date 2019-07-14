const ShowContactView = function() {};

ShowContactView.prototype.email = function() {
	return $('#email').val();
};

ShowContactView.prototype.name = function() {
	return $('#name').val();
};

ShowContactView.prototype.lastName = function() {
	return $('#lastName').val();
};

ShowContactView.prototype.renderContact = function() {
	return "<ul><li><input type='checkbox'></li><li><input type='text' id='email' value='a@b.com'/></li><li><input type='text' id='name' value='terry'/></li><li><input type='text' id='lastName' value='odd-e'/></li></ul>";
};

module.exports = {
  ShowContactViewExp: ShowContactView,
}

