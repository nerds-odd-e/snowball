
Feature: Questions will disappear after correctly answered, assuming the user has photographic memory

  Background:
    Given user "mary" has logged in successfully

  Scenario: 正解した問題が表示されない
    Given 質問1ある
    When 訓練を開始する
    And User answered 1 question correctly
    And ホームに戻る
    And 訓練を開始する
    Then 表示文が "出題する問題がありません"

  Scenario: 正解した問題が表示されない(11問以上)
    Given 質問11ある
    When 訓練を開始する
    And User answered 10 question correctly
    And ホームに戻る
    When 訓練を開始する
    And User answered 1 question correctly
    Then 表示文が "Good job!"
    And ホームに戻る

  Scenario Outline: 訓練を中断する。中断した問題から再開する。訓練していない問題だけが表示される。
    Given 質問3ある
    When 訓練を開始する
    And User answered <answer_correctly_count> question correctly
    And ホームに戻って中断する
    And 訓練を開始する
    Then 正解していない問題が<answer_incorrectly_count>問ある
    Examples:
      | answer_correctly_count | answer_incorrectly_count |
      | 0                      | 3                        |
      | 1                      | 2                        |


