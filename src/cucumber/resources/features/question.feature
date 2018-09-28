Feature:
  User can take an online test :)

  @developing
  Scenario: 最後の問題で正解するとテスト終了ページに遷移すること
    Given There is a a Single Question with 2 options
    And I start the test
    When I make the right answer
    Then I should see the End of Test page

  @developing
  Scenario: 最後の問題で間違えるとアドバイスページに遷移すること
    Given There is a a Single Question with 2 options
    And I start the test
    When I make the incorrect answer
    Then I should see the Advice page

  @developing
  Scenario: 初期表示：質問とoptionsが表示されていること
    Given User is in the top page
    When User clicks "Start Test" button on menu
    Then User should see a question and options
      | description | Is same of feature and story? |
      | option1     | Yes                           |
      | option2     | No                            |
      | option3     | I have no idea.               |

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

  @developing
  Scenario: テストに10問正解するとEnd of test pageが表示されること
    Given User is in the test page
    And There are 10 questions
    And User answered correctly the 10 th question page
    Then "End Of Test" is shown

  @developing
  Scenario Outline: Adviceページから1問残っていればQuestionページ、残っていなければEnd Of Testページが表示されること
    Given User is in the test page
    And User answered correctly the <number_of_questions> th question page
    And User chooses the "option1" option
    And User clicks the answer button
    When User clicks the next button
    Then "<page_content>" is shown

    Examples:
      | number_of_questions | page_content |
      | 8                   | Question     |
      | 9                   | End Of Test  |

   @developing
   Scenario Outline: アドバイス有無、解答正誤、によってのアドバイスページの表示有無
     Given On question, "<question_has_advise>"
     When  User's "<answer_is_right>"
     Then  User "<can_go_advice_page>"
     And  On advise, "<advise_page_has_advise>"

     Examples:
     | question_has_advise | answer_is_right | can_go_advice_page | advise_page_has_advise |
     | true                | true            | false              | false                  |
     | true                | false           | true               | true                   |
     | false               | true            | false              | false                  |
     | false               | false           | true               | false                  |

   @developing
   Scenario Outline: アドバイスページからNEXTボタンを押したとき、問題の進行によりFinishページが表示
     Given On question progress, "<number_of_question>" of 2
     When User clicks incorrect answer
     And User clicks the "Next" button
     Then "<page_content>" is shown

     Examples:
     | number_of_question | page_content |
     | 1                  | Question     |
     | 2                  | End Of Test  |

  @developing
  Scenario: 未回答でNEXTを押したときに、同じQuestionページに遷移する事
    Given User is in the test page
    And Question is "What is Scrum?"
    When User clicks the answer button
    Then Question "What is Scrum?" is shown
    And Answer button is shown
