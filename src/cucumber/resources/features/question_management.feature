Feature: Question management
  As a trainer, I want to manage questions,
  so that students can take test.

  Background:
    Given a trainer enters the question edit page


  @developing
  Scenario: trainer add a question with 2 options
    When he inputs question:
      | description  |  "what is 1+1?"  |
      | option1      |  "must be 3!"    |
      | option2      |  "of course 2."  |
      | advice       | "you should read a math book" |
    And he set the option2 as the correct answer
    And he press the "add_button"
    Then he should see question in question list:
      | description  |  "what is 1+1?"  |
      | option1      |  "must be 3!"    |
      | option2      |  "of course 2."  |
      | advice       | "you should read a math book" |
    And option2 is green

  @developing
  Scenario: trainer add a question with 5 options
    When he inputs question:
      | description  |  "what is 1+1?"  |
      | option1      |  "must be 1!"    |
      | option2      |  "of course 2."  |
      | option3      |  "must be 3!"    |
      | option4      |  "of course 4."  |
      | option5      |  "of course 5."  |
      | advice       | "you should read a math book" |
    And he set the option2 as the correct answer
    And he press the "add_button"
    Then he should see question in question list:
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
    When Trainer add a new question with <description>
    Then Error message "<error message>" appears and stay at the same page

    Examples:
      |description|error message                 |
      |over length|The description is over length|
      |empty      |The description is empty      |

  @developing
  Scenario Outline: Add a new question with invalid option
    When Trainer add a new question with options that have "<option lengths>"
    Then Error message "<error message>" appears and stay at the same page
    Examples:
      |option lengths|error message                       |
      |0,0,0,0,0     |All options are empty               |
      |10,0,5,0,0    |Please input options in order       |
      |5,0,0,0,0     |You need to input at least 2 options|
      |101,10,0,0,0  |Option is over length               |



  @developing
  Scenario: Adding a new question with over length option
    When Trainer add a new question that the "<option>" is over length
    Then Error message "The option is over length" appears and stay at the same page

  @developing
  Scenario: Adding a new question with over length advice
    When Trainer add a new question that the "<advice>" is over length
    Then Error message "The advice is over length" appears and stay at the same page

  @developing
  Scenario: Adding a new question with same description
    When Trainer add a new question that the "<description>" is the same with an existing question
    Then Error message "The question already exists" appears and stay at the same page

  @developing
  Scenario: trainer add a question with only 1 option
    When Add a question that has only 1 option
    Then Error message appears and stay at the same page

  @developing
  Scenario: correct answer text is nothing
    When Add a question that has correct answer text is nothing
    Then Error message appears and stay at the same page



  @developing
  Scenario: trainer add a question with empty options
    When trainer add a question
    And "<option>" is empty
    Then Error message appears and stay at the same page

  @developing
  Scenario: trainer add a question with empty options
    When trainer add a question
    And "<advise>" is empty
    Then Error message appears and stay at the same page

  @developing
  Scenario: trainer add a question without selecting the correct answer
    When trainer add a question
    And  none of options is selected as correct answer
    Then Error message appears and stay at the same page

  @developing
  Scenario Outline: trainer add a question that has skipped option lines
    When he add a question description "what is 2*2?"
    And add question option1 "<option1>"
    And add question option2 "<option2>"
    And add question option3 "<option3>"
    And add question option4 "<option4>"
    And add question option5 "<option5>"
    And he set the <correct answer> as the correct answer
    And he set the advise "you should read a math book"
    And he press the "<add_button>"
    Then he see the message "<error message>" and stay at the same page

  Examples:
    | option1 | option2 | option3 | option4 | option5 | correct answer | error message |
    | 3.      |         | 4.      |         |         | option3        | Error. Blank option must not be included before the bottom option |
    |         |         | 4.      |         |         | option3        | Error. Blank option must not be included before the bottom option |
    | 1.      |         |         | 4.      |         | option4        | Error. Blank option must not be included before the bottom option |
