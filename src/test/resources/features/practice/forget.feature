Feature:
  User can practice questions using Spaced Repetition.

  Background:
    Given user "mary" has logged in successfully

  @developing
  Scenario Outline: 2日以内に回答した問題は出題しない
    Given 問題が<全ての問題数>個存在する
    And <前回の回答日>日前に問題を<前回回答した問題数>個回答した
    When User is taking a practiceTest
    Then User should see "<result message>"
    Examples:
      | 全ての問題数 | 前回の回答日 | 前回回答した問題数 | result message |
      | 1      | 0      | 1         | 問題はありません       |
      | 1      | 1      | 1         | 問題はありません       |
      | 1      | 2      | 1         | Question       |

  @developing
  Scenario: 1度回答したことがある問題は回答したことがない問題より先に出題される
    Given There are questions with an answered time:
      | description    | correctOption     | category | answered_time |
      | What is scrum? | None of the above | Scrum    | null          |
      | What is TDD?   | None of the above | Scrum    | 1days ago     |
      | What is CI?    | None of the above | Scrum    | 2days ago     |
    When User is taking a practiceTest
    Then "What is CI?" の問題が出る


