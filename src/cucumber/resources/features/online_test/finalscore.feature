Feature:
  結果画面でカテゴリ毎にファイナルスコアの出力をテストする事

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

  Scenario Outline: テストの正答率がn％の時、最終ページの回答率欄にnが表示される
    Given User is taking a onlineTest with <number_of_question> questions
    And  User answered correctly the <number_of_correct> th question page
    And  User answered wrong the <number_of_wrong> th question page
    Then 正答率に<correct_percentage>が表示される
    Examples:
      | number_of_question | number_of_wrong | number_of_correct | correct_percentage |
      | 2                  | 2               | 0                 | 0                  |
      | 2                  | 1               | 1                 | 50                 |

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

    @now
  Scenario Outline: 1カテゴリーのみが出題される
    Given "<category>" から <number_of_questions> 題出題される
    When  User answered wrong the <number_of_wrong> th question page
    And  User answered correctly the <number_of_correct> th question page
    Then 苦手カテゴリーのメッセージ欄に"<message>"が表示される

    Examples:
      | category | number_of_questions | number_of_wrong | number_of_correct | message       |
      | 1        | 1                   | 0               | 1                 | よくできました       |
      | 1        | 2                   | 0               | 2                 | よくできました       |
      | 1        | 1                   | 1               | 0                 | Scrumをもっと勉強して |
      | 1        | 2                   | 2               | 0                 | Scrumをもっと勉強して |
      | 2        | 1                   | 1               | 0                 | Techをもっと勉強して   |
      | 3        | 1                   | 1               | 0                 | Teamをもっと勉強して   |
      | 3        | 5                   | 3               | 2                 | Teamをもっと勉強して   |
      | 3        | 5                   | 1               | 4                 | よくできました   |

  @developing
  Scenario Outline: 2カテゴリーから出題される
    Given "<category1>" から <number_of_questions1> 題、"<category2>" から <number_of_questions2> 題出題される
    When  User answered wrong the <number_of_wrong> th question page
    And  User answered correctly the <number_of_correct> th question page
    Then 苦手カテゴリーのメッセージ欄に"<message>"が表示される
    Examples:
      | category1 | category2 | number_of_questions1 | number_of_questions2 | number_of_wrong | number_of_correct | message           |
      | scrum     | TDD       | 1                    | 1                    | 0               | 2                 | よくできました           |
      | scrum     | TDD       | 1                    | 1                    | 2               | 0                 | scrumとTDDをもっと勉強して |


  @developing
  Scenario Outline: 全てのカテゴリの正答率が80%を超える場合はAdviceが表示されない
    Given Scrumの正答率が <scrum_correct_percent>
    And Techの正答率が <tech_correct_percent>
    And Teamの正答率が <team_correct_percent>
    Then Adviceの表示・非表示が切り替わる（ <advice> ）
    Examples:
      | scrum_correct_percent | tech_correct_percent  | team_correct_percent  | advice |
      | 80                    | 80                    | 80                    | false  |
      | null                  | 80                    | 80                    | false  |
      | 80                    | null                  | 80                    | false  |
      | 80                    | 80                    | null                  | false  |
      | 80                    | null                  | null                  | false  |
      | null                  | 80                    | null                  | false  |
      | null                  | null                  | 80                    | false  |
      | 79                    | 79                    | 79                    | true   |
      | 79                    | 79                    | 80                    | true   |
      | 79                    | 80                    | 80                    | true   |
      | 0                     | 0                     | 0                     | true   |
      | null                  | null                  | null                  | false  |

  @developing
  Scenario Outline: markdownが表示される
    Given markdownの文字列 <markdown>
    Then  <html> が表示される
    Examples:
      | markdown                          | html                                            |
      | # abc                             | <h1>abc</h1>                                    |
      | [abc](https://www.yahoo.co.jp)    | <a herf="https://www.yahoo.co.jp">abc</a>       |

