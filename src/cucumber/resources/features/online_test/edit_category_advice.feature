Feature:
  Admin can add category advice

  @now
  Scenario: give advice on failed category
    Given the advice for "Scrum" is "Go read the Scrum Guide"
    When I answer 4 questions wrong out of 5
    Then I should see "Go read the Scrum Guide" in the final result page


  @developing
  Scenario Outline: Validation
    Given Update Adviceを開いている
    And Categoryで"<category>"を選択している
    And Adviceに"<advice>" を入力する
    When Updateボタンを押す
    Then "<messages>"というメッセージが表示される
    Examples:
      | category | advice | messages                        |
      | Scrum    |        | Scrumのアドバイスを入力してください |
      | Scrum    | foo    |                                 |

  @developing
  Scenario Outline: Adviceの呼び出し
    Given Update Adviceを開いている
    When Categoryで"<category>"を選択する
    Then "<advice>"がAdviceのフォームに表示される
    Examples:
      | category | advice       |
      | Scrum    | scrum_advice |
      | Tech     | tech_advice  |
      | Team     | team_advice  |

  @developing
  Scenario Outline: Adviceの更新が成功する
    Given Update Adviceを開いている
    And adviceに"updated advice"を入力する
    And Categoryを"<category>"として選択済み
    When Updateボタンを押す
    Then "updated advice"というadviceが表示される
    And Categoryで"<category>"が選択済み
    Examples:
      | category |
      | Scrum    |
      | Tech     |
      | Team     |
