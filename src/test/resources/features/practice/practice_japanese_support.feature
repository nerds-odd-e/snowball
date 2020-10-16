Feature:
  Japanese language support

  Scenario: 質問の質問文と設問が日本語で表示できる
    Given 日本語の質問がある
    And user "mary" has logged in successfully
    And User is taking a practiceTest
    Then 質問の質問文と設問が日本語で表示される

