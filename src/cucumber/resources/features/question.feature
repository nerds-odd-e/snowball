Feature:
  User can take an online test :)

  @now
  Scenario: 初期表示：質問とoptionsが表示されていること
    Given User is in the top page
    And There is a question "What is Scrum"
    When User clicks "Start Test" button on menu
    Then User go to the test page
    And User should see a question and options
      | description | What is scrum?    |
      | option1     | Scrum is Rugby    |
      | option2     | Scrum is Baseball |
      | option3     | Scrum is Soccer   |
      | option4     | Scrum is Sumo     |
      | option5     | None of the above |

  Scenario Outline: 正解または不正解を選んで、回答ボタンを押下するとEndOfTestまたはAdviceに遷移すること
    Given User is in the test page
    And There is a question "What is Scrum"
    When User chooses "<selected_option>"
    And User clicks the answer button
    Then Move to "<page_content>" page

    Examples:
      | selected_option | page_content |
      | option1         | Advice       |
      | option2         | Advice       |
      | option3         | Advice       |
      | option4         | Advice       |
      | option5         | Question     |

  Scenario Outline: テストページで不正解を選んで、回答ボタンを押下するとAdviceページが表示されること
    Given User is in the test page
    And There is a question "What is Scrum"
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

  Scenario: テストに10問正解するとEnd of test pageが表示されること
    Given User is in the test page
    And There are 10 questions
    And User answered correctly the 10 th question page
    Then "End Of Test" is shown

  Scenario Outline: Adviceページから1問残っていればQuestionページ、残っていなければEnd Of Testページが表示されること
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