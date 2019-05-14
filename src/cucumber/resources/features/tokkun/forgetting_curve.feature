Feature:

  @developing
  Scenario: 未回答の質問が特訓の質問に出てくる
    Given 2問の質問がある
    And 初回の特訓で1問正解して、1問不正解する
    When 1日後に特訓開始をする
    Then 初回特訓ときに不正解した質問のみが表示されること

  @developing
  Scenario Outline: n連続で正解している質問が、最終回答日からx日後に表示される
    Given "<correct_answer_num>"連続で正解している1つの質問がある
    When 前回の質問回答日から"<past_days>"日後に特訓を実施する
    Then 質問が表示される
    Examples:
      | correct_answer_num | past_days |
      | 1                  | 2         |
      | 2                  | 7         |
      | 3                  | 20        |
      | 4                  | 60        |

  @developing
  Scenario Outline: n連続で正解している質問が、最終回答日からx日以内に表示されない
    Given "<correct_answer_num>"連続で正解している1つの質問がある
    When 前回の質問回答日から"<past_days>"日以内に特訓を実施する
    Then 質問が表示されない
    Examples:
      | correct_answer_num | past_days |
      | 1                  | 2         |
      | 2                  | 7         |
      | 3                  | 20        |
      | 4                  | 60        |
      | 5                  | 100       |