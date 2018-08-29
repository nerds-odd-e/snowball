Feature:
  User can take an online test :)

  @now
  Scenario: 初期表示：質問とoptionsが表示されていること
    When User clicks "start test" button
    Then User go to the test page
    And User should see a question and options
      | description | What is scrum?    |
      | option1     | Scrum is Rugby    |
      | option2     | Scrum is Baseball |
      | option3     | Scrum is Soccer   |
      | option4     | Scrum is Sumo     |
      | option5     | None of the above |

  @developing
  Scenario: テストページで正解を選んで、回答ボタンを押下するとEndOfTestに遷移すること
    Given User is in the test page
    When User chooses the correct option
    And User clicks the answer button
    Then User should see the "end of test" page