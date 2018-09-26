Feature: Online Test Creation

  @developing
  Scenario Outline: The questions are randomly chosen and the test is generate
    Given There are "<n>" questions in the system
    When I start an online test
    Then An online test with "<m>" questions is generated

    Examples:
      | n   | m   |
      | 0   | 0   |
      | 1   | 1   |
      | 10   | 10  |
      | 15   | 10  |
