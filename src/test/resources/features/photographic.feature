
Feature: Questions will disappear after correctly answered, assuming the user has photographic memory

  @developing
  Scenario: 一度表示された問題が表示されない
    Given 質問1ある
    When 訓練を開始
    And 問題を1問解く
    And 訓練開始
    Then 表示文が "Good job!"

  @developing
  Scenario: If there are 11 questions, the 1st time user finishes 10, and the 2nd time finishes 1, and no more question can be seen
    Given User is taking a onlinePractice with 10 questions and there are enough questions
    And User answered 10 questions correctly
    Then 表示文が "Good job!"
    And User click HOME button
    Then I'm on the admin dashboard
    And User is taking a onlinePractice with 1 questions and there are enough questions
    And User answered 1 questions correctly
    Then 表示文が "Good job!"
    And User click HOME button
    And User is taking a onlinePractice with 0 questions and there are enough questions
    Then 表示文が "Good job!"

  @developing
  Scenario Outline: ユーザが、問題を解くときにまだ正解してない問題を表示する
    Given 質問<total_questions>ある
    Given ユーザは、解答されている質問<answered_questions>個がある
    Given 解答されてない質問<not_answered_questions>個がある
    When 3問のテストを行う
    Then 質問<questions_to_display>個が表示される
    Examples:
      | answered_questions | not_answered_questions | questions_to_display | total_questions |
      | 0                  | 3                      | 3                    | 3               |
      | 2                  | 1                      | 1                    | 3               |



