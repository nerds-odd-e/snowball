Feature:
  特訓ページで問題に解答する

  Background:
    Given there is a question category "Scrum"

  Scenario: 特訓の問題に正解する
    Given 問題 "PBIの分割で重要な3つのVは何か" が追加されている
    And ユーザが登録されている
    When 特訓回答ページに遷移する
    When ユーザが "visible" と "valuable" と "vertical" と回答する
    And ユーザーがanswerボタンを押す
    Then 特訓のEndOfTheTestが表示される


