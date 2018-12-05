# new feature
# Tags: optional

Feature: A description

  @developing
  Scenario Outline: markdownが表示される
    Given markdownの文字列 <markdown>
    When 質問を追加した
    And 質問リストのページをクリックする
    Then 質問リストに追加した質問が表示されている
    And  Adviceに <html> が表示される
    Examples:
      | markdown                          | html                                            |
      | # abc                             | <h1>abc</h1>                                    |
      | [abc](https://www.yahoo.co.jp)    | <a herf="https://www.yahoo.co.jp">abc</a>       |

