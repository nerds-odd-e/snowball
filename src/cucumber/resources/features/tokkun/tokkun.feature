Feature:
  特訓を開始すると、回答して５日経過した問題が表示される

  @developing
  Scenario: 特訓を開始する
    Given 特訓画面が表示されている
    And 質問が1つある
    When 特訓ボタンを押す
    Then 特訓回答画面に遷移する

  @developing
  Scenario Outline: 特訓を開始する
    Given "<user_name>"でログインしている
    And 特訓画面が表示されている
    And "<user_name>"が5日以上前に回答した質問が1つある
    When 特訓ボタンを押す
    Then 特訓回答画面に遷移する
    And "<user_name>"が5日以上前に回答した質問が表示されている

    Examples:
      | user_name |
      | tanaka |

  @developing
  Scenario Outline: 特訓を開始する
    Given "<user_name>"でログインしている
    And 特訓画面が表示されている
    And "<user_name>"が5日以上前に回答した質問が0
    When 特訓ボタンを押す
    Then 特訓回答画面に遷移する
    And 質問が表示されていない

    Examples:
      | user_name |
      |　satou |

  @developing
  Scenario Outline: Displaying the question
    Given Add a question "What is scrum?" with dummy options
    And User is on the first question
    And "<current_date>" is newer than "<last_answered_date>" + 5 days
    Then User should see a question and options
      | description | What is scrum?    |
      | option1     | Food              |
      | option2     | Drink             |
      | option3     | Country           |
      | option4     | Animal            |
      | option5     | None of the above |

    Examples:
      | current_date | last_answered_date |
      | 201905191200 | 201905141000       |
