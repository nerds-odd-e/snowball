Feature:
  User can add private questions

  @developing
  Scenario: Privateな質問を追加できる
    When 質問の内容を入力してAddボタンを押す
    Then Privateな質問が追加される
