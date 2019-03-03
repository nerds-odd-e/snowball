Feature:
  結果画面でカテゴリ毎にファイナルスコアの出力をテストする事

  Scenario Outline: テストの正答率がn％の時、最終ページの回答率欄にnが表示される
    Given User is taking a onlineTest with <number_of_question> questions
    And  User answered <number_of_correct> questions correctly
    And  User answered all other questions wrong
    Then 正答率に<correct_percentage>が表示される
    And 分母に<number_of_question>が表示される
    And 分子に<number_of_correct>が表示される
    Then メッセージ欄に"<message>"が表示される
    Examples:
      | number_of_question | number_of_correct | correct_percentage | message               |
      | 2                  | 0                 | 0                  | 基本を学びなおしましょう  |
      | 2                  | 1                 | 50                 | 基本を学びなおしましょう  |
      | 7                  | 6                 | 85                 |  あともう少し           |
      | 2                  | 2                 | 100                | あなたはスクラムマスター！ |

  Scenario Outline: 1カテゴリーのみが出題される
    Given "<category>" から <number_of_questions> 題出題される
    When  User answered <number_of_wrong> questions wrong
    And  User answered <number_of_correct> questions correctly
    Then 苦手カテゴリーのメッセージ欄に"<message>"が表示される

    Examples:
      | category | number_of_questions | number_of_wrong | number_of_correct | message       |
      | 1        | 1                   | 0               | 1                 |        |
      | 1        | 2                   | 0               | 2                 |        |
      | 1        | 1                   | 1               | 0                 | Scrumをもっと勉強して |
      | 1        | 2                   | 2               | 0                 | Scrumをもっと勉強して |
      | 2        | 1                   | 1               | 0                 | Techをもっと勉強して   |
      | 3        | 1                   | 1               | 0                 | Teamをもっと勉強して   |
      | 3        | 5                   | 3               | 2                 | Teamをもっと勉強して   |
      | 3        | 5                   | 1               | 4                 |    |
