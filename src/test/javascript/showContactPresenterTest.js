describe('Show Contact Selection Page', function() {
	var presenter, mockView;
	
	beforeEach(function(){
		mockView = new ShowContactView();
		presenter = new ShowContactPresenter(mockView);
	});
	
	it('should call renderContact when showContact',function() {
		spyOn(mockView, 'renderContact');
		spyOn(mockView, 'email').andReturn('a@b.com');
		
		presenter.showContact();
		
		expect(mockView.renderContact).toHaveBeenCalled();
	});

});
