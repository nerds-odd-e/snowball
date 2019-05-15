Feature:
  display wrong tokkun question one day later


  Scenario Outline: 間違った問題は22時間後に表示される
    Given Given Add a question "スクラムとは何ですか？" with dummy options and advice "Read the Scrum Guide again, please"
    When 問題に<is_answer_correct>する
    And <elapsed_hour>時間が経過する
    Then 問題が<is_display>される
    Examples:
      | is_answer_correct | elapsed_hour | is_display |
      | 正解                | 22           | 非表示        |
      | 不正解               | 22           | 表示         |
      | 不正解               | 21           | 非表示        |
