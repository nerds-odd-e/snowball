Feature:
  問題表示の前にカテゴリー選択が表示される

  Scenario Outline: Questions should be selected from categories evenly
    Given there are <scrum_stored> questions of category "Scrum"
    Given there are <tech_stored> questions of category "Tech"
    Given there are <team_stored> questions of category "Team"
    When do a test with 10 questions
    Then there should be <scrum_shown> Scrum questions
    And in total <total_shown> questions
    Examples:
      | scrum_stored | tech_stored | team_stored | total_shown | scrum_shown |
      | 5            | 5           | 5           | 10          | >=3         |
      | 5            | 5           | 0           | 10          | 5           |
      | 5            | 0           | 0           | 5           | 5           |
      | 0            | 5           | 5           | 10          | 0           |
      | 0            | 0           | 5           | 5           | 0           |
      | 1            | 5           | 5           | 10          | 1           |
      | 5            | 1           | 5           | 10          | >=4         |
