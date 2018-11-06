Feature:
  Admin can add questions

  @developing
  Scenario Outline: 質問の追加が成功する
    Given 作成画面を開いている
    And Description の文字数が "<descriptionTextCount>"
    And Option1 の文字数が "<option1TextCount>"
    And Option2 の文字数が  "<option2TextCount>"
    And Option3 の文字数が  "<option3TextCount>"
    And Option4 の文字数が  "<option4TextCount>"
    And Option5 の文字数が  "<option5TextCount>"
    And Option6 の文字数が  "<option6TextCount>"
    And "<answer>"を回答として選択済み
    And adviceが の文字数が "<adviceTextCount>"
    When Addボタンを押す
    Then 質問の一覧に遷移する
    Examples:
      | descriptionTextCount | option1TextCount | option2TextCount | option3TextCount | option4TextCount | option5TextCount | option6TextCount | answer  | adviceTextCount |
      | 1                    | 5                | 5                | 5                | 0                | 0                | 0                | option1 | 0               |
      | 1                    | 5                | 5                | 5                | 0                | 0                | 0                | option1 | 10              |
      | 1                    | 5                | 5                | 5                | 0                | 0                | 0                | option1 | 10              |
      | 200                  | 5                | 5                | 5                | 0                | 0                | 0                | option1 | 10              |
      | 1                    | 100              | 5                | 5                | 0                | 0                | 0                | option1 | 10              |
      | 1                    | 5                | 5                | 5                | 0                | 0                | 0                | option1 | 500             |