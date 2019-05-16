Feature: Signup
  ユーザーがサインアップできる

  Scenario: ユーザーがアカウントを新規登録できる
    Given ユーザーがサインアップページを開いている
    When ユーザーはUserNameに"ktanaka"を入力する
    And ユーザーはEmailに"tanaka@tokoroten.com"を入力する
    And ユーザーはPasswordに"hogepassword"を入力する
    And ユーザーはPassword_confirmに"hogepassword"を入力する
    And ユーザーはSubmitボタンをクリックする
    Then 特訓のトップページに遷移する
    And 自分のユーザー名"ktanaka"が表示されている


