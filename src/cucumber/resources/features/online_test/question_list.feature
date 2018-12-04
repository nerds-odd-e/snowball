# new feature
# Tags: optional

Feature: A description
  @developing
  Scenario: 質問がある場合に質問リストを開くと質問が表示される
    Given 質問がある
    When 質問リストのページをクリックする
    Then 質問リストに質問がある
  @developing
  Scenario: 質問を追加すると質問リストに表示される
    Given 質問を追加した
    When 質問リストのページをクリックする
    Then 質問リストに追加した質問が表示されている
