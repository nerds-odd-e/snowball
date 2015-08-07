var ShowContactView = function() {};

ShowContactView.prototype.email = function() {
	return $('#email').val();
};

ShowContactView.prototype.name = function() {
	return $('#name').val();
};

ShowContactView.prototype.lastname = function() {
	return $('#lastname').val();
};

ShowContactView.prototype.renderContact = function() {
	return "<ul><li><input type='checkbox'></li><li><input type='text' id='email' value='a@b.com'/></li><li><input type='text' id='name' value='terry'/></li><li><input type='text' id='lastname' value='odd-e'/></li></ul>";
};

