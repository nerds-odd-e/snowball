Feature:
  TODO機能

  @now
  Scenario: Todo一覧が表示される
    Given Todoが2つある
    When Todo一覧ページに遷移する
    Then Todo一覧ページが表示される
    And Todoが複数表示されている

