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
#  @now
  Scenario: ユーザがプラクティスを開始できること
    Given 問題が15個存在する
    And ユーザがログインした状態である
    When プラクティスを開始
    And 問題に10回正解する
    Then "お疲れ様でした"のメッセージが表示される

  @developing
  @simple-review
  Scenario: 解答した順にソートされること
    Given 問題1と問題2が存在する
    And ユーザがログインした状態である
    And 問題1に解答する
    And 問題2に解答する
    When プラクティスを開始
    Then 問題1が出題される
    When 問題1に正解する
    Then 問題2が出題される
    When 問題2に正解する
    Then "お疲れ様でした"のメッセージが表示される

  @developing
  @simple-review
  Scenario: 一部未解答の問題が存在する時は未解答問題だけが出題されること
    Given 問題1と問題2が存在する
    And ユーザがログインした状態である
    And 今日は2019年8月27日である
    And 問題1の解答日時が2019年8月27日である
    And 問題2は解答していない
    When プラクティスを開始
    Then 問題2が出題される
    When 問題2に正解する
    Then "お疲れ様でした"のメッセージが表示される

  # space-based repetation
  @developing
  Scenario: 回答した問題は回答日から起算して仕様に従った間隔で再度出題されること
    Given ユーザがログインした状態である
    And 問題1と問題2が存在する
    And 問題1の解答日時が2019年8月26日17時00分00秒である
    And 問題2は未解答である
    And 現在は2019年8月27日である
    When "Start Practice"をする
    Then 問題1が出題される

  @developing
  Scenario: 再度出題された問題に回答しなかった場合翌日も出題される
    Given ユーザがログインした状態である
    And 問題1と問題2が存在する
    And 問題1の解答日時が2019年8月26日17時00分00秒である
    And 問題2には一度も解答していない
    And 2019年8月27日に問題1を回答していない
    And 今日は2019年8月28日である
    When テストを開始
    Then 問題1が出題される

  @developing
  Scenario: 延期された問題に解答した場合直近の解答日から起算して再出題される
    Given ユーザがログインした状態である
    And 問題1と問題2が存在する
    And 問題1の解答日時が2019年8月26日17時00分00秒である
    And 問題2には一度も解答していない
    And 2019年8月27日に問題1を回答していない
    And 問題1の解答日時が2019年8月28日17時00分00秒である
    When 今日は2019年8月29日である
    And テストを開始
    Then 問題2が出題される
    When 今日は2019年8月30日である
    And テストを開始
    Then 問題2が出題される
    When 今日は2019年8月31日である
    And テストを開始
    Then 問題1が出題される
