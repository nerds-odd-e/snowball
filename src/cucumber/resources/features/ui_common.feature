Feature:
  サイドメニューの各種リンクから画面遷移ができる

  Scenario: Update Adviceがサイドメニューに表示される
    Given topページが表示されている
    When Update Adviceのリンクをクリックする
    Then Update Adviceが表示される
