Feature: Create Game
  As the admin I want create a game

  Scenario: Create New Game
    Given I am at Emerson's landing page
    When I submit a distance of 20
    Then Page should be redirected to "gameSpectator"
    And Distance should be 20

Feature: View Game
  As an admin I want to see the player's progress when he plays the game

  Scenario: View Game
    Given A game is started
    And a player joins the game
    When the player roll the die
    Then the player's move should be displayed on the spectator view

