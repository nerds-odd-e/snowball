@developing
Feature:
  User can practice questions using Spaced Repetition.

  Background:
    Given that there are questions
      | question              |
      | What is Scrum         |
      | What is Scrum Master  |
      | What is Product Owner |


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

  Scenario: New question should be shown to user if there is no due questions
    Given User is on day 2 of active practice
    And User should see a question "What is Scrum Master"
    Then User submits answer
    Then date is increased by 1 days
    And User does a practise
    Then User shuold see a question "What is Scrum"
    And User submits answer
    Then User should see a question "What is Scrum Master"

  Scenario: No questions should be shown if questions have been answered today
    Given User has answered all the due questions today
    Then User should not see any questions in the practice page