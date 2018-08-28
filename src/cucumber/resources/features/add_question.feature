Feature: Add Question
  As a trainer, I want to add new questions,
  so that students can take test.

  @developing
  Scenario: Adding a new question with over length description
    When Trainer add a new question that the "<description>" is over length
    Then Error message "The description is over length" appears and stay at the same page

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