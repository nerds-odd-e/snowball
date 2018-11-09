Feature:
  結果画面でカテゴリ毎にファイナルスコアの出力をテストする事

  @developing
  Scenario:
    Given アドミンがスクラムのカテゴリの問題が10問つくられている
    When ユーザが7問正答する
    Then "スクラムをもっと勉強してください"が表示されてる事

  Scenario Outline: テストが全n問のとき最終ページの分母にnが表示される
    Given User is taking a onlineTest with <number_of_questions> questions
    And  User answered correctly the <number_of_questions> th question page
    Then 分母に<number_of_questions>が表示される
    Examples:
      | number_of_questions |
      | 1                   |
      | 2                   |

  Scenario Outline: テストの正答数が2問のとき最終ページの分子に2が表示される
    Given User is taking a onlineTest with <number_of_correct> questions
    And  User answered correctly the <number_of_correct> th question page
    Then "End Of Test" should be shown
    And 分子に<number_of_correct>が表示される
    Examples:
      | number_of_correct |
      | 1                 |
      | 2                 |

  @now
  Scenario Outline: テストの正答率がn％の時、最終ページの回答率欄にnが表示される
    Given User is taking a onlineTest with <number_of_question> questions
    And  User answered correctly the <number_of_correct> th question page
    And  User answered wrong the <number_of_wrong> th question page
    Then 正答率に<correct_percentage>が表示される
    Examples:
      | number_of_question | number_of_wrong | number_of_correct | correct_percentage |
      | 2                  | 2               | 0                 | 0                  |
      | 2                  | 1               | 1                 | 50                 |

  @now
  Scenario Outline: テストの正答率に応じて、メッセージが表示される
    Given User is taking a onlineTest with <number_of_question> questions
    And  User answered correctly the <number_of_correct> th question page
    And  User answered wrong the <number_of_wrong> th question page
    Then メッセージ欄に"<message>"が表示される
    Examples:
      | number_of_question | number_of_correct | number_of_wrong | message       |
      | 5                  | 1                 | 4               | 基本を学びなおしましょう  |
      | 7                  | 6                 | 1               | あともう少し        |
      | 1                  | 1                 | 0               | あなたはスクラムマスター！ |

  @developing
  Scenario Outline: 1カテゴリーのみが出題される
    Given "<category>" から <number_of_questions> 題出題される
    When  User answered wrong the <number_of_wrong> th question page
    And  User answered correctly the <number_of_correct> th question page
    Then 苦手カテゴリーのメッセージ欄に"<message>"が表示される

    Examples:
      | category | number_of_questions | number_of_wrong | number_of_correct | message       |
      | scrum    | 1                   | 0               | 1                 | よくできました       |
      | scrum    | 1                   | 1               | 0                 | scrumをもっと勉強して |
      | TDD      | 1                   | 1               | 0                 | TDDをもっと勉強して   |
