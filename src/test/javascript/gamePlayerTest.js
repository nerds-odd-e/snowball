describe('x',function(){
    var rootId = "testContainer";
    	var markup = "<span id='dice'>6</span>" +
    	    "<span id='currentDistance'>6</span>" +
    			"<button type='button' id='normalButton' onclick='normalRollBtnClicked'>Normal Roll</button></div>"

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

    describe('normal button', function() {
        it("should change dice value to 1 when clicked",function() {
              normalRollBtnClicked();
             expect(document.getElementById("dice").innerHTML).toBe("1");
             expect(document.getElementById("currentDistance").innerHTML).toBe("1");
        });
    });

    describe('super button', function() {
            it("should change dice value to 5 when clicked",function() {
                  superRollBtnClicked();
                  expect(document.getElementById("dice").innerHTML).toBe("5");
                 expect(document.getElementById("currentDistance").innerHTML).toBe("5");
            });
     });
});