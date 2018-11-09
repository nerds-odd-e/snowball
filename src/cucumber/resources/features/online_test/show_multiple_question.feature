Feature: Show Multiple Question

  Scenario: 複数選択をする
    Given 既に「スクラムにある役割は何がありますか？」という複数選択回答の問題がある
    And ユーザーの最初の問題です
    Then 複数選択回答が表示されること
    When 2つ回答を選択する
    Then 2つ回答が選択されている事

  @developing
  Scenario Outline: 正解の答えを選んだらへEndToTest遷移
    Given 既に「スクラムにある役割は何がありますか？」という複数選択回答の問題がある
    And ユーザーの最初の問題です
    When "<incorrect option>"を選択した

  Examples:
  | incorrect option |
  | BOSS             |
  | CTO              |
