Feature:
  問題表示の前にカテゴリー選択が表示される

  Background:
    Given there is a question category "Scrum"
    And there is a question category "Tech"
    And there is a question category "Team"


  Scenario: Start test with all the categories
    Given there is one question exist in the system
    And I'm on the category selection page
    And all the categoriesu should have been selected
    When I click the start test button
    Then I should see the test starts

  Scenario Outline: Questions should be selected from categories evenly
    Given In "Scrum" there are <scrum_stored> questions
    And In "Tech" there are <tech_stored> questions
    And In "Team" there are <team_stored> questions
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
