Feature: Test Results page
  Users will be shown the test results when they have answered all the questions in the test

  @developing
  @now
  Scenario Outline:
    Given User finished a exam with <correctanswer> out of <totalquestion> score
    When User is on the Test Results page
    Then  The Test results page displays "<message>" and "<result_percentage>"
  Examples:
  | correctanswer | totalquestion | message | result_percentage |
  | 6   | 10 |  Please, try to get better score!!! | 60% |
  | 7   | 10 |  Please, try to get better score!!! | 70% |
  | 8   | 10 |  Good :) | 80% |
  | 9   | 10 |  Excellent :D | 90% |
  | 10  | 10 |  Excellent :D | 100% |
  | 2   | 10 | Too bad !!! :(  | 20% |
  | 3   | 10 |  Too bad !!! :( | 30% |



