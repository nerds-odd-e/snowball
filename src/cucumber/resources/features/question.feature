Feature:
  User can take an online test :)

  Background:
    Given User is in the test page

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
    
  Scenario Outline: 正解または不正解を選んで、回答ボタンを押下するとEndOfTestまたはAdviceに遷移すること
    When User chooses "<selected_option>"
    And User clicks the answer button
    Then Redirected to "<redirected_page>" page

    Examples:
      | selected_option | redirected_page |
      | option1 | advice |
      | option2 | advice |
      | option3 | advice |
      | option4 | advice |
      | option5 | end_of_test |

  @developing
  Scenario: テストページで不正解を選んで、回答ボタンを押下するとAdviceページが表示されること
    When User chooses the incorrect option
    And User clicks the answer button
    Then User go to the "Advice" page
    And User should see "correct" option highlighted and text "None of the above"
    And User should see "selected incorrect" option highlighted and text "Scrum is Baseball"
    And User should see "advice text"
