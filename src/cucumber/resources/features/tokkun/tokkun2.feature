Feature:
  特訓を開始すると、回答して５日経過した問題もしくは未回答の問題のみ表示される

  Background:
    Given There are users as bellow
      | sato@example.com   | abcd1002 |
      | mariko@example.com | abcd1002 |
    And 問題Aが追加された
    And 問題Bが追加された

    @developing
    Scenario Outline: 特訓を開始して５日経過した問題と未回答の問題が表示される
      Given I login with "sato@example.com" and "abcd1002"
      And <問題Aを回答した日>に問題Aを回答した
      And <問題Bを回答した日>に問題Bを回答した
      And 特訓を受ける
      Then 特訓に<今の問題数>問がみえる
      Examples:
      |問題Aを回答した日  | 問題Bを回答した日   | 今の問題数　|
      |       　　　　　 | 　　　　　　　　    | 2 　　　　  |
      | 1日前      　　　|     　　　　　　　　| 1　　　　   |
      | 5日前      　　　|     　　　　　　　　| 1　　　　   |
      | 6日前      　　　|          　　　　　 | 2　　　　   |
      | 1日前      　　　| 1日前    　　　　　 | 0　　　　   |

  @developing
  Scenario Outline: 各ユーザが別々の特訓を受ける
    Given "sato@example.com" answered question A 1 day before
    When I login as "ログインしたユーザ"
    And I start to do tokun
    Then I should see <今の問題数> in the tokkun
    Examples:
      |　ログインしたユーザ     | 今の問題数　|
      | sato@example.com     |  1        |
      | mariko@example.com   |  2        |