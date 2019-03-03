Feature:
  Admin can add category advice

  Scenario Outline: カテゴリのアドバイスが登録できる
    Given Update Adviceを開いている
    When カテゴリで"<category>"を選択している
    And アドバイスに"<advice>"と入力する
    And アドバイスリンクに"<link>"と入力する
    And Updateボタンを押す
    Then Update Advice画面に戻ってきてカテゴリが"<category>"でアドバイスが"<advice>"になってる
    And Update Advice画面に戻ってきてカテゴリが"<category>"でアドバイスリンクが"<link>"になってる
    Examples:
      | category | advice                 | link                |
      | Scrum    | You should study scrum | http://google.co.jp |
      | Tech     | You should study tech  | http://facebook.com |
      | Team     | You should study team  | http://yahoo.co.jp  |
