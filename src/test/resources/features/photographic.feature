Feature: Questions will disappear after correctly answered, assuming the user has photographic memory

  Background:
    Given user "mary" has logged in successfully

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

  @developing
  Scenario: 訓練を中断する。中断した問題から再開する。訓練していない問題だけが表示される。
    Given システムに問題が2問ある
    When 訓練を開始する
    And 1問のテストに正解する
    And ホームに戻って中断する
    And 訓練を開始する
    Then 正解していない問題が1問ある



