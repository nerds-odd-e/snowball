Feature: The frequency of a question will be set to the base level if the user answers the question incorrectly

  Background:
    Given user "mary" has logged in successfully

  Scenario: If there is question and the user answer 1 of it correctly, the user see the incorrect one in next practice
    Given There are questions with dummy options:
      | description | advice                             | category | correctOption |
      | What is PO? | PO is Product Owner | Scrum | correct |
    And The user start the practice
    And The user answer 1 incorrectly
    And The user has seen the "Good job!"
    When User start the practice again
    Then User only sees the "What is PO?" that was incorrect

