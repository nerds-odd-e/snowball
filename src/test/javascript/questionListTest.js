describe('test question list function', function() {


	var rootId = "testContainer";
	var markup = "<div class='col-lg-12' id='questionTables'></div>";
	var mock_json = [{"id":1,"attributes":{"company":"odde","email":"a@a.com","firstname":"Tom","lastname":"Jerry","location":"Singapore"}},{"id":2,"attributes":{"company":"odde2","email":"b@b.com","firstname":"Jerry","lastname":"Tom","location":"Singapore"}}];

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

	it('should render empty question list when server JSON is empty',function() {

		var mock_json = [];

		renderQuestionList(mock_json, $("#questionTables"));
		expect($(".panel.panel-default").length).toBe(mock_json.length);
	});

	it('should render 1 question when server JSON has 1 question',function() {

    		var mock_json = {
            'question1':
              {
               'description':'what is 1+1?',
               'options':{
                 'option1':'must be 1!0',
                 'option2':'of course 2.',
                 'option3':'must be 3!',
                 'option4':'of course 4.',
                 'option5':'of course 5.'
                },
               'advice':'you should read a math book',
               'correct_answer': 'option2'
              }
            };

    		renderQuestionList(mock_json, $("#questionTables"));
    		console.log(document.getElementById(mock_json['question1']['correct_answer'] + "row").getAttribute("class"));
    		expect(document.getElementById("description").innerHTML).toBe(mock_json['question1']['description']);
    		expect(document.getElementById("option1").innerHTML).toBe(mock_json['question1']['options']['option1']);
    		expect(document.getElementById("option2").innerHTML).toBe(mock_json['question1']['options']['option2']);
    		expect(document.getElementById("option3").innerHTML).toBe(mock_json['question1']['options']['option3']);
    		expect(document.getElementById("option4").innerHTML).toBe(mock_json['question1']['options']['option4']);
    		expect(document.getElementById("option5").innerHTML).toBe(mock_json['question1']['options']['option5']);
    		expect(document.getElementById("advice").innerHTML).toBe(mock_json['question1']['advice']);
    		expect(document.getElementById(mock_json['question1']['correct_answer'] + "row").getAttribute("class")).toBe("bg-success");

    	});
});
