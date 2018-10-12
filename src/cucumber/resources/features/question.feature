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

  @developing
  Scenario Outline: 正解または不正解を選んで、回答ボタンを押下するとEndOfTestまたはAdviceに遷移すること
    Given test question with 5 options and 5th correct
    And User is in the test page
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
  Scenario: テストに10問正解するとEnd of test pageが表示されること
    Given User is in the test page
    And There are 10  questions at the beginning
    And User answered correctly the 10 th question page
    Then "End Of Test" is shown

  @developing
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

 @developing
 Scenario: User selects the correct answer and click on the Next Button
   Given User is in the test page
   And There is only one question
   When User selects the correct answer
   And User Clicks on the next Button
   Then User sees the Summary Page


 @developing
 Scenario: User selects the correct answer and click on the Next Button
   Given User is in the test page
   And There are 2 questions at the beginning
   When User selects the correct answer
   And User Clicks on the Next Button in the first question
   Then User should see second question

@now
  Scenario: User starts a test
    Given User is in the top page
    And There are 5  questions at the beginning
    And User clicks "Start Test" button on menu
    Then User go to the test page for first time
    And There is a question with options