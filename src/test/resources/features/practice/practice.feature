Feature:
  User can practice questions using Spaced Repetition.


  Background:
    And There are questions with dummy options:
      | description | correctOption | category |
      | Q1          | correctOption | Retro    |
    Given user "mary" has logged in successfully

  Scenario: User can practice question
    Given User is taking a practiceTest
    When User answered 1 questions correctly
    Then User should see "Good job!"

  Scenario: Return first question should be shown if questions have been answered today
    Given User is taking a practiceTest
    When User answered 1 question correctly
    Then User should see "Good job!"
    When User is taking a practiceTest
    Then 問題1が出題される

  Scenario: If user answers wrongly, user should be shown advice page and be redirected to see completed practice page
    Given User is taking a practiceTest
    When User answered 1 question wrongly
    Then User should see Advice page
    When User clicks on Next on Advice page
    Then User should see "Good job!"

  Scenario: ユーザがプラクティスを開始できること
    Given 問題が15個存在する
    When User is taking a practiceTest
    And User answered 10 questions correctly
    Then User should see "Good job!"

  @developing
  Scenario: 未回答の問題が表示される
    Given 問題が登録されている
    And 未回答の問題だけがある
    When 全てのユーザが特訓を開始すると
    Then 未回答の問題が表示される

  @developing
  Scenario: 回答してから５日たった問題が表示される
    Given 問題が登録されている
    And 回答してから５日たった問題がある
    When 全てのユーザが特訓を開始すると
    Then 回答してから５日たった問題が表示される

  @developing
  Scenario: 回答済みの問題を優先的に表示する
    Given 問題が登録されている
    And 回答して５日たった問題と未回答の問題がある
    When 全てのユーザが特訓を開始すると
    Then 回答して５日たった問題が優先して表示される