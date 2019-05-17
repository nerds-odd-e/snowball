Feature:
  特訓トップにアクセスする

  @now
  Scenario: 未ログインユーザーはログインさせる
    Given ユーザーは未ログイン
    When 特訓トップにアクセスする
    Then ログインページに遷移する

  @now
  Scenario: ログインしたユーザーは特訓トップににアクセスできる
    Given There are users as bellow
      | mary@example.com | abcd1234 |
    When I login with "mary@example.com" and "abcd1234"
    And 特訓トップにアクセスする
    Then 特訓トップページを表示する



