Feature:
  Japanese language support

  Scenario: 質問の質問文と設問が日本語で表示できる
    Given 日本語の質問がある
    And user "mary" has logged in successfully
    And User is taking a practiceTest
    Then 質問の質問文と設問が日本語で表示される
    
  Scenario: 回答で間違えた時に質問の質問文と設問とアドバイスが日本語で表示できる
    Given 日本語の質問がある
    And user "mary" has logged in successfully
    And User is taking a practiceTest
    When 誤った設問を選択して
    And User clicks the answer button
    Then 質問の質問文と設問とアドバイスが日本語で表示される