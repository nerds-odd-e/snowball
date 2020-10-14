Feature:
  User can practice questions of categories


  Background:
    And There are questions with dummy options:
      | description    | correctOption | category |
      | What is Scrum? | correctOption | Scrum    |
      | Why do Scrum?  | correctOption | Scrum    |
      | What is XP?    | correctOption | XP       |
    Given user "mary" has logged in successfully

    @developing
  Scenario: User can practice question of a specific category
    Given User is taking a practiceTest of "XP"
    Then "What is XP?"という問題が出題される
    When User answered 1 questions correctly
    Then User should see "Good job!"

  @developing
  Scenario: User can practice question of a specific category
    Given User is taking a practiceTest of "Scrum"
    Then User should only see "Scrum" questions
    And there should be 2 questions in the test
