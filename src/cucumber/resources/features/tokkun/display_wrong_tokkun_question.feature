Feature:
  display wrong tokkun question one day later

  Scenario Outline: 間違った問題は22時間後に表示される
    Given ユーザが登録されている
    Given ユーザがログインされている
    Given Add a question "スクラムとは何ですか？" with dummy options and advice "Read the Scrum Guide again, please"
    Given スクラムとは何ですか？の問題が<elapsed_hour>時間前に不正解になっている
    Then 問題が<is_display>される
    Examples:
    | elapsed_hour | is_display |
    | 23           | 表示        |
    | 21           | 非表示      |

  Scenario Outline: 正解した問題は22時間後に表示されない
    Given ユーザが登録されている
    Given ユーザがログインされている
    Given Add a question "スクラムとは何ですか？" with dummy options and advice "Read the Scrum Guide again, please"
    Given スクラムとは何ですか？の問題が<elapsed_hour>時間前に正解になっている
    Then 問題が<is_display>される
    Examples:
      | elapsed_hour | is_display |
      | 23           | 非表示      |
      | 21           | 非表示      |