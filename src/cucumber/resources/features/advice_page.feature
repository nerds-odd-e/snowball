Feature:
 Advice page shown when user answers wrongly

  Scenario Outline:
    Given User is in the test page

    And There is a question "What is Scrum"
    When User chooses the "<incorrect option>" option
    And User clicks the answer button
    Then User go to the "Advice" page
    And User should see "correct" option highlighted and text "None of the above"
    And User should see "selected incorrect" option highlighted and text "<text>"
    And User should see "<advice>"

    Examples:
      | incorrect option | text              | advice                                     |
      | option1          | Scrum is Rugby    | Scrum is a framework for agile development.|
      | option2          | Scrum is Baseball | Scrum is a framework for agile development.|
      | option3          | Scrum is Soccer   | Scrum is a framework for agile development.|
      | option4          | Scrum is Sumo     | Scrum is a framework for agile development.|

  @now
  @developing
  Scenario: Proceed to next question
    Given I take the test
    And I'm on Advice Page
    And there are more questions
    When I click on Next Question
    Then I should see the Next Question Page
