Feature: The frequency of a question will be set to the base level if the user answers the question incorrectly

  @developing
  Scenario: If there are 2 questions and the user answer 1 of it correctly, the user see the incorrect one in next practice
    Given There is a logged in user
    Given There are questions with dummy options:
      | description | advice                             | category | correctOption |
      | スクラムとは何ですか？ | Read the Scrum Guide again, please | Scrum    | correct |
      | What is PO? | PO is Product Owner | Scrum | correct |
    And The user start the practice
    And The user answer 1 correctly
    And The user answer 1 incorrectly
    And The user has seen the "Good job!"
    When User start the practice again
    Then User only sees the same question that was answer incorrectly