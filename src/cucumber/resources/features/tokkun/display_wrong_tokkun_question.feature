Feature:
  display wrong tokkun question one day later

  Scenario Outline:
    Given 問題が登録されている
    When 問題に<is_answer_correct>する
    And <elapsed_hour>時間が経過する
    Then 問題が<is_display>される
    Examples:
      | is_answer_correct | elapsed_hour | is_display |
      | 正解                | 22           | 非表示        |
      | 不正解               | 22           | 表示         |
      | 不正解               | 21           | 非表示        |
