Feature:
  問題表示の前にカテゴリー選択が表示される

  @developing
  Scenario: カテゴリーのチェックボックスが全て外れる
    Given カテゴリー選択画面が表示される
    And クリアボタンが表示される
    And カテゴリーのチェックボックスにチェックが入っている
    When クリアボタンをクリック
    Then カテゴリーのチェックボックスのチェックが全て外れる

  @developing
  Scenario: カテゴリーのチェックボックスを全部外してスタートボタンが押せない
    Given カテゴリー選択画面が表示される
    And カテゴリーのチェックボックスにチェックが入っていない
    When スタートボタンをクリック
    Then 質問画面に遷移しない

  @developing
  Scenario: Questionがないカテゴリーはチェックボックスに表示されない
    Given カテゴリー選択画面が表示される
    And "scrum"カテゴリーにQuestionが存在しない
    Then チェックボックスに"scrum"カテゴリーが表示されない

  Scenario: スタートボタンを押したら、問題画面に遷移する
    Given カテゴリー選択画面が表示される
    And 問題が存在している
    And カテゴリーのチェックボックスにチェックが入っている
    When スタートボタンをクリック
    Then 問題画面へ遷移する

  Scenario Outline: 3カテゴリから均等に最大10問問題を表示する
    Given "Scrum"に<scrum_stored>問が登録されている
    And "Tech"に<tech_stored>問が登録されている
    And "Team"に<team_stored>問が登録されている
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
