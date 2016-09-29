describe('GamePlayer',function(){
    var rootId = "testContainer";
    var markup = "<span id='dice'>6</span>" +
        "<span id='currentDistance'>6</span>" +
        "<span id='scars'>6</span>" +
    	"<button type='button' id='normalButton' onclick='normalRollBtnClicked'>Normal Roll</button></div>";

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

    describe('Roll die', function() {
        it("should update status on successful callback",function() {
            onSuccessCallback("{\"distance\":20, \"dieResult\": 1, \"playerPos\": 1, \"playerScar\": 0}");
            expect(document.getElementById("dice").innerHTML).toBe("1");
            expect(document.getElementById("scars").innerHTML).toBe("0");
            expect(document.getElementById("currentDistance").innerHTML).toBe("1");
        });
    });
});