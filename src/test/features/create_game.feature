Feature: Create Game
  As the admin I want create a game

  Scenario: Create New Game
    Given I am at Emerson's landing page
    When I submit a distance of 20
    Then Page should be redirected to "GameSpectator"