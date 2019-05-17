Feature:
  問題のカテゴリを追加できる

  Scenario: 新しいカテゴリを追加できる
    Given カテゴリ追加を開始する画面に遷移する
    And カテゴリ追加ボタンを押す
    Then カテゴリ追加画面に遷移する
    When 新しいカテゴリを入力する
    And 決定ボタンをクリックする
    Then 問題作成画面に遷移する
    And 新しいカテゴリが選択できる

  @developing
  Scenario: Adminが新しいカテゴリを追加できる
    Given Adminがログインする
    When カテゴリ追加ボタンを押す
    Then カテゴリ追加画面に遷移する
    When 新しいカテゴリを入力する
    And 決定ボタンをクリックする
    Then 問題作成画面に遷移する
    And 新しいカテゴリが選択できる
