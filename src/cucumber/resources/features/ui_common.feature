Feature:
  サイドメニューの各種リンクから画面遷移ができる

  Scenario: Update Adviceがサイドメニューに表示される
    Given topページが表示されている
    When Update Adviceのリンクをクリックする
    Then Update Adviceが表示される

  Scenario: Homeの左のサイドバーのStartTestを押すと、カテゴリ選択に遷移する
    Given topページが表示されている
    And 問題が存在している
    When StartTestのリンクをクリックする
    Then カテゴリ選択画面が表示される
    And カテゴリのチェックボックスが表示される
    And カテゴリのチェックボックスは全て選択されている
