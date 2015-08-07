describe('Show Contact Selection Page', function() {
	var presenter, mockView;
	
	beforeEach(function(){
		mockView = new ShowContactView();
		presenter = new ShowContactPresenter(mockView);
		spyOn(mockView, 'getContact');
	});
	
	it('should call getContact when showContact',function() {
		spyOn(mockView, 'email').andReturn('a@b.com');
		
		presenter.showContact();
		
		expect(mockView.getContact).toHaveBeenCalled();
	});
});
