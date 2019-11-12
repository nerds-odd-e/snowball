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
  Scenario: 回答した当日同じ問題は出題しないこと
    Given 問題が1個存在する
    When User is taking a practiceTest
    And User answered 1 question correctly
    Then User should see "Good job!"
    When ホームに遷移
    When User is taking a practiceTest
    Then User should see "問題はありません"

  @developing
  Scenario: 1日前に回答した問題は出題されないこと
    Given 問題が1個存在する
    And 1日前に問題に回答した履歴が存在する
    When User is taking a practiceTest
    Then User should see "問題はありません"

  @developing
  Scenario: 2日前に回答した問題は出題されること
    Given 問題が1個存在する
    And 2日前に問題に回答した履歴が存在する
    When User is taking a practiceTest
    And User answered 1 question correctly
    Then User should see "Good job!"

  @developing
  Scenario: 1度回答したことがある問題は回答したことがない問題より先に出題される
    Given 問題が2個存在する
    When プラクティス開始
    And 1個問題を解く
    And 時間を2日経過させる
    And ホームに遷移
    And プラクティス開始
    Then 1度回答した問題が出題される
    When 問題を解く
    Then 回答したことがない問題が出題される
    When 問題を解く
    Then "Good job!"が出力される