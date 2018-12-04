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
      | incorrect option  |
      | 食べ物 |
      | 飲み物 |
      | 国 |
      | 動物 |

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

 @now
  Scenario Outline: カテゴリ別の出題データをそれぞれ準備した際に、同じ割合で出題されていること
    Given カテゴリ別の出題データを準備する
    And カテゴリーscrum"<opt1>"を何個準備する
    And カテゴリーteam"<opt2>"を何個準備する
    And カテゴリーtechnical"<opt3>"を何個準備する
    When 回答を終えた場合、
    Then カテゴリーscrumの母数は<opt4>が表示される
    And カテゴリーteamの母数は"<opt5>"が表示される
    And カテゴリーtechnicalの母数は"<opt6>"が表示される


    Examples:
       | opt1 | opt2 | opt3 | opt4 | opt5 | opt6 |
       | 10   | 5    | 0    |  5   | 5    | 0    |
       | 10   | 10   | 10   |  >=3 | >=3  | >=3  |
       | 10   | 1    | 1    |  8   | 1    | 1    |
       | 3    | 3     | 3   |  3   | 3    | 3    |
       | 3    | 1     | 3   |  3   | 1    | 3    |


  Scenario Outline: 出題数の上限が固定されている
    Given 問題開始画面に移動する
    And カテゴリーscrum"<opt1>"を何個準備する
    And カテゴリーteam"<opt2>"を何個準備する
    And カテゴリーtechnical"<opt3>"を何個準備する
    When 10回回答する
    Then 結果画面に移動する
