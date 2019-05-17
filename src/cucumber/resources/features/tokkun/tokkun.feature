Feature:
  特訓を開始すると、回答して５日経過した問題もしくは未回答の問題のみ表示される

  Background:
    Given ユーザが登録されている
    Given 問題Aが追加された
    And 佐藤が6日前に問題Aを回答した
    And 問題Bが追加された
    And 佐藤が1日前に問題Bを回答した
    And 問題Cが追加された
    And 佐藤は問題Cを回答していない
    And 特訓画面が表示されている

  @developing
  Scenario Outline: 特訓を開始して５日経過した問題と未回答の問題が表示される
    Given 佐藤がログインしている
    When 特訓ボタンを押す
    Then 特訓回答画面に遷移する
    And 佐藤が"<last answered days before>"に回答した質問が佐藤に"<is display>"

    Examples:
      | last answered days before | is display |
      | 6                         | 表示         |
      | 1                         | 非表示        |
      | none                      | 表示         |

  @developing
  Scenario Outline: 初めてログインする山田はすべての問題が表示される
    Given 山田が初めてログインしている
    When 特訓ボタンを押す
    Then 特訓回答画面に遷移する
    And 佐藤が"<last answered days before>"に回答した質問が山田に"<is display>"

    Examples:
      | last answered days before | is display |
      | 6                         | 表示         |
      | 1                         | 表示         |
      | none                      | 表示         |

  @developing
  Scenario Outline: 山田は自身の回答履歴にしたがって問題が表示される
    Given 山田でログインしている
    And 山田が3日前に問題Aを回答した
    And 山田が5日前に問題Bを回答した
    And 山田は問題Cを回答していない
    When 特訓ボタンを押す
    Then 特訓回答画面に遷移する
    And 山田は問題"<question>"が"<is display>"

    Examples:
      | question | is display |
      | A        | 非表示        |
      | B        | 表示         |
      | C        | 表示         |


