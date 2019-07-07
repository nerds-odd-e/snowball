Feature:
  User can practice questions using Spaced Repetition.

  Background:
    And There are questions with dummy options:
      | description | correctOption | category |
      | Q1          | correctOption | Retro    |
    Given user "mary" has logged in successfully

  Scenario: User can practice question
    Given User is taking a practiceTest
    When User answered 1 questions correctly
    Then User should see "You have finished your practice for today"

  Scenario: No questions should be shown if questions have been answered today
    Given User is taking a practiceTest
    When User answered 1 question correctly
    Then User should see "You have finished your practice for today"
    When User is taking a practiceTest
    Then User should see "You have finished your practice for today"

  Scenario: If user answers wrongly, user should be shown advice page and be redirected to see completed practice page
    Given User is taking a practiceTest
    When User answered 1 question wrongly
    Then User should see Advice page
    When User clicks on Next on Advice page
    Then User should see "You have finished your practice for today"
    When User is taking a practiceTest
    Then User should see "You have finished your practice for today"
