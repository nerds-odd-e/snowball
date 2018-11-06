Feature: Show Multiple Question

  @developing
  Scenario: 複数選択問題を表示する
    Given 既に「スクラムにある役割は何がありますか？」という複数選択回答の問題がある
    When 複数選択回答の問題が選ばれる
    Then 問題を表示する
#    Then 選択された問題を表示する
#    And 選択肢のチェックボックスを表示する
#    And Answerボタンを表示する
