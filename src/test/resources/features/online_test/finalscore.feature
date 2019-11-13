Feature:
  結果画面でカテゴリ毎にファイナルスコアの出力をテストする事

  Background:
    Given user "mary" has logged in successfully

  Scenario Outline: テストの正答率がn％の時、最終ページの回答率欄にnが表示される
    And User is taking a onlineTest with <number_of_question> questions and there are enough questions
    And  User answered <number_of_correct> questions correctly
    And  User answered all other questions wrong
    Then 正答率に<correct_percentage>が表示される
    And 分母に<number_of_question>が表示される
    And 分子に<number_of_correct>が表示される
    Then メッセージ欄に"<message>"が表示される
    Examples:
      | number_of_question | number_of_correct | correct_percentage | message       |
      | 2                  | 1                 | 50                 | 基本を学びなおしましょう  |
      | 7                  | 6                 | 85                 | あともう少し        |
      | 2                  | 2                 | 100                | あなたはスクラムマスター！ |

