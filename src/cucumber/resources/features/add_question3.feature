Feature: add question

  @developing
  Scenario: trainer add a question with an empty description
    When trainer add a question
    And "<description>" is empty
    Then Error message appears and stay at the same page

  @developing
  Scenario: trainer add a question with empty options
    When trainer add a question
    And "<option>" is empty
    Then Error message appears and stay at the same page

  @developing
  Scenario: trainer add a question without selecting the right answer
    When trainer add a question
    And  none of options is selected
    Then Error message appears and stay at the same page
