Feature: AddQuestion


  @developing
  Scenario: After make a question the form is reset
    Given no question registered
    When Push submit with required fields
    Then Display registered contents
    And Reset form

  @now @developing
  Scenario: I can set the question fields
    Given there are no questions
    When load the form
    Then I can input the question_body
    And Click the save button
