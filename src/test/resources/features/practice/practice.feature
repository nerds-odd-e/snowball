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
    Then User should see "Good job!"

  Scenario: Return first question should be shown if questions have been answered today
    Given User is taking a practiceTest
    When User answered 1 question correctly
    Then User should see "Good job!"
    When User is taking a practiceTest
    Then 問題1が出題される

  Scenario: If user answers wrongly, user should be shown advice page and be redirected to see completed practice page
    Given User is taking a practiceTest
    When User answered 1 question wrongly
    Then User should see Advice page
    When User clicks on Next on Advice page
    Then User should see "Good job!"

  @simple-review
  Scenario: ユーザがプラクティスを開始できること
    Given 問題が15個存在する
    When User is taking a practiceTest
    And User answered 10 questions correctly
    Then User should see "Good job!"


  @simple-review
  Scenario Outline: 解答した順にソートされること
    Given <FirstQuestion>と<SecondQuestion>が存在する
    And <FirstQuestion>の解答日時が2019年8月26日である
    And <SecondQuestion>の解答日時が2019年8月27日である
    When プラクティスを開始
    Then <FirstQuestion>が出題される
    When <FirstQuestion>に正解する
    Then <SecondQuestion>が出題される
    When <SecondQuestion>に正解する
    Then "Good job!"のメッセージが表示される
    Examples:
      | FirstQuestion | SecondQuestion |
      | 問題1           | 問題2            |
      | 問題2           | 問題1            |

  @simple-review
  Scenario: 一部未解答の問題が存在する時は未解答問題だけが出題されること
    Given There are questions with dummy options:
      | description | correctOption | category |
      | Q2          | correctOption | Retro    |
    And 今日は2019年8月27日である
    And 問題1の解答日時が2019年8月27日である
    And 問題2は解答していない
    When プラクティスを開始
    Then 問題2が出題される
    When 問題2に正解する
    Then "Good job!"のメッセージが表示される

  # space-based repetition
  @developing
  Scenario Outline: 解答した問題は解答日から起算して仕様に従った間隔で再度出題されること
    Given 問題1と問題2が存在する
    And 問題1に対して"<last-answered-date>"日前に"<answered-count>"回目の解答をした
    When プラクティスを開始
    Then 問題<number>が出題される
    Examples:
      | last-answered-date | answered-count | number |
      | 1                  | 1              | 1      |
      | 2                  | 2              | 2      |
      | 3                  | 2              | 1      |
      | 10                 | 3              | 1      |
      | 30                 | 4              | 1      |
      | 90                 | 5              | 1      |

  @developing
  Scenario: 延期された問題に解答した場合直近の解答日から起算して再出題される
    Given 問題1が存在する
    And 問題1の解答日時が2019年8月26日17時00分00秒である
    And 2019年8月27日に問題1を回答していない
    And 問題1の解答日時が2019年8月28日17時00分00秒である
    When 今日は2019年8月29日である
    And プラクティスを開始
    Then 問題2が出題される
    When 今日は2019年8月30日である
    And プラクティスを開始
    Then 問題2が出題される
    When 今日は2019年8月31日である
    And プラクティスを開始
    Then 問題1が出題される
