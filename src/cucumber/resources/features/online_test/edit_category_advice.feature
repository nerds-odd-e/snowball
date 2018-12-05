Feature:
  Admin can add category advice

  @now
  Scenario Outline: カテゴリのアドバイスが登録できる
    Given Update Adviceを開いている
    When カテゴリで"<category>"を選択している
    And アドバイスに"<advice>"と入力する
    And Updateボタンを押す
    Then 画面に入力した内容が画面に表示されている
    Examples:
      | category | advice                 |
      | Scrum    | You should study scrum |
