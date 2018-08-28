Feature: add question

  @developing
  Scenario: trainer add a question with an empty description
    When a trainer enters the question edit page
    And trainer add a question
    And "<description>" is empty
    Then Error message appears and stay at the same page

  @developing
  Scenario: trainer add a question with empty options
    When a trainer enters the question edit page
    And trainer add a question
    And "<option>" is empty
    Then Error message appears and stay at the same page

  @developing
  Scenario: trainer add a question with empty options
    When a trainer enters the question edit page
    And trainer add a question
    And "<advise>" is empty
    Then Error message appears and stay at the same page

  @developing
  Scenario: trainer add a question without selecting the right answer
    When a trainer enters the question edit page
    And trainer add a question
    And  none of options is selected
    Then Error message appears and stay at the same page
