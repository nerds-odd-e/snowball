Feature:
  特訓ページで問題に解答する

  Background:
    Given there is a question category "Scrum"

  Scenario: Doing tokkun with 1 question for the first time will right answer
    Given Add a question "What is scrum?" with dummy options
    And a user has logged in
    When the user started to do tokkun
    When User chooses the "None of the above" answer
    And User clicks the answer button
    Then the user should see the end of tokkun page

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
