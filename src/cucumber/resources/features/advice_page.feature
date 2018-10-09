Feature: Advice page
  Users will be sent to the advice page when they answer wrongly

  @now
  @developing
  Scenario: Proceed to next question
    Given I take the test
    And I'm on Advice Page
    And there are more questions
    When I click on Next Question
    Then I should see the Next Question Page
