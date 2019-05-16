Feature:
  公開問題をAdminが承認すると承認済みになる

  Scenario: 公開問題をAdminが承認すると承認済みになる
    Given 未承認の問題がある
      | description | What is scrum?  |
      | option1     | Rugby           |
      | option2     | Football        |
    When Adminが承認する
    Then 問題が承認済みになる
