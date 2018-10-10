Feature: Advice page
  Users will be redirected to the advice page when they answer wrongly

  @developing
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

  @developing
  @now
  Scenario Outline: Exiting the Advice page
    Given I take the test
    And I'm on Advice Page
    And there are "<additional>" questions remaining
    When I click on the Next button
    Then I should see the "<nextPage>" page
    Examples:
      | additional | nextPage |
      | 1          | Question |
