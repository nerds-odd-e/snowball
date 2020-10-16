Feature:
  User can practice questions of history.

  Background:
    And There are questions with dummy options:
      | description | correctOption | category |
      | What is Mob Programing. | correctOption | Retro |
    Given user "mary" has logged in successfully

  Scenario: 未回答の問題が表示される
    Given 未回答の問題だけがある
    And User is taking a practiceTest
    When 全てのユーザが特訓を開始すると
    Then 未回答の問題が表示される

  Scenario: 回答してから５日たった問題が表示される
    Given User is taking a practiceTest
    And 回答してから５日たった問題がある
    When 全てのユーザが特訓を開始すると
    Then 回答してから５日たった問題が表示される

  @developing
  Scenario: 回答済みの問題を優先的に表示する
    Given User is taking a practiceTest
    And 回答して５日たった問題と未回答の問題がある
    When 全てのユーザが特訓を開始すると
    Then 回答して５日たった問題が優先して表示される
