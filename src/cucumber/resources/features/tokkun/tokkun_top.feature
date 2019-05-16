Feature:
  特訓トップにアクセスする

  @now
  Scenario: 未ログインユーザーはログインさせる
    Given ユーザーは未ログイン
    When 特訓トップにアクセスする
    Then ログインページに遷移する


