Feature: QuestionCreation

  Scenario: Make a question with 6 answers
    Given no question registered
    When Push submit with question body "body" and question advice "advice" with the following answers <body> and the first answers is correct
      | answer_1 |
      | answer_2 |
      | answer_3 |
      | answer_4 |
      | answer_5 |
      | answer_6 |
    Then Display registered contents with 6 answers
    And Reset form

