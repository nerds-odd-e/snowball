Feature:
  User can take an online test :)

  Scenario: Displaying the question
    Given Add a question "What is scrum?" with dummy options
    And User is on the first question
    And User should see a question and options
      | description | What is scrum?    |
      | option1     | Food              |
      | option2     | Drink             |
      | option3     | Country           |
      | option4     | Animal            |
      | option5     | None of the above |

  Scenario Outline: User navigates to advice or next question page
    Given User is taking a onlineTest with 2 questions
    And User is on the first question
    When User chooses the "<selected_option>" answer
    And User clicks the answer button
    Then It should move to "<page_content>" page
    Examples:
      | selected_option | page_content |
      | wrongOption     | Advice       |
      | correctOption   | Question     |

  Scenario Outline: advice page should include clear feedback
    Given Add a question "スクラムとは何ですか？" with dummy options and advice "Read the Scrum Guide again, please"
    And User is on the first question
    When User chooses the "<incorrect option>" answer
    And User clicks the answer button
    Then User should see "correct" option "#28a745" and text "None of the above"
    And User should see "selected incorrect" option "#dc3545" and text "<incorrect option>"
    And User should see "Read the Scrum Guide again, please"

    Examples:
      | incorrect option |
      | Food             |
      | Drink            |
      | Country          |
      | Animal           |

  Scenario Outline: User goes to end of test if he has answered all questions
    Given User is taking a onlineTest with 3 questions
    And User answered correctly the <number_of_questions> th question page
    Then "<page_content>" should be shown

    Examples:
      | number_of_questions | page_content |
      | 2                   | Question     |
      | 3                   | End Of Test  |

  Scenario: User goes to end of test if he has answered last questions wrong
    Given User is taking a onlineTest with 3 questions
    And User answered correctly the 2 th question page
    When User chooses the "wrongOption" answer
    And User clicks the answer button
    And User clicks the next button
    Then "End Of Test" should be shown

  Scenario Outline: 一度アドバイスページから質問ページに戻ってもう一度Answerを押した時に正しい質問ページに遷移する
    Given User is taking a onlineTest with 2 questions
    And User is on the first question
    When User chooses the "<incorrect option>" answer
    And User clicks the answer button
    Then アドバイスページにいる
    When ユーザーがブラウザの戻るを実行する
    Then 質問1の画面に遷移する
    When User chooses the "<incorrect option>" answer
    And User clicks the answer button
    Then 質問2の画面に遷移する

    Examples:
      | incorrect option |
      | wrongOption      |

  Scenario Outline: 一度一つ前のアドバイスページに戻ってもう一度Nextを押した時に正しい質問ページに遷移する
    Given User is taking a onlineTest with 2 questions
    And User is on the first question
    When User chooses the "<incorrect option>" answer
    And User clicks the answer button
    Then アドバイスページにいる
    When User clicks the next button
    Then 質問2の画面に遷移する
    When ユーザーがブラウザの戻るを実行する
    And User clicks the next button
    Then 質問2の画面に遷移する

    Examples:
      | incorrect option |
      | wrongOption      |

  Scenario Outline: 一度アドバイスページから質問ページに戻ってもう一度Answerを押した時に "You answered previous question twice" と表示する
    Given User is taking a onlineTest with 2 questions
    And User is on the first question
    When User chooses the "<incorrect option>" answer
    And User clicks the answer button
    Then アドバイスページにいる
    When ユーザーがブラウザの戻るを実行する
    Then 質問1の画面に遷移する
    When User chooses the "<incorrect option>" answer
    And User clicks the answer button
    Then 質問2の画面に遷移する
    And "You answered previous question twice" should be shown
    And text "You answered previous question twice" is color red

    Examples:
      | incorrect option |
      | wrongOption      |

  Scenario Outline: 一度アドバイスページから質問ページに戻ってもう一度Answerを押した時にEnd of Testページで "You answered previous question twice" と表示する
    Given User is taking a onlineTest with 1 questions
    And User is on the first question
    When User chooses the "<incorrect option>" answer
    And User clicks the answer button
    Then アドバイスページにいる
    When ユーザーがブラウザの戻るを実行する
    Then 質問1の画面に遷移する
    When User chooses the "<incorrect option>" answer
    And User clicks the answer button
    Then "End Of Test" should be shown
    And "You answered previous question twice" should be shown
    And text "You answered previous question twice" is color red

    Examples:
      | incorrect option |
      | wrongOption      |

  Scenario Outline: 質問ページに移動した時に、回答選択肢が何も選択されていない
    Given Add Questionを開いている
    And Descriptionに"What is scrum?" を入力する
    And Typeを"<回答type>" を選択する
    And "<選択肢1>"に"Scrum is Rugby"を入力する
    And "<選択肢2>"に"Scrum is Baseball"を入力する
    And "<選択肢1>"を回答として選択済み
    When Addボタンを押す
    And OnlineTestを開始する
    Then "What is scrum?"という問題が出題される
    And 0つ"<ボタンtype>"の回答が選択されている事

    Examples:
      | 回答type          | 選択肢1      | 選択肢2      | ボタンtype  |
      | Single Choice   | option1   | option2   | radio    |
      | Multiple Choice | checkbox1 | checkbox2 | checkbox |

  Scenario: 何も選択しないでanswerをクリックした場合、次の出題に移動しない
    Given User is taking a onlineTest with 1 questions
    And 質問1の画面に遷移する
    When User clicks the answer button
    Then 質問1の画面に遷移する

  Scenario: 複数選択可能な問題で何も選択しないでanswerをクリックした場合、次の出題に移動しない
    Given User is taking a onlineTest with 1 multiple choice questions
    And 質問1の画面に遷移する
    When User clicks the answer button
    Then 質問1の画面に遷移する

  Scenario: アドバイスページで正解2つと誤った回答1つがわかる
    Given Add a question "スクラムに含まれる要素は何ですか？（option1とoption4が正解）" of multiple answers
    And User is on the first question
    When User chooses "option1" and "option2" answers
    And User clicks the answer button
    Then User should see "correct" option "#28a745" and text "option4"
    And User should see "selected incorrect" option "#dc3545" and text "option1"

  @developing
  Scenario: アドバイスページで正解2つと誤った回答2つがわかる
    Given Add a question "スクラムに含まれる要素は何ですか？（option1とoption4が正解）" of multiple answers
    And User is on the first question
    When User chooses "option1" and "option2" answers
    And User clicks the answer button
    Then User should see "selected correct" option "#28a745" and text "option1"
    And User should see "selected incorrect" option "#dc3545" and text "option2"
    And User should see "unselected correct" option "#333" and text "option3"
    And User should see "unselected incorrect" option "#28a745" and text "option4"

  @developing
  Scenario Outline: 問題ページで正解2つを選択して、次の問題に移動
    Given Add a question "スクラムに含まれる要素は何ですか？（option1とoption4が正解）" of multiple answers with <number_of_questions> questions
    And User is on the first question
    When User chooses "option1" and "option4" answers
    And User clicks the answer button
    Then It should move to "<page_content>" page

    Examples:
      | number_of_questions | page_content |
      | 2                   | Question     |
      | 1                   | End Of Test  |

  @developing
  Scenario: 問題ページで正解1つと不正解1つを選択して、アドバイスページで正解1つと不正解1つが選択される
    Given Add a question "スクラムに含まれる要素は何ですか？（option1とoption4が正解）" of multiple answers
    And User is on the first question
    When User chooses "option1" and "option2" answers
    And User clicks the answer button
    Then "option1" and "option2" are selected in advice page

  @developing
  Scenario Outline: 3カテゴリから均等に10問問題を表示する
    Given scrumに<scrum_stored>問が登録されている
    And techに<tech_stored>問が登録されている
    And teamに<team_stored>問が登録されている
    When startをクリックしてすべての問題を回答したとき
    Then scrumが<scrum_shown>問が表示されること
    And 合計で<total_shown>問が表示されること

    Examples:
      | scrum_stored | tech_stored | team_stored | total_shown | scrum_shown |
      | 5            | 5           | 5           | 10          | >=3         |
      | 5            | 5           | 0           | 10          | 5           |
      | 5            | 0           | 0           | 5           | 5           |
      | 0            | 5           | 5           | 10          | 0           |
      | 0            | 0           | 5           | 5           | 0           |
      | 1            | 5           | 5           | 10          | 1           |
      | 5            | 1           | 5           | 10          | >=4         |
