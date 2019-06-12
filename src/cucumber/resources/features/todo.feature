Feature:
  TODO機能

  Scenario: Todo一覧が表示される
    Given Todoが2つある
    When Todo一覧ページに遷移する
    Then Todo一覧ページが表示される
    And Todoが複数表示されている

  Scenario: Todo登録ができる
    Given Todo一覧ページに遷移する
    When "お風呂掃除"を"title"に入力
    And "add_todo"をクリック
    And Todo一覧ページに遷移する
    Then "お風呂掃除"が表示されている

  @now
  Scenario: Todoが検索できる
    Given 以下のTodoがある
      |お風呂掃除|
      |トイレ掃除|
      |買い物|
    And Todo一覧ページに遷移する
    When "買い物"を"search"に入力
    Then "買い物"が表示されている
    And "掃除"が表示されていない
