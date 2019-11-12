Feature:
  User can practice questions using Spaced Repetition.


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
    Given 問題が1個存在する
    When User is taking a practiceTest
    And 1個問題を解く
    And 時間を2日経過させる
    And User is taking a practiceTest
    Then 1度回答した問題が出題される
    When User answered 1 question correctly
    Then User should see "Good job!"

  @developing
  Scenario: 1度回答したことがある問題は回答したことがない問題より先に出題される
    Given 問題が2個存在する
    When User is taking a practiceTest
    And User answered 1 question correctly
    And 時間を2日経過させる
    And User is taking a practiceTest
    Then 1度回答した問題が出題される
    When User answered 1 question correctly
    Then 回答したことがない問題が出題される
    When User answered 1 question correctly
    Then User should see "Good job!"