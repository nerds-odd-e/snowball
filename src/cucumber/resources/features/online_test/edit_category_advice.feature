Feature:
  Admin can add category advice

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
