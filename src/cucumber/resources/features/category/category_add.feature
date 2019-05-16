Feature:
  問題のカテゴリを追加できる

  @now
  Scenario: Adminが新しいカテゴリを追加できる
    Given Adminがログインする
    When カテゴリ追加ボタンを押す
    Then カテゴリ追加画面に遷移する
    When 新しいカテゴリを入力する
    And 決定ボタンをクリックする
    Then 問題作成画面に遷移する
    And 新しいカテゴリが選択できる

