Feature:
  特訓を開始すると、回答して５日経過した問題もしくは未回答の問題のみ表示される

  @now
  Scenario Outline: 正解した問題は118時間後に表示される
    Given ユーザが登録されている
    Given Add a question "スクラムとは何ですか？" with dummy options and advice "Read the Scrum Guide again, please"
    Given スクラムとは何ですか？の問題が<elapsed_hour>時間前に正解になっている
    Then 問題が<is_display>される
    Examples:
      | elapsed_hour | is_display |
      | 120           | 表示        |
      | 110           | 非表示      |