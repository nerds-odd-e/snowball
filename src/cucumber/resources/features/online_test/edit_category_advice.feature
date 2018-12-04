Feature:
  Admin can add category advice

  Background: Display Add Question page
    Given Add Questionを開いている
    Then "Invalid inputs found!" というメッセージが表示されていない
    And "Right answer is not selected!" というメッセージが表示されていない

  @developing
  Scenario Outline: Validation
    Given Update Adviceを開いている
    And Categoryで"<category>"を選択している
    And Adviceに"<advice>" を入力する
    When Updateボタンを押す
    Then "<messages>"というメッセージが表示される
    Examples:
      | category | advice | messages                        |
      |          | foo    | カテゴリーを選んでください          |
      | scrum    |        | Scrumのアドバイスを入力してください |
      |          |        | カテゴリーを選んでください          |
      | scrum    | foo    |                                 |

  @developing
  Scenario Outline: Adviceの呼び出し
    Given Update Adviceを開いている
    When Categoryで"<category>"を選択する
    Then "<advice>"がAdviceのフォームに表示される
    Examples:
      | category | advice       |
      |          |              |
      | scrum    | scrum_advice |
      | tech     | tech_advice  |
      | team     | team_advice  |

  @developing
  Scenario: Adviceの更新が成功する
    Given Update Adviceを開いている
    And adviceに"updated advice"を入力する
    And Categoryを"<category>"として選択済み
    When Updateボタンを押す
    Then "updated advice"というadviceが表示される
    And Categoryで"<category>"が選択済み
    Examples:
      | category |
      | scrum    |
      | tech     |
      | team     |
