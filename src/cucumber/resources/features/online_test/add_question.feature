Feature:
  Admin can add questions

  @developing
  Scenario: 質問のValidation
    Given Add Questionを開いている
    And Descriptionが未入力
    When Addボタンを押す
    Then 入力が不適切ですというメッセージが表示される

  @developing
  Scenario: 質問の追加
    Given 作成画面を開いている
    And Description「お元気ですか？」が入力済み
    And Options「1: 元気です」が入力済み
    And Options「2: 風邪です」が入力済み
    And 「1: 元気です」を回答として選択済み
    And adviceが未入力
    When Addボタンを押す
    Then TODO: 質問の一覧に遷移し保存した一覧が表示される