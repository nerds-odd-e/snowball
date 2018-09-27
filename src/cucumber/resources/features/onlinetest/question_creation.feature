Feature: QuestionCreation


  Scenario: After make a question the form is reset
    Given no question registered
    When Push submit with required fields
    Then Display registered contents
    And Reset form

