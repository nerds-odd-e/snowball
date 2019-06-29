Feature:
  User can see his progress on the Question and Advice page :)

  Background:
    Given there is a question category "Scrum"
    And Add a question "What is scrum?" with dummy options
    And Add a question "What is Scrum Master?" with dummy options
    And Add a question "What is Product Owner?" with dummy options

  Scenario: Displaying the question progress ( 1 of 3 )
    And User is on the first question
    Then User sees the question progress as "1/3"

  Scenario: Displaying the question progress ( 2 of 3 )
    And User is on the second question
    Then User sees the question progress as "2/3"

  Scenario: Displaying the question progress of advice page ( 2 of 3 )
    And User picked the wrong answer on the second question
    Then User sees the question progress as "2/3"
