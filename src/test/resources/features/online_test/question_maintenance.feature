Feature:
  User can add private questions

  Background: Display Add Question page
    Given there is a question category "Scrum"

  Scenario: TerryがPrivateな質問を追加できる
    Given "Terry"ユーザが登録されている
    And "Terry"がログインしている
    When 質問の内容を入力してAddボタンを押す
    Then "Terry"の特訓トップページに追加した質問が表示される

  Scenario: カテゴリを選択する
    Given "Terry"ユーザが登録されている
    Given "Terry"がログインしている
    Given Add Questionを開いている
    Given Descriptionに"description" を入力する
    And option1に"option1"を入力する
    And option2に"option2"を入力する
    And "1"番目のoptionを選択する
    And カテゴリーとして"Scrum"を選択する
    When Addボタンを押す
    And 特訓のトップページに遷移する
    Then カテゴリーに "Scrum" が表示される

  Scenario Outline: Validation
    Given Add Questionを開いている
    And Descriptionに"<description>" を入力する
    And option1に"<opt1>"を入力する
    And option2に"<opt2>"を入力する
    And option3に"<opt3>"を入力する
    And option4に"<opt4>"を入力する
    And option5に"<opt5>"を入力する
    And option6に"<opt6>"を入力する
    And "<selected>"番目のoptionを選択する
    And adviceに "<advice>" を入力する
    When Addボタンを押す
    Then "<messages>"というメッセージが表示される

    Examples:
      | description | opt1 | opt2 | opt3 | opt4 | opt5 | opt6 | selected | advice | messages                      |
      |             |      |      |      |      |      |      |          |        | Invalid inputs found!         |
      | xxx         | aaa  | bbb  |      |      |      |      |          |        | Right answer is not selected! |
      | xxx         | aaa  | bbb  |      |      |      |      | 4        |        | Invalid inputs found!         |
      | xxx         |      | bbb  |      |      |      |      | 2        |        | Invalid inputs found!         |
      | xxx         |      |      | ccc  |      |      |      | 3        |        | Invalid inputs found!         |

  Scenario Outline: 質問の追加が成功する
    Given Add Questionを開いている
    And Descriptionに"<description>" を入力する
    And option1に"<opt1>"を入力する
    And option2に"<opt2>"を入力する
    And option3に"<opt3>"を入力する
    And option4に"<opt4>"を入力する
    And option5に"<opt5>"を入力する
    And option6に"<opt6>"を入力する
    And is-publicをチェックする
    And "<selected>"番目のoptionを選択する
    And advice に"<advice>" を入力する
    When Addボタンを押す
    And OnlineTestを開始する
    Then "<description>"という問題が出題される
    And option1に"<opt1>"が表示される
    And option2に"<opt2>"が表示される
    And option3に"<opt3>"が表示される
    And option4に"<opt4>"が表示される
    And option5に"<opt5>"が表示される
    And option6に"<opt6>"が表示される
    And "option<selected>"を回答として選択する
    And Answerボタンを押す
    And EndOfTheTestが表示される

    Examples:
      | description    | opt1           | opt2              | opt3            | opt4          | opt5                | opt6              | selected | advice           |
      | What is scrum? | Scrum is Rugby | Scrum is Baseball | Scrum is Soccer | Scrum is Sumo | Scrum is BasketBall | Scrum is Swimming | 1        | Rugby is Scrum!! |

  Scenario Outline: 質問の追加が成功する(イメージ図添付ありパターン)
    Given Add Questionを開いている
    And Descriptionに"<description>" を入力する
    And option1に"<opt1>"を入力する
    And option2に"<opt2>"を入力する
    And option3に"<opt3>"を入力する
    And option4に"<opt4>"を入力する
    And option5に"<opt5>"を入力する
    And option6に"<opt6>"を入力する
    And is-publicをチェックする
    And attached-fileを選択する
    And "option<selected>"を回答として選択する
    And Addボタンを押す
    And OnlineTestを開始する
    And "<description>"という問題が出題される
    And 2を回答として選択する
    And Answerボタンを押す
    Then Advice画面が表示される
    And attached-fileが表示される
    And attached-fileを選択する
    Then attached-fileを開く

    Examples:
      | description    | opt1           | opt2              | opt3            | opt4          | opt5                | opt6              | selected | advice           |
      | What is scrum? | Scrum is Rugby | Scrum is Baseball | Scrum is Soccer | Scrum is Sumo | Scrum is BasketBall | Scrum is Swimming | 1        | Rugby is Scrum!! |

  Scenario Outline: 質問の追加が成功する(イメージ図添付なしパターン)
    Given Add Questionを開いている
    And Descriptionに"<description>" を入力する
    And option1に"<opt1>"を入力する
    And option2に"<opt2>"を入力する
    And option3に"<opt3>"を入力する
    And option4に"<opt4>"を入力する
    And option5に"<opt5>"を入力する
    And option6に"<opt6>"を入力する
    And is-publicをチェックする
    And "option<selected>"を回答として選択する
    And Addボタンを押す
    And OnlineTestを開始する
    And "<description>"という問題が出題される
    And 2を回答として選択する
    And Answerボタンを押す
    Then Advice画面が表示される
    And attached-fileが空欄になっている

    Examples:
      | description    | opt1           | opt2              | opt3            | opt4          | opt5                | opt6              | selected | advice           |
      | What is scrum? | Scrum is Rugby | Scrum is Baseball | Scrum is Soccer | Scrum is Sumo | Scrum is BasketBall | Scrum is Swimming | 1        | Rugby is Scrum!! |



  Scenario: 回答項目が6個未満の質問の追加が成功する
    Given Add Questionを開いている
    And Descriptionに"Terryさんはどこに住んでいますか？" を入力する
    And option1に"シンガポール"を入力する
    And option2に"カナダ"を入力する
    And is-publicをチェックする
    And "option1"を回答として選択済み
    And advice に"Rugby is Scrum!!" を入力する
    When Addボタンを押す
    And OnlineTestを開始する
    Then "Terryさんはどこに住んでいますか？"という問題が出題される
    And option1に"シンガポール"が表示される
    And option2に"カナダ"が表示される
    And "option1"を回答として選択する
    And Answerボタンを押す
    And EndOfTheTestが表示される

  Scenario: Added question would be shown in question list page
    Given There are questions with dummy options:
      | description    | correctOption     | category |
      | What is scrum? | None of the above | Scrum    |
    Then User should see questions and options in question list page with correct one highlighted
      | description | What is scrum?    |
      | option1     | Food              |
      | option2     | None of the above |

  Scenario: 公開問題をAdminが承認すると承認済みになる
    Given 未承認の問題がある
      | description | What is scrum? |
      | option1     | Rugby          |
      | option2     | Football       |
    When Adminが承認する
    Then 問題が承認済みになる

  Scenario Outline: 公開範囲に従って追加した質問が表示される
    Given "Terry"ユーザが登録されている
    And "admin2"ユーザが登録されている
    And "<is-public>"な質問が"<creator>"によって登録されている
    When "<user>"でログインする
    Then ダッシュボードに問題が"<is-display>"
    Then オンラインテストに問題が"<is-display>"
    Then プラクティスに問題が"<is-display>"

    Examples:
      | is-public | creator | user   | is-display |
      | public    | Terry   | Terry  | 表示される      |
      | public    | Terry   | admin2 | 表示される      |
      | private   | Terry   | Terry  | 表示される      |
      | private   | Terry   | admin2 | 表示されない     |

  Scenario: 質問一覧に登録済みのイメージ図が表示される
    Given Add Questionを開いている
    And Descriptionに"<description>" を入力する
    And option1に"<opt1>"を入力する
    And option2に"<opt2>"を入力する
    And is-publicをチェックする
    And attached-fileを選択する
    And 1を回答として選択する
    When Addボタンを押す
    Then Question Listが表示される
    And attached-fileが表示される
    When attached-fileを選択する
    Then attached-fileを開く

  Scenario: 質問一覧にイメージ図がない質問が表示される
    Given Add Questionを開いている
    And Descriptionに"<description>" を入力する
    And option1に"<opt1>"を入力する
    And option2に"<opt2>"を入力する
    And is-publicをチェックする
    And 1を回答として選択する
    When Addボタンを押す
    Then Question Listが表示される
    And attached-fileが空欄になっている
