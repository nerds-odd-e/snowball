describe('check blank element',function(){
  it('should be true if blank',function(){
    expect(isBlank(" ")).toBe(true);
  });
});