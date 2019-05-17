Feature:
  特訓ページで問題に解答する

  @now
  Scenario: 特訓の問題に正解する
    Given ユーザが登録されている
    Given 問題 "PBIの分割で重要な3つのVは何か" が追加されている
    When 特訓回答ページに遷移する
    When ユーザが "visible" と "valuable" と "vertical" と回答する
    And ユーザーがanswerボタンを押す
    Then 特訓のEndOfTheTestが表示される


