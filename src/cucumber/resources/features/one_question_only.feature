Feature:
  User can take single question test

  @developing
  Scenario: Student can take a single question test
    Given Student as user
    And Student want to take test
    Then Student go to the test page
    And Student click "start test" button
    Then Studenet should see a question and options
    When Student choses the right answer
    Then Student will see "end of test" page