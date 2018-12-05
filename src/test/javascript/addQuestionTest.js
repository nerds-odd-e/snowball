describe('Show Add Question Page', function() {

	var rootId = "testContainer";
	var markup = "<div class='row' id='options'>test</div> <div class='row' id='checkboxes'>test</div>";

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


	it('should hide checkboxes when select single question',function() {
        switchOptionView(1);

        expect(document.getElementById("options").style.display).toBe("block");
        expect(document.getElementById("checkboxes").style.display).toBe("none");
	});


	it('should hide options when select multiple question',function() {
        switchOptionView(2);

        expect(document.getElementById("options").style.display).toBe("none");
        expect(document.getElementById("checkboxes").style.display).toBe("block");
	});

});
