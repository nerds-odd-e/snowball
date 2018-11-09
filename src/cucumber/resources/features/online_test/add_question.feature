Feature:
  Admin can add questions

  Background: Display Add Question page
    Given Add Questionを開いている
    Then "Invalid inputs found!" というメッセージが表示されていない
    And "Right answer is not selected!" というメッセージが表示されていない

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

  @developing
  Scenario: Duplicate question
    Given Add Questionを開いている
    And Descriptionに あああ が入力されている
    And すでに あああ というQuestionが存在している
    And option1に １１１ が入力されている
    And option2に ２２２ が入力されている
    And option1 が正解として選択されている
    When Addボタンを押す
    Then すでに存在している質問です というメッセージが表示される

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



