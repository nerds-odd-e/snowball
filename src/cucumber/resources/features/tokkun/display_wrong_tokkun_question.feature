Feature:
  display wrong tokkun question one day later

  @now
  Scenario Outline: 間違った問題は22時間後に表示される
    Given ユーザが登録されている
    Given Add a question "スクラムとは何ですか？" with dummy options and advice "Read the Scrum Guide again, please"
    Given スクラムとは何ですか？の問題が不正解になっている
    And <elapsed_hour>時間が経過する
    Then 問題が<is_display>される
    Examples:
    | elapsed_hour | is_display |
    | 22           | 表示        |
    | 21           | 非表示      |
