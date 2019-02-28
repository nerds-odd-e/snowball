Feature:
  User can take an online tokkun test :)

  @developing
  Scenario: Displaying the question for tokkun
    Given 5日前にOnlineTestを受講した
    When ユーザがtokkunページへのリンクをクリックする
    Then 特訓ページに1問目が表示されている
    And 以下の内容が表示されている
      | description | What is scrum?    |
      | option1     | Food              |
      | option2     | Drink             |
      | option3     | Country           |
      | option4     | Animal            |
      | option5     | None of the above |
