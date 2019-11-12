Feature: Questions will disappear after correctly answered, assuming the user has photographic memory

  @developing
  Scenario: 一度表示された問題が表示されない
    Given 質問1ある
    When 訓練を開始
    And 問題を1問解く
    And 訓練再開始
    Then 表示文が "Good job!"

  @developing
  Scenario: If there are 11 questions, the 1st time user finishes 10, and the 2nd time finishes 1, and no more question can be seen
    Given There is a user with email "john@example.com" and password "abcd1002"
    And I login with "john@example.com" and "abcd1002"
    And user should see the dashboard
    Given 質問11ある
    When User click the Start Practice button
    And User answered 10 questions correctly
    Then 表示文が "Good job!"
    And User click HOME button
    And user should see the dashboard
    When User click the Start Practice button
    And User answered 1 questions correctly
    Then 表示文が "Good job!"
    And User click HOME button

#  @now
#  Scenario Outline: ユーザが、問題を解くときにまだ正解してない問題を表示する
#    Given 問題<total_questions>ある
#    Given システムは、テストユーザ（"test"）を利用する。
#    Given ユーザは、正解している問題が<answered_questions>問ある
#    Given 特訓前に正解していない問題が<not_answered_questions_before>問ある
#    When 3問のテストに正解し中断する
#    Then 特訓後に正解していない問題が<not_answered_questions_after>問ある
#    Examples:
#      | answered_questions | not_answered_questions_before | not_answered_questions_after | total_questions |
#      | 0                  | 3                             | 3                            | 3               |
#      | 2                  | 1                             | 1                            | 3               |

  @developing
  Scenario: 訓練を中断する。中断した問題から再開する。訓練していない問題だけが表示される。
    Given 問題が11問ある
    When 3問のテストに正解し中断する
    Then 問題が8問ある



