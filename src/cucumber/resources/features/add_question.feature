Feature: AddQuestion


  @now @developing
  Scenario: Make a question.
    Given no question registered
    When Push submit with required fields
    Then Display registered contents
    And Reset form

