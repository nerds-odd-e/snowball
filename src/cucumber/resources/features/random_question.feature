Feature:
  User can take random question test

  @developing
  Scenario: Student can take a random question test
    Given Student as user
    And Student want to take random test
    And System displayed random test question page.
    When Student choose a correct answer.
    And Student pressed "answer" button.
    Then System displayed next question which choose Randomly