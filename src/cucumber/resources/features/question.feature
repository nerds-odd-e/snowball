Feature:
  User can take an online test :)

  Scenario: 初期表示：質問とoptionsが表示されていること
    Given User is in the top page
    And There is a dataset question "What is Scrum"
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
  Scenario Outline: 最後の問題の時に、正解または不正解を選んで、回答ボタンを押下するとEndOfTestまたはAdviceに遷移すること
    Given There is a dataset question "What is Scrum"
    And User is in the test page
    When User chooses dataset "<selected_option_text>"
    And User clicks the answer button
    Then Move to "<page_content>" page

    Examples:
      | selected_option_text | page_content |
      | Scrum is Rugby       | Advice       |
      | None of the above    | End of test  |

  @developing
  Scenario Outline: 最後の問題でないの時に、正解または不正解を選んで、回答ボタンを押下すると次の問題またはAdviceに遷移すること
    Given There are dataset questions
    And User is in the test page
    When User chooses dataset "<selected_option_text>"
    And User clicks the answer button
    Then Move to "<page_content>" page

    Examples:
      | selected_option_text | page_content |
      | Scrum is Rugby       | Advice       |
      | None of the above    | Question     |

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