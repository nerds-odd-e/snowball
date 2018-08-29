Feature: Question management
  As a trainer, I want to manage questions,
  so that students can take test.

  Background:
    Given a trainer enters the question edit page

  @developing
  Scenario: trainer add a question with 2 options
    When trainer inputs question:
      | description  |  "what is 1+1?"  |
      | option1      |  "must be 3!"    |
      | option2      |  "of course 2."  |
      | advice       | "you should read a math book" |
    And trainer set the option2 as the correct answer
    And trainer press the "add_button"
    Then trainer should see question in question list:
      | description  |  "what is 1+1?"  |
      | option1      |  "must be 3!"    |
      | option2      |  "of course 2."  |
      | advice       | "you should read a math book" |
    And option2 is green

  @developing
  Scenario: trainer add a question with 5 options
    When trainer inputs question:
      | description  |  "what is 1+1?"  |
      | option1      |  "must be 1!"    |
      | option2      |  "of course 2."  |
      | option3      |  "must be 3!"    |
      | option4      |  "of course 4."  |
      | option5      |  "of course 5."  |
      | advice       | "you should read a math book" |
    And trainer set the option2 as the correct answer
    And trainer press the "add_button"
    Then trainer should see question in question list:
      | description  |  "what is 1+1?"  |
      | option1      |  "must be 1!"    |
      | option2      |  "of course 2."  |
      | option3      |  "must be 3!"    |
      | option4      |  "of course 4."  |
      | option5      |  "of course 5."  |
      | advice       | "you should read a math book" |
    And option2 is green

  @developing
  Scenario Outline: Add a new question with invalid description
    When trainer add a new question with description that have "<description length>"
    And trainer press the "add_button"
    Then Error message "<error message>" appears and stay at the same page

    Examples:
      |description length|error message                 |
      |501               |The description is over length|
      |0                 |The description is empty      |

  @developing
  Scenario Outline: Add a new question with invalid option
    When trainer add a new question with options that have "<option lengths>"
    And option2 is selected as correct answer
    And trainer press the "add_button"
    Then Error message "<error message>" appears and stay at the same page
    Examples:
      |option lengths|error message                       |
      |0,0,0,0,0     |All options are empty               |
      |10,0,5,0,0    |Please input options in order       |
      |5,0,0,0,0     |You need to input at least 2 options|
      |101,10,0,0,0  |Option is over length               |

  @developing
  Scenario Outline: Add a new question with invalid advice
    When trainer add a new question with advice that have "<advice length>"
    And option2 is selected as correct answer
    And trainer press the "add_button"
    Then Error message "<error message>" appears and stay at the same page

    Examples:
      |advice length |error message            |
      |501           |Advice is over length    |
      |0             |You need to input advice |

  @developing
  Scenario: Adding a new question with same description
    When trainer add a new question that the "<description>" is the same with an existing question
    And trainer inputs question with description:
      | description  |  "what is 1+1?"  |
    And option2 is selected as correct answer
    And trainer press the "add_button"
    And a trainer enters the question edit page
    And trainer inputs question with description:
      | description  |  "what is 1+1?"  |
    And option2 is selected as correct answer
    And trainer press the "add_button"
    Then Error message "The question already exists" appears and stay at the same page

  @developing
  Scenario: correct answer text is empty
    When trainer inputs question that has option2 text is empty
    And option2 is selected as correct answer
    And trainer press the "add_button"
    Then Error message appears and stay at the same page

  @developing
  Scenario: trainer add a question without selecting the correct answer
    When trainer inputs a question
    And none of options is selected as correct answer
    And trainer press the "add_button"
    Then Error message appears and stay at the same page