Feature:　Simple Review
  Scenario: ユーザがテストを開始できること
    Given ユーザがログインした状態である
    And 問題が15個存在する
    When テストを開始
    Then 10個の問題が表示される

#  Scenario: ソーと
##    Given ユーザがログインした状態である
##    And 問題が2個存在する
##    And 全2問の問題を解いたことがある
##    When テストを開始
##    Then 個の問題が表示される


