Feature:

  Admin can add questions

  Background: Display Add Question page
    Given Add Questionを開いている
    Then "Invalid inputs found!" というメッセージが表示されていない
    And "Right answer is not selected!" というメッセージが表示されていない

  Scenario: カテゴリーの初期値は空
    Then カテゴリーは何も選択されていない

  @developing
  Scenario Outline: カテゴリを選択する
    Given Add Questionを開いている
    And Descriptionに"<description>" を入力する
    And option1に"<opt1>"を入力する
    And option2に"<opt2>"を入力する
    And option3に"<opt3>"を入力する
    And option4に"<opt4>"を入力する
    And option5に"<opt5>"を入力する
    And option6に"<opt6>"を入力する
    And "1"番目のoptionを選択する
    And adviceに "<advice>" を入力する
    And カテゴリーとして"<category>"が選択する
    When Addボタンを押す
    And 質問の一覧に遷移する
    Then 質問一覧ページのカテゴリーに "<category>" が表示される
    Examples:
      | category  |
      |           |
      | Scrum     |
      | Team      |
      | Tech      |

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

  Scenario: 質問の追加が成功する
    Given Add Questionを開いている
    And Descriptionに"What is scrum?" を入力する
    And option1に"Scrum is Rugby"を入力する
    And option2に"Scrum is Baseball"を入力する
    And option3に"Scrum is Soccer"を入力する
    And option4に"Scrum is Sumo"を入力する
    And option5に"Scrum is BasketBall"を入力する
    And option6に"Scrum is Swimming"を入力する
    And "option1"を回答として選択済み
    And advice に"Rugby is Scrum!!" を入力する
    When Addボタンを押す
    And OnlineTestを開始する
    Then "What is scrum?"という問題が出題される
    And option1に"Scrum is Rugby"が表示される
    And option2に"Scrum is Baseball"が表示される
    And option3に"Scrum is Soccer"が表示される
    And option4に"Scrum is Sumo"が表示される
    And option5に"Scrum is BasketBall"が表示される
    And option6に"Scrum is Swimming"が表示される
    And "option1"を回答として選択する
    And Answerボタンを押す
    And EndOfTheTestが表示される

  Scenario: 回答項目が6個未満の質問の追加が成功する
    Given Add Questionを開いている
    And Descriptionに"Terryさんはどこに住んでいますか？" を入力する
    And option1に"シンガポール"を入力する
    And option2に"カナダ"を入力する
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

  @developing
  Scenario: 複数選択の問題を追加できる(回答一つ)
    Given Add Questionを開いている
    And Descriptionに"What is scrum?" を入力する
    And Typeを"Multiple Choice" を選択する
    And checkbox1に"Scrum is Rugby"を入力する
    And checkbox2に"Scrum is Baseball"を入力する
    And checkbox3に"Scrum is Soccer"を入力する
    And checkbox4に"Scrum is Sumo"を入力する
    And checkbox5に"Scrum is BasketBall"を入力する
    And checkbox6に"Scrum is Swimming"を入力する
    And "checkbox1"を回答として選択済み
    And advice に"Rugby is Scrum!!" を入力する
    When Addボタンを押す
    And OnlineTestを開始する
    Then "What is scrum?"という問題が出題される
    And checkbox1に"Scrum is Rugby"が表示される
    And checkbox2に"Scrum is Baseball"が表示される
    And checkbox3に"Scrum is Soccer"が表示される
    And checkbox4に"Scrum is Sumo"が表示される
    And checkbox5に"Scrum is BasketBall"が表示される
    And checkbox6に"Scrum is Swimming"が表示される
    And "checkbox1"を回答として選択する
    And Answerボタンを押す
    And EndOfTheTestが表示される

  @developing
  Scenario: 複数選択の問題を追加できる(回答二つ)
    Given Add Questionを開いている
    And Descriptionに"What is scrum?" を入力する
    And Typeを"Multiple Choice" を選択する
    And checkbox1に"Scrum is Rugby"を入力する
    And checkbox2に"Scrum is Baseball"を入力する
    And checkbox3に"Scrum is Soccer"を入力する
    And checkbox4に"Scrum is Sumo"を入力する
    And checkbox5に"Scrum is BasketBall"を入力する
    And checkbox6に"Scrum is Swimming"を入力する
    And "checkbox1"を回答として選択済み
    And "checkbox2"を回答として選択済み
    And advice に"Rugby is Scrum!!" を入力する
    When Addボタンを押す
    And OnlineTestを開始する
    Then "What is scrum?"という問題が出題される
    And checkbox1に"Scrum is Rugby"が表示される
    And checkbox2に"Scrum is Baseball"が表示される
    And checkbox3に"Scrum is Soccer"が表示される
    And checkbox4に"Scrum is Sumo"が表示される
    And checkbox5に"Scrum is BasketBall"が表示される
    And checkbox6に"Scrum is Swimming"が表示される
    And "checkbox1"を回答として選択する
    And "checkbox2"を回答として選択する
    And Answerボタンを押す
    And EndOfTheTestが表示される

  @developing
  Scenario: Added question would be shown in question list page
    Given Question added
    | Description | What is scrum?  |
    | Option1     | Rugby           |
    | Option2     | Football        |
    Then User should see questions and options in question list page
      | Description | What is scrum?  |
      | Option1     | Rugby           |
      | Option2     | Football        |