Feature: Photographic

  @developing
  Scenario: 一度表示された問題が表示されない
    Given 問題が一問だけある
    When 訓練を開始
    And 問題を1問解く
    And 訓練開始
    Then 表示文が "Good job!"