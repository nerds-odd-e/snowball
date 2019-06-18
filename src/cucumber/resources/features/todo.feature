Feature:
  Todo機能

  @now
  Scenario: Todo登録
    Given テキストボックスに"トイレ掃除"が入力されている
    When 登録ボタンをおす
    Then "トイレ掃除"がリストにが表示されている
    When リロードをする
    Then "トイレ掃除"がリストにが表示されている
