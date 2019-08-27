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
    Then User should see "You have finished your practice for today"

  Scenario: No questions should be shown if questions have been answered today
    Given User is taking a practiceTest
    When User answered 1 question correctly
    Then User should see "You have finished your practice for today"
    When User is taking a practiceTest
    Then User should see "You have finished your practice for today"

  Scenario: If user answers wrongly, user should be shown advice page and be redirected to see completed practice page
    Given User is taking a practiceTest
    When User answered 1 question wrongly
    Then User should see Advice page
    When User clicks on Next on Advice page
    Then User should see "You have finished your practice for today"
    When User is taking a practiceTest
    Then User should see "You have finished your practice for today"

  @developing
  Scenario: 回答した問題は1日後に表示する
    Given 問題を回答し1日経過する
    When テストをうけること
    Then 同じ問題が画面に表示されること

  @developing
  Scenario: 回答した問題は2日後には表示しない
    Given 問題を回答し2日経過する
    When テストをうけること
    Then 同じ問題が画面に表示しないこと

  @developing
  Scenario: ユーザがテストを開始できること
    Given ユーザがログインした状態である
    And 問題が15個存在する
    When テストを開始
    Then 10個の問題が表示される

  @developing
  Scenario: 解答日時によってソートされること
    Given ユーザがログインした状態である
    And 問題1と問題2が存在する
    And 問題1の解答日時が2019年8月26日17時00分00秒である
    And 問題2の解答日時が2019年8月26日17時02分00秒である
    When テストを開始
    Then 問題1が出題される
