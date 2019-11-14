Feature:
  User can practice questions using Spaced Repetition.

  @developing
  Scenario Outline: 2日以内に回答した問題は出題しない
    Given 問題が<全ての問題数>個存在する
    And <前回の回答日>日前に問題を<前回回答した問題数>個回答した
    When User is taking a practiceTest
    Then User should see "<result message>"
    Examples:
      | 全ての問題数 | 前回の回答日 | 前回回答した問題数   | result message   |
      |  1         | 0           |  1                |  問題はありません  |
      |  1         | 1           |  1                |  問題はありません  |
      |  1         | 2           |  1                |  Question        |

  @developing
  Scenario: 1度回答したことがある問題は回答したことがない問題より先に出題される
    Given There are questions with dummy options:
      | description     | correctOption     | category |
      | What is scrum?  | None of the above | Scrum    |
      | What is TDD?    | None of the above | Scrum    |
    When User is taking a practiceTest
    And What is scrum? の問題を解く
    And ホームに戻って中断する
    And 時間を2日経過させる
    And User is taking a practiceTest
    Then What is scrum? の問題が出る

