Feature: add question

  @developing
  Scenario: trainer add a question with only 1 option
    When a trainer enters the question edit page
    And Add a question that has only 1 option
    Then Error message appears and stay at the same page

  @developing
  Scenario: trainer add a question with five options
    When a trainer enters the question edit page
    And Add a question that has 5 options
    Then Add the question in the question list

