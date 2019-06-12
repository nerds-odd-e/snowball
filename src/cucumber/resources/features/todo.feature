Feature:
  TODO機能

  @now
  Scenario: Todo一覧が表示される
    Given Todoが2つある
    When Todo一覧ページに遷移する
    Then Todo一覧ページが表示される
    And Todoが複数表示されている

  @now
  Scenario: Todo登録ができる
    Given Todo一覧ページに遷移する
    When "お風呂掃除"を"title"に入力
    And "add_todo"をクリック
    And Todo一覧ページに遷移する
    And "お風呂掃除"が表示されている

