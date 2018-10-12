Feature:
  User can take an online test :)

  @developing
  Scenario: 初期表示：質問とoptionsが表示されていること
    Given User is in the top page
    And There is a question "What is scrum?"
    When User clicks "Start Test" button on menu
    Then User go to the test page
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
    Then Move to "<page_content>" page
    Examples:
      | selected_option | page_content |
      | wrong           | Advice       |
      | correct         | Question     |

  @developing
  Scenario Outline: テストページで不正解を選んで、回答ボタンを押下するとAdviceページが表示されること
    Given User is in the test page
    And There is a question "What is scrum?"
    When User chooses the "<incorrect option>" option
    And User clicks the answer button
    Then User go to the "Advice" page
    And User should see "correct" option highlighted and text "None of the above"
    And User should see "selected incorrect" option highlighted and text "<text>"
    And User should see "Scrum is a framework for agile development."

    Examples:
      | incorrect option | text              |
      | option1          | Scrum is Rugby    |
      | option2          | Scrum is Baseball |
      | option3          | Scrum is Soccer   |
      | option4          | Scrum is Sumo     |

  @developing
  Scenario Outline: User goes to end of test if he has answered all questions
    Given User is in the test page
    And User answered correctly the <number_of_questions> th question page
    And User chooses the "option2" option
    And User clicks the answer button
    When User clicks the next button
    Then "<page_content>" is shown

    Examples:
      | number_of_questions | page_content |
      | 8                   | Question     |
      | 9                   | End Of Test  |

  @developing
  Scenario: User selects the correct answer and click on the Next Button
    Given User is in the test page
    And There is only one question
    When User selects the correct answer
    And User Clicks on the next Button
    Then User sees the Summary Page

  Scenario: User starts a test
    Given User is in the top page
    And There are 5  questions at the beginning
    And User clicks "Start Test" button on menu
    Then User go to the test page for first time
    And There is a question with options