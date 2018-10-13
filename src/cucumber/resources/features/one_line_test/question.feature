Feature:
  User can take an online test :)

  Scenario: Displaying the question
    Given There is a question "What is scrum?"
    And User should see a question and options
      | description | What is scrum?    |
      | option1     | Scrum is Rugby    |
      | option2     | Scrum is Baseball |
      | option3     | Scrum is Soccer   |
      | option4     | Scrum is Sumo     |
      | option5     | None of the above |

  Scenario Outline: User navigates to advice or next question page
    Given User is taking a quiz with 2 questions
    And User is on the first question
    When User chooses the "<selected_option>" answer
    And User clicks the answer button
    Then It should move to "<page_content>" page
    Examples:
      | selected_option | page_content |
      | wrongOption     | Advice       |
      | correctOption   | Question     |

  Scenario Outline: advice page should include clear feedback
    Given There is a question "What is scrum?"
    And User is in the test page
    When User chooses the "<incorrect option>" answer
    And User clicks the answer button
    Then User should see "correct" option highlighted and text "None of the above"
    And User should see "selected incorrect" option highlighted and text "<incorrect option>"
    And User should see "Scrum is a framework for agile development."

    Examples:
      | incorrect option  |
      | Scrum is Rugby    |
      | Scrum is Baseball |
      | Scrum is Soccer   |
      | Scrum is Sumo     |

  Scenario Outline: User goes to end of test if he has answered all questions
    Given User is taking a quiz with 3 questions
    And User answered correctly the <number_of_questions> th question page
    Then "<page_content>" should be shown

    Examples:
      | number_of_questions | page_content |
      | 2                   | Question     |
      | 3                   | End Of Test  |

  Scenario: User goes to end of test if he has answered last questions wrong
    Given User is taking a quiz with 3 questions
    And User answered correctly the 2 th question page
    When User chooses the "wrongOption" answer
    And User clicks the answer button
    And User Clicks on the next Button
    Then "End Of Test" should be shown
