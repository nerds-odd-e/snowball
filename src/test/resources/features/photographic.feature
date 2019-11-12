Feature: Questions will disappear after correctly answered, assuming the user has photographic memory

  @developing
  Scenario: 一度表示された問題が表示されない
    Given 問題が一問だけある
    When 訓練を開始
    And 問題を1問解く
    And 訓練再開始
    Then 表示文が "Good job!"

  @developing
  Scenario Outline: ユーザが、問題を解くときにまだ正解してない問題を表示する
    Given 質問<total_questions>ある
    Given システムは、テストユーザ（"test"）を利用する。
    Given ユーザは、解答されている質問<answered_questions>個がある
    Given 解答されてない質問<not_answered_questions>個がある
    When 3問のテストを行う
    Then 質問<questions_to_display>個が表示される
    Examples:
      | answered_questions | not_answered_questions | questions_to_display | total_questions |
      | 0                  | 3                      | 3                    | 3               |
      | 2                  | 1                      | 1                    | 3               |
