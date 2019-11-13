Feature:
  User can practice questions using Spaced Repetition.

  @developing
  Scenario Outline: 2日以内に回答した問題は出題しない
    Given 問題が<全ての問題数>個存在する
    And <前回の回答日>日前に回答した問題が<前回回答した問題数>個存在する
    When User is taking a practiceTest
    Then User should see "<result message>"
    Examples:
      | 全ての問題数 | 前回の回答日 | 前回回答した問題数   | result message   |
      |  1         | 0           |  1                |  問題はありません  |
      |  1         | 1           |  1                |  問題はありません  |
      |  1         | 2           |  1                |  Question        |


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