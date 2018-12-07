Feature:
  User can take an online test :)

  Scenario: Displaying the question
    Given Add a question "What is scrum?"
    And User is on the first question
    And User should see a question and options
      | description | What is scrum?    |
      | option1     | Scrum is Rugby    |
      | option2     | Scrum is Baseball |
      | option3     | Scrum is Soccer   |
      | option4     | Scrum is Sumo     |
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
    Given Add a question "スクラムとは何ですか？"
    And User is on the first question
    When User chooses the "<incorrect option>" answer
    And User clicks the answer button
    Then User should see "correct" option "#28a745" and text "以上の何でもない"
    And User should see "selected incorrect" option "#dc3545" and text "<incorrect option>"
    And User should see "スクラムガイドを読みましょう。"

    Examples:
      | incorrect option |
      | 食べ物              |
      | 飲み物              |
      | 国                |
      | 動物               |

  @developing
  Scenario Outline: Adviceのmarkdownが表示される
    Given markdownの文字列 <markdown>
    When User chooses the "<incorrect option>" answer
    Then Adviceに <html> が表示される
    Examples:
      | incorrect option | markdown                       | html                                      |
      | 食べ物              | # abc                          | <h1>abc</h1>                              |
      | 食べ物              | [abc](https://www.yahoo.co.jp) | <a herf="https://www.yahoo.co.jp">abc</a> |


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

  @developing
  Scenario Outline: カテゴリ別の出題データをそれぞれ準備した際に、同じ割合で出題されていること
    Given カテゴリ別の出題データを準備する
    And カテゴリー"scrum"を<opt1>個準備する
    And カテゴリー"team"を<opt2>個準備する
    And カテゴリー"technical"を<opt3>個準備する
    When <opt7>回回答する
    Then カテゴリー"scrum"の母数は<opt4>以上が表示される
    And カテゴリー"team"の母数は<opt5>以上が表示される
    And カテゴリー"technical"の母数は<opt6>以上が表示される


    Examples:
      | opt1 | opt2 | opt3 | opt4 | opt5 | opt6 | opt7 |
      | 10   | 5    | 0    | 5    | 5    | 0    | 10   |
      | 10   | 10   | 10   | 3    | 3    | 3    | 10   |
      | 10   | 1    | 1    | 8    | 1    | 1    | 10   |
      | 3    | 3    | 3    | 3    | 3    | 3    | 9    |
      | 3    | 1    | 3    | 3    | 1    | 3    | 7    |


  @developing
  Scenario Outline: 出題数の上限が固定されている
    Given 問題開始画面に移動する
    And カテゴリー"scrum"を3個準備する
    And カテゴリー"team"を3個準備する
    And カテゴリー"technical"を4個準備する
    When 10回回答する
    Then 結果画面に移動する
    Examples:
      |  |

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

  Scenario Outline: 複数回答の問題に回答できる
    Given <correct_answer>個の正解がある問題が<question_count>個登録されている
    And 問題が出題される
    And ユーザの<n>個の選択のうち、<m>個が正しい
    When "answer"をクリックする
    Then <page> ページに遷移する
    Examples:
      | correct_answer | question_count | n | m | page        |
      | 1              | 1              | 1 | 1 | End Of Test |
      | 1              | 1              | 1 | 0 | Advice      |
      | 1              | 2              | 1 | 1 | Question    |
      | 2              | 1              | 2 | 2 | End Of Test |
      | 2              | 2              | 2 | 2 | Question    |

  @developing
  Scenario: アドバイスページで正解2つと誤った回答1つがわかる
    Given Add a question "スクラムに含まれる要素は何ですか？（option1とoption4が正解）" of multiple answers
    And User is on the first question
    When User chooses "option1" and "option2" answers
    And User clicks the answer button
    Then User should see "correct" options "#28a745" and text "option1" "option4"
    And User should see "selected incorrect" option "#dc3545" and text "option2"

  @developing
  Scenario: アドバイスページで正解2つと誤った回答2つがわかる
    Given Add a question "スクラムに含まれる要素は何ですか？（option1とoption4が正解）" of multiple answers
    And User is on the first question
    When User chooses "option2" and "option3" answers
    And User clicks the answer button
    Then User should see "correct" options "#28a745" and text "option1" "option4"
    And User should see "selected incorrect" options "#dc3545" and text "option2" "option3"

