Feature: QuestionCreation


  @developing @javascript
  Scenario: After make a question the form is reset
    Given no question registered
    When Push submit with required fields
    Then Display registered contents
    And Reset form

  @developing
  Scenario: I can set the question fields
    Given there are no questions
    When load the form
    Then I can input the question_body
