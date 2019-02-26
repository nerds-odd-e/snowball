Feature:
  Admin can edit category advice link
  @developing
  Scenario: カテゴリのアドバイスリンクフォームが表示されている
    Given Update Adviceを開いている
    Then アドバイスリンクフォームが表示されている

  @developing
  Scenario Outline: カテゴリのアドバイスリンクが登録できる
    Given Update Adviceを開いている
    When カテゴリで"<category>"を選択している
    And アドバイスリンクに"<link>"と入力する
    Then Update Advice画面に戻ってきてカテゴリが"<category>"でアドバイスリンクが"<link>"になってる
    Examples:
      | category | link                |
      | Scrum    | http://google.co.jp |
      | Tech     | http://facebook.com |
      | Team     | http://yahoo.co.jp  |
