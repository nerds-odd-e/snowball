Feature:
  User can take random question test

#  @developing
#  @now
# (０〜８回回答済み)正解を選択して回答ボタンを押下すると次のランダムQuestionに遷移すること
#  Scenario Outline: Student can take a random question test
#    Given Student as user
#    And Student want to take random test
#    And System displayed random test question page.
#    When Student choose a correct answer.
#    And Student pressed "answer" button.
#    Then System displayed next question which choose Randomly
#
#  Examples:
#      | count | correct |
#      | 0     | true    |
#      | 1     | true    |
#      | 2     | true    |
#      | 3     | true    |
#      | 4     | true    |
#      | 5     | true    |
#      | 6     | true    |
#      | 7     | true    |
#      | 8     | true    |


# ９回回答済みで、正解を選んで、回答ボタンを押下するとEndOfTestに遷移すること
# ９回回答済みで、不正解を選んで、回答ボタンを押下するとAdviceに遷移すること
# ９回回答済みで、Advice画面からNextを押下するとEndOfTestに遷移すること



  #  @developing
# (０〜８回回答済み)正解を選択して回答ボタンを押下すると次のランダムQuestionに遷移すること
  @now
  Scenario Outline: Student can take a random question 10 times
    Given Student as user
    And Student want to take random test
    And System displayed random test question page.
    When Student choose a correct answer for 10 times "<count>" "<correct>"
    And Student pressed "answer" button.
    Then System displayed EndOfTest page

  Examples:
    | count | correct |
    | 0     | true    |
    | 1     | true    |
    | 2     | true    |
    | 3     | true    |
    | 4     | true    |
    | 5     | true    |
    | 6     | true    |
    | 7     | true    |
    | 8     | true    |



