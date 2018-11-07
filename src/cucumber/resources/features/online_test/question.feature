Feature:
  User can take an online test :)

  Scenario: Displaying the question
    Given There is a question "What is scrum?"
    And User is on the first question
    And User should see a question and options
      | description | What is scrum?    |
      | option1     | Scrum is Rugby    |
      | option2     | Scrum is Baseball |
      | option3     | Scrum is Soccer   |
      | option4     | Scrum is Sumo     |
      | option5     | None of the above |

  Scenario: Displaying the question progress ( 1 of 3 )
    Given There is a question "What is scrum?"
    And There is a question "What is Scrum Master?"
    And There is a question "What is Product Owner?"
    And User is on the first question
    Then User sees the question progress as "1/3"

  Scenario: Displaying the question progress ( 2 of 3 )
    Given There is a question "What is scrum?"
    And There is a question "What is Scrum Master?"
    And There is a question "What is Product Owner?"
    And User is on the second question
    Then User sees the question progress as "2/3"

  Scenario Outline: User navigates to advice or next question page
    Given User is taking a quiz with 2 questions
    And User is on the first question
    When User chooses the "<selected_option>" answer
    And User clicks the answer button
    Then It should move to "<page_content>" page
    Examples:
      | selected_option | page_content |
      | wrongOption     | Advice       |
      | correctOption   | Question     |

  Scenario Outline: advice page should include clear feedback
    Given There is a question "What is scrum?"
    And User is on the first question
    When User chooses the "<incorrect option>" answer
    And User clicks the answer button
    Then User should see "correct" option "#28a745" and text "None of the above"
    And User should see "selected incorrect" option "#dc3545" and text "<incorrect option>"
    And User should see "Scrum is a framework for agile development."

    Examples:
      | incorrect option  |
      | Scrum is Rugby    |
      | Scrum is Baseball |
      | Scrum is Soccer   |
      | Scrum is Sumo     |

  Scenario Outline: User goes to end of test if he has answered all questions
    Given User is taking a quiz with 3 questions
    And User answered correctly the <number_of_questions> th question page
    Then "<page_content>" should be shown

    Examples:
      | number_of_questions | page_content |
      | 2                   | Question     |
      | 3                   | End Of Test  |

  Scenario: User goes to end of test if he has answered last questions wrong
    Given User is taking a quiz with 3 questions
    And User answered correctly the 2 th question page
    When User chooses the "wrongOption" answer
    And User clicks the answer button
    And User clicks the next button
    Then "End Of Test" should be shown

  @developing
  Scenario Outline: 最後の問題が終わったらFinal Scoreを表示する
    Given 問題が<number_of_questions>問ある時
    And ユーザーが<number_of_correct>問正解したら
    Then "<message>" が表示される

    Examples:
      | number_of_questions | number_of_correct | message                             |
      | 20                  | 20                | 20/20問 あなたの正解率は100%です。あなたはスクラムマスター！ |
      | 20                  | 17                | 17/20問 あなたの正解率は85%です。あともう少し！        |
      | 20                  | 16                | 16/20問 あなたの正解率は80%です。基本を学び直しましょう   |

  Scenario: テストが全1問のとき最終ページの分母に1が表示される
    Given User is taking a quiz with 1 questions
    And User answered correctly the 1 th question page
    Then 分母に1が表示される

  @now
  Scenario Outline:
    Given User is taking a quiz with 2 questions
    And User is on the first question
    When User chooses the "<incorrect option>" answer
    And User clicks the answer button
    Then アドバイスページにいる
    When ユーザーがブラウザの戻るを実行する
    Then 質問2の画面に遷移する

    Examples:
      | incorrect option |
      | wrongOption      |
