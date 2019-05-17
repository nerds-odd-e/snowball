Feature: Signup
  ユーザーがサインアップできる

  Background:
    Given ユーザーがサインアップページを開いている

  Scenario: ユーザーがアカウントを新規登録できる
    When ユーザーはUserNameに"yamada"を入力する
    And ユーザーはEmailに"tanaka@tokoroten.com"を入力する
    And ユーザーはPasswordに"hogepassword"を入力する
    And ユーザーはPassword_confirmに"hogepassword"を入力する
    And ユーザーはSubmitボタンをクリックする
    Then 特訓のトップページに遷移する
    And 自分のユーザー名"yamada"が表示されている

  Scenario: ユーザーがアカウント登録でパスワードの入力に誤りがある
    When ユーザーはUserNameに"ktanaka"を入力する
    And ユーザーはEmailに"tanaka@tokoroten.com"を入力する
    And ユーザーはPasswordに"hogepassword"を入力する
    And ユーザーはPassword_confirmに"hogehoge"を入力する
    And ユーザーはSubmitボタンをクリックする
    Then サインアップページにエラーメッセージが表示される

  Scenario: 登録済みのユーザーを登録しようとして失敗する
    Given ユーザー"ktanaka"がユーザー登録済みである
    When ユーザーはUserNameに"ktanaka"を入力する
    And ユーザーはEmailに"tanaka@tokoroten.com"を入力する
    And ユーザーはPasswordに"hogepassword"を入力する
    And ユーザーはPassword_confirmに"hogepassword"を入力する
    And ユーザーはSubmitボタンをクリックする
    Then サインアップページにエラーメッセージが表示される
