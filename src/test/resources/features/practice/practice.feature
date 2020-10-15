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

  Scenario: ユーザがプラクティスを開始できること
    Given 問題が15個存在する
    When User is taking a practiceTest
    And User answered 10 questions correctly
    Then User should see "Good job!"

  @developing
  Scenario: 未回答の問題が表示される
    Given User is taking a practiceTest
    And 未回答の問題だけがある
    When 全てのユーザが特訓を開始すると
    Then 未回答の問題が表示される

  @developing
  Scenario: 回答してから５日たった問題が表示される
    Given User is taking a practiceTest
    And 回答してから５日たった問題がある
    When 全てのユーザが特訓を開始すると
    Then 回答してから５日たった問題が表示される

  @developing
  Scenario: 回答済みの問題を優先的に表示する
    Given User is taking a practiceTest
    And 回答して５日たった問題と未回答の問題がある
    When 全てのユーザが特訓を開始すると
    Then 回答して５日たった問題が優先して表示される

  @developing
  Scenario: ある問題に正答した場合、５日以上経つと同じ問題が再度表示される
    Given User is taking a practiceTest
    And 未回答の問題がある
    And 正答してから５日未満の問題がある
    And 正答して５日以上たった問題がある
    When 全てのユーザが特訓を開始すると
    Then １件目に正答して５日以上たった問題が表示される
    And ２件目に未回答の問題が表示される
    And 正答してから５日未満の問題は表示されない

  @developing
  # @now
  Scenario: ある問題に誤答した場合、１日後に同じ問題が再度表示される
    Given 問題が登録されている
    And 誤答して1日たった問題と未回答の問題がある
    When User is taking a practiceTest
    Then 誤答して１日たった問題が優先して表示される

  @developing
  Scenario: 正答した問題と誤答した問題が混在している場合、誤答した問題が優先的に表示される
    Given User is taking a practiceTest
    And 正答して５日以上たった問題がある
    And 未回答の問題がある
    And 誤答して１日以上たった問題がある
    When 全てのユーザが特訓を開始すると
    Then １件目に誤答して１日以上たった問題が表示される
    And ２件目に正答して５日以上たった問題が表示される
    And ３件目に未回答の問題が表示される

  @developing
  Scenario: ある問題に１回連続で正答した場合、２日後に同じ問題が再度表示される
    Given User is taking a practiceTest
    And １回連続で正答して２日たった問題と未回答の問題がある
    When 全てのユーザが特訓を開始すると
    Then １回連続で正答して２日たった問題が表示される

  @developing
  Scenario: ある問題に２回連続で正答した場合、５日後に同じ問題が再度表示される
    Given User is taking a practiceTest
    And ２回連続で正答して５日たった問題と未回答の問題がある
    When 全てのユーザが特訓を開始すると
    Then ２回連続で正答して５日たった問題が表示される

  @developing
  Scenario: ある問題に３回連続で正答した場合、１０日後に同じ問題が再度表示される
    Given User is taking a practiceTest
    And ３回連続で正答して１０日たった問題と未回答の問題がある
    When 全てのユーザが特訓を開始すると
    Then ３回連続で正答して１０日たった問題が表示される

  @developing
  Scenario: ある問題に４回以上連続で正答した場合、１０日後に同じ問題が再度表示される
    Given User is taking a practiceTest
    And ４回以上連続で正答して１０日たった問題と未回答の問題がある
    When 全てのユーザが特訓を開始すると
    Then ４回以上連続で正答して１０日たった問題が表示される