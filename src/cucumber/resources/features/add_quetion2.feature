Feature: add question

  @developing
  Scenario: trainer add a question with only 1 option
    When a trainer enters the question edit page
    And Add a question that has only 1 option
    Then Error message appears and stay at the same page

  @developing
  Scenario: trainer add a question with 2 options
    When a trainer enters the question edit page
    And Add a question that has 2 options
    Then Add the question in the question list

  @developing
  Scenario: trainer add a question with 5 options
    When a trainer enters the question edit page
    And Add a question that has 5 options
    Then Add the question in the question list

  @developing
  Scenario: trainer add a question with 6 options
    When a trainer enters the question edit page
    And Add a question that has 6 options
    Then Error message appears and stay at the same page

  @developing
  Scenario: correct answer text is nothing
    When a trainer enters the question edit page
    And Add a question that has correct answer text is nothing
    Then Error message appears and stay at the same page

  @developing
  Scenario: trainer add a option
    When a trainer enters the question edit page
    And I add option
    And Push "+(Add option)" button
    And Currently number of option is 4
    Then Add to new option input field to same page

  @developing
  Scenario: trainer add a option
    When a trainer enters the question edit page
    And I add option
    And Push "+(Add option)" button
    And Currently number of option is 5
    Then Error message appears and stay at the same page

  @developing
  Scenario: trainer minus a option
    When a trainer enters the question edit page
    And I minus option
    And Push "-(Minus option)" button
    And Minus button is place to each option's right side
    And Currently number of option is 3
    Then Minus to specified option input field to same page

  @developing
  Scenario: trainer minus a option
    When a trainer enters the question edit page
    And I minus option
    And Push "-(Minus option)" button
    And Minus button is place to each option's right side
    And Currently number of option is 2
    Then Error message appears and stay at the same page