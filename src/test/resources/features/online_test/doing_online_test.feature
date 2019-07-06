Feature:
  User can take an online test :)

  Scenario: Displaying the question
    Given There are questions with dummy options:
      | description    | correctOption     | category |
      | What is scrum? | None of the above | Scrum    |
    And User is on the first question
    And User should see a question and options
      | description | What is scrum?    |
      | option1     | Food              |
      | option2     | Drink             |
      | option3     | Country           |
      | option4     | Animal            |
      | option5     | None of the above |

  Scenario Outline: User navigates to advice or next question page
    Given User is taking a onlineTest with 2 questions and there are enough questions
    And User is on the first question
    When User chooses the "<selected_option>" answer
    And User clicks the answer button
    Then It should move to "<page_content>" page
    Examples:
      | selected_option | page_content |
      | wrongOption     | Advice       |
      | correctOption   | Question     |

  Scenario Outline: advice page should include clear feedback
    Given There are questions with dummy options:
      | description | advice                             | category |
      | スクラムとは何ですか？ | Read the Scrum Guide again, please | Scrum    |
    And User is on the first question
    When User chooses the "<incorrect option>" answer
    And User clicks the answer button
    Then User should see "unselected_correct" option "#0000FF" and text "None of the above"
    And User should see "selected_incorrect" option "#dc3545" and text "<incorrect option>"
    And User should see "Read the Scrum Guide again, please"

    Examples:
      | incorrect option |
      | Food             |
      | Drink            |
      | Country          |
      | Animal           |

  Scenario Outline: 何も選択しないでanswerをクリックした場合、次の出題に移動しない
    Given User is taking a onlineTest with 2 <回答type> questions and there are enough questions
    When User clicks the answer button
    Then 質問1の画面に遷移する

    Examples:
      | 回答type          |
      | single choice   |
      | multiple choice |

  Scenario Outline: User goes to end of test if he has answered all questions
    Given User is taking a onlineTest with 3 questions and there are enough questions
    And User answered <number_of_questions> questions correctly
    Then "<page_content>" should be shown

    Examples:
      | number_of_questions | page_content |
      | 2                   | Question     |
      | 3                   | End Of Test  |

  Scenario Outline: 問題ページで正解2つを選択して、次の問題に移動
    Given User is taking a onlineTest with <number_of_questions> multiple choice questions and there are enough questions
    When User chooses "correctOption1" and "correctOption2" answers
    And User clicks the answer button
    Then It should move to "<page_content>" page

    Examples:
      | number_of_questions | page_content |
      | 2                   | Question     |
      | 1                   | End Of Test  |

  Scenario: User goes to end of test after seeing the last advice
    Given User is taking a onlineTest with 3 questions and there are enough questions
    And User answered 2 questions correctly
    When User chooses the "wrongOption" answer
    And User clicks the answer button
    And User clicks the next button
    Then "End Of Test" should be shown

  Scenario: 一度一つ前のアドバイスページに戻ってもう一度Nextを押した時に正しい質問ページに遷移する
    Given User is taking a onlineTest with 2 questions and there are enough questions
    And User is on the first question
    When User chooses the "wrongOption" answer
    And User clicks the answer button
    Then アドバイスページにいる
    When User clicks the next button
    Then 質問2の画面に遷移する
    When ユーザーがブラウザの戻るを実行する
    And User clicks the next button
    Then 質問2の画面に遷移する

  Scenario: 一度アドバイスページから質問ページに戻ってもう一度Answerを押した時に "You answered previous question twice" と表示する
    Given User is taking a onlineTest with 2 questions and there are enough questions
    And User is on the first question
    When User chooses the "wrongOption" answer
    And User clicks the answer button
    And ユーザーがブラウザの戻るを実行する
    Then 質問1の画面に遷移する
    When User chooses the "wrongOption" answer
    And User clicks the answer button
    Then 質問2の画面に遷移する
    And "You answered previous question twice" should be shown
    And text "You answered previous question twice" is color red

  Scenario: 一度アドバイスページから質問ページに戻ってもう一度Answerを押した時にEnd of Testページで "You answered previous question twice" と表示する
    Given User is taking a onlineTest with 1 questions and there are enough questions
    And User is on the first question
    When User chooses the "wrongOption" answer
    And User clicks the answer button
    Then アドバイスページにいる
    When ユーザーがブラウザの戻るを実行する
    Then 質問1の画面に遷移する
    When User chooses the "wrongOption" answer
    And User clicks the answer button
    Then "End Of Test" should be shown
    And "You answered previous question twice" should be shown
    And text "You answered previous question twice" is color red

  Scenario Outline: 質問ページに移動した時に、回答選択肢が何も選択されていない
    When User is taking a onlineTest with 1 <回答type> questions and there are enough questions
    Then 0つ"<ボタンtype>"の回答が選択されている事

    Examples:
      | 回答type          | ボタンtype  |
      | single choice   | radio    |
      | multiple choice | checkbox |

  Scenario: アドバイスページで正解2つと誤った回答2つがわかる
    Given there is a question "スクラムに含まれる要素は何ですか？（option1とoption4が正解）" of multiple answers
    And User is on the first question
    When User chooses "correctOption1" and "wrongOption1" answers
    And User clicks the answer button
    Then User should see "selected_correct" option "#28a745" and text "correctOption1"
    And User should see "selected_incorrect" option "#dc3545" and text "wrongOption1"
    And User should see "unselected_incorrect" option "#333" and text "wrongOption2"
    And User should see "unselected_correct" option "#0000FF" and text "correctOption2"

