Feature:
  Practiceで出題する問題がなくなったときに、
  もっと問題が解けるようにする

  @developing
  Scenario Outline: Practiceで出題する問題がない時のみ、retrainingボタンが表示される
    Given 問題が<全ての問題数>問存在する
    And Practiceで出題される問題が<Practiceで出題される問題数>問存在する
    When ホーム画面に遷移
    Then 「retraining」ボタンが<retrainingボタン>
  Examples:
  | 全ての問題数  | Practiceで出題される問題数   | retrainingボタン |
  |  1          |   0                       | 表示される         |
  |  1          |   1                       | 表示されない       |

  @developing
  Scenario Outline: retrainingを開始すると、最大10問の問題が表示される
    Given 問題は<全ての問題数>個存在するが、Practiceで出題する問題がない
    When retrainingを開始する
    Then 問題が表示される
    When User answered <回答する問題数> question correctly
    Then User should see "Good job!"
    Examples:
      | 全ての問題数 | 回答する問題数 |
      | 1      | 1       |
      | 11     | 10      |
