describe('isChange function',function(){

  it('should return true when pass blank value',function(){
	  expect(isChange("  ")).toBe(false);
  });

  it('should return false when pass not blank value',function(){
	   expect(isChange("Course Title")).toBe(true);
 });
});

describe('checkSaveInputElement function', function(){
	var rootId = "testContainer";
	var markup = "<input type='text' id='courseName' name='courseName'> " +
	        "<input type='text' id='courseDuration' name='courseDuration'> " +
			"<button type='button' id='save_button' value='Save' disabled>Save</button>";

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

	it('should disable save button when course name field is changed',function(){
		checkSaveInputElement("#courseName");
        expect(document.getElementById("save_button").disabled).toBe(true);
	});

	it('should enable save button when field is not changed',function(){
        document.getElementById("courseName").value = "Course Name";

        checkSaveInputElement("#courseName");

        expect(document.getElementById("save_button").disabled).toBe(false);
	});

});


describe('getUrlParameter function',function(){

     beforeEach(function(){
        spyOn(window, 'decodeURIComponent').and.returnValue('status=failed&msg=testMessage');
     });

     it('should return param value for existing param',function(){
 	    var paramValue=getUrlParameter("msg");
         expect(paramValue).toBe('testMessage');
 	});

 	 it('should not return param value for non-existing param',function(){
     	    var paramValue=getUrlParameter("test");
             expect(paramValue).toBeUndefined();
     	});

});

describe('enableSaveButton function', function(){
	var rootId = "testContainer";
	var markup = "<button type='button' id='save_button' value='Save' disabled>Save</button>";

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


	it('should enable save button',function(){
       enableSaveButton();
       expect(document.getElementById("save_button").disabled).toBe(false);
	});

});

describe('disableSaveButton function', function(){
	var rootId = "testContainer";
	var markup = "<button type='button' id='save_button' value='Save' disabled>Save</button>";

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


	it('should disable save button',function(){
       disableSaveButton();
       expect(document.getElementById("save_button").disabled).toBe(true);
	});

});