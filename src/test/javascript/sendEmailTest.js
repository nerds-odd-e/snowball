describe('check blank element',function(){
  it('should be true if it is blank',function(){
    expect(isBlank(" ")).toBe(true);
  });
  it('should be false if it is not blank',function(){
	    expect(isBlank("not blank")).toBe(false);
	  });
});