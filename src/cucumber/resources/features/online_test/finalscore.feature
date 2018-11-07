Feature:
  結果画面でカテゴリ毎にファイナルスコアの出力をテストする事

  @developing
  Scenario:
    Given アドミンがスクラムのカテゴリの問題が10問つくられている
    When ユーザが7問正答する
    Then "スクラムをもっと勉強してください"が表示されてる事

  Scenario Outline: 最後の問題が終わったらFinal Scoreを表示する
    Given 問題が<number_of_questions>問ある時
    And ユーザーが<number_of_correct>問正解したら
    Then "<message>" が表示される

    Examples:
      | number_of_questions | number_of_correct | message                             |
      | 20                  | 20                | 20/20問 あなたの正解率は100%です。あなたはスクラムマスター！ |
      | 20                  | 17                | 17/20問 あなたの正解率は85%です。あともう少し！        |
      | 20                  | 16                | 16/20問 あなたの正解率は80%です。基本を学び直しましょう    |

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
    Then 分子に<number_of_correct>が表示される
    Examples:
      | number_of_correct |
      | 1                 |
      | 2                 |

    @now
  Scenario: テストの正答率が50％の時、最終ページの回答率欄に50が表示される
    Given User is taking a onlineTest with 2 questions
    And  User answered correctly the 1 th question page
    And  User answered wrong the 1 th question page
    Then 正答率に50が表示される

  Scenario: テストの正答率が0％の時、最終ページの回答率欄に50が表示される
    Given User is taking a onlineTest with 2 questions
    And  User answered wrong the 2 th question page
    Then 正答率に0が表示される

