Feature: Online Test Creation

  Scenario Outline: The questions are randomly chosen and the test is generate
    Given There are "<n>" questions in the system
    And More than one question registered in all categories
    When I start an online test
    Then An online test with "<m>" questions is generated
    And The problem in the test includes four categories

    Examples:
      | n  | m  |
      | 4  | 4  |
      | 10 | 10 |
      | 15 | 10 |
