Feature: Show Multiple Question

  Scenario: 複数選択をする
    Given 既に「スクラムにある役割は何がありますか？」という複数選択回答の問題がある
    And ユーザーの最初の問題です
    Then 複数選択回答が表示されること
    When 2つ回答を選択する
    Then 2つ回答が選択されている事

  Scenario: 不正解の答えを選んだらへアドバイス遷移
    Given 既に「スクラムにある役割は何がありますか？」という複数選択回答の問題がある
    And ユーザーの最初の問題です
    When "BOSS"を選択した
    And "CTO"を選択した
    And User clicks the answer button
    Then アドバイスページにいる
    
  Scenario: 正解の答えを選んだらへEndOfTest遷移
    Given 既に「スクラムにある役割は何がありますか？」という複数選択回答の問題がある
    And ユーザーの最初の問題です
    When "PO"を選択した
    And "SM"を選択した
    And "Team"を選択した
    And User clicks the answer button
    Then EndOfTheTestが表示される事
