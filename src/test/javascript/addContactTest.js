describe('isChange function',function(){
	
  it('should return true when pass blank value',function(){
	  expect(isChange("  ")).toBe(false);
  });
  
  it('should return false when pass not blank value',function(){
	   expect(isChange("mail@mail.com")).toBe(true);
 });
});

describe('checkAddInputElement function', function(){
	var rootId = "testContainer";
	var markup = "<input type='text' id='email'> " +
			"<button type='button' id='add_button' value='add' disabled>Add Contact</button>";
	
	beforeEach(function(){
		var container = document.createElement('div');
		container.setAttribute('id', rootId);
		document.body.appendChild(container);
		container.innerHTML = markup;
	});
	
	afterEach(function() {
		var container = document.getElementById(rootId);
		container.parentNode.removeChild(container);
	});

	it('should disable add contact button when add contact field is changed',function(){
		checkAddInputElement();
        expect(document.getElementById("add_button").disabled).toBe(true);
	});
	
	it('should enable add contact button when field is not changed',function(){
        document.getElementById("email").value = "mail@mail.com";
        
        checkAddInputElement();
        
        expect(document.getElementById("add_button").disabled).toBe(false);
	});
	
});