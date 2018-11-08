Feature: Show Multiple Question

  Scenario: 複数選択問題を表示する
    Given 既に「スクラムにある役割は何がありますか？」という複数選択回答の問題がある
    And ユーザーの最初の問題です
    Then 複数選択回答が表示されること

