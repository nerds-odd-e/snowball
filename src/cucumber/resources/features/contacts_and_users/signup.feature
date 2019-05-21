Feature: Signup

  Background:
    Given user is on the sign up page

  Scenario: user sign up a new account
    When user sign up with:
      | userName         | yamada               |
      | email            | tanaka@tokoroten.com |
      | password         | hogepassword         |
      | password_confirm | hogepassword         |
    And ユーザーはSubmitボタンをクリックする
    Then 特訓のトップページに遷移する
    And 自分のユーザー名"yamada"が表示されている

  Scenario: user uses different password & confirmation
    When user sign up with:
      | userName         | ktanaka              |
      | email            | tanaka@tokoroten.com |
      | password         | hogepassword         |
      | password_confirm | hogehoge             |
    And ユーザーはSubmitボタンをクリックする
    Then サインアップページにエラーメッセージが表示される

  Scenario: User name is unique
    Given ユーザー"ktanaka"がユーザー登録済みである
    When ユーザーはUserNameに"ktanaka"を入力する
    And ユーザーはEmailに"tanaka@tokoroten.com"を入力する
    And ユーザーはPasswordに"hogepassword"を入力する
    And ユーザーはPassword_confirmに"hogepassword"を入力する
    And ユーザーはSubmitボタンをクリックする
    Then サインアップページにエラーメッセージが表示される
