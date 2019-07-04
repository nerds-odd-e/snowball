
Feature:
  User can practice questions using Spaced Repetition.

  Background:
    Given the user chooses the question category "Retro"
#    And Add a question "Q3" with dummy options and chosen category "Retro"
#    And Add a question "Q2" with dummy options and chosen category "Retro"

  @now
  Scenario: User can practice question
    Given Add a question "Q1" with dummy options and chosen category "Retro"
    Given User is taking a practiceTest
    When User answered 1 questions correctly
    Then User should see "You have finished your practice for today"

@developing
  Scenario: User can practise using spaced repetition of interval 1,3,7 days
    Given User has an active practice
    And User should see a question "What is Scrum"
    Then User submits answer
    Then date is increased by 1 days
    And User does a practise
    Then User shuold see a question "What is Scrum"
    And User submits answer
    Then date is increased by 2 days
    And User does a practise
    Then User shuold see a question "What is Scrum"
    And User submits answer
    Then date is increased by 4 days
    And User does a practise
    Then User shuold see a question "What is Scrum"
    And User submits answer

  @developing
  Scenario: New question should be shown to user if there is no due questions
    Given User is on day 2 of active practice
    And User should see a question "What is Scrum Master"
    Then User submits answer
    Then date is increased by 1 days
    And User does a practise
    Then User shuold see a question "What is Scrum"
    And User submits answer
    Then User should see a question "What is Scrum Master"

    @developing
  Scenario: No questions should be shown if questions have been answered today
    Given User has answered all the due questions today
    Then User should not see any questions in the practice page



  @developing
  Scenario: No questions should be shown if questions have been answered today
    Given User is taking a practiceTest
    When User answered 1 question correctly
    Then User should see "You have finished your practice for today"
    When User start a fixed repetition practice with 1 question again on the same day
    Then User should see "You have finished your practice for today"

  @developing
  Scenario: User repeats the fixed interval practice
    Given There is a user with "mary@example.com" but password initialize is undone
    When I login with "mary@example.com" and "abcd1234"
    When I start a practice with 1 question each day, he should see these questions
      | days | question |
      | 0    | Q1       |
      | 1    | Q1       |
      | 2    | Q2       |
      | 3    | Q2       |




