Feature: Advice page
  Users will be redirected to the advice page when they answer wrongly

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
  Scenario Outline: Exiting the Advice page
    Given I take the test
    And there are "<pending>" questions
    And I'm on Advice Page
    When I click on the "<next>" button
    Then I should see the "<nextPage>" page
    Examples:
      | pending | next   | nextPage |
      | True    | Next   | Question |
      | False   | Finish | Summary  |