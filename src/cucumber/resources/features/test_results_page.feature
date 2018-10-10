Feature: Test Results page
  Users will be shown the test results when they have answered all the questions in the test

  @developing
  Scenario:
    Given The user took a test with 10 questions
    And He answered 6 questions correctly
    When User is on the Test Results page
    Then  The Test results page displays "You can improve" and "60%"

