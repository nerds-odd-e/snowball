Feature:
  User can take single question test

  @developing
  Scenario: User can start test
    Given User is student
    And User clicks "start test" button
    Then User go to the test page
    And User should see a question and options

  Scenario: User answers a single question test correctly
    Given User is in the test page
    When User chooses the correct option
    And User clicks the answer button
    Then User should see the "end of test" page

  Scenario: User answers a single question test incorrectly
    Given User is in the test page
    When User chooses incorrect option
    And User clicks the answer button
    Then User should see the selected option highlighted in red
    And The correct option should be highlighted in green
    And User should see the advice

  Scenario: Go from advice page to end of test page
    Given User is in advice page
    When User clicks next button
    Then User should see the "end of test" page