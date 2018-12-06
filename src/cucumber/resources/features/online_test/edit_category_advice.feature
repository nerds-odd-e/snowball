Feature:
  Admin can add category advice

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
  Scenario Outline: カテゴリのアドバイスが登録できる
    Given Update Adviceを開いている
    When カテゴリで"<category>"を選択している
    And アドバイスに"<advice>"と入力する
    And Updateボタンを押す
    Then Update Advice画面に戻ってきてカテゴリが"<category>"でアドバイスが"<advice>"になってる
    Examples:
      | category | advice                 |
      | Scrum    | You should study scrum |
      | Tech     | You should study tech  |
      | Team     | You should study team  |
