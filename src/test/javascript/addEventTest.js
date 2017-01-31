describe('isChange function',function(){

  it('should return true when pass blank value',function(){
	  expect(isChange("  ")).toBe(false);
  });

  it('should return false when pass not blank value',function(){
	   expect(isChange("Event Title")).toBe(true);
 });
});

describe('checkRegisterInputElement function', function(){
	var rootId = "testContainer";
	var markup = "<input type='text' id='evtTitle' name='evtTitle'> " +
	        "<input type='text' id='content' name='content'> " +
			"<button type='button' id='register_button' value='Register' disabled>Register</button>";

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

	it('should disable register event button when register event field is changed',function(){
		checkRegisterInputElement();
        expect(document.getElementById("register_button").disabled).toBe(true);
	});

	it('should enable register event button when field is not changed',function(){
        document.getElementById("evtTitle").value = "Event Title";

        checkRegisterInputElement();

        expect(document.getElementById("register_button").disabled).toBe(false);
	});

});