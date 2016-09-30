
Feature: Create Game
  As the admin I want create a game

  Scenario: Create New Game
    Given I am at Emerson's landing page
    When I submit a distance of 20
    Then Page should be redirected to "gameSpectator"
    And Distance should be 20

  Scenario: Player joins a created game
    Given a game of distance 20 is created
    When  a player joins the game on a separate window
    Then the player UI should display "currentDistance" of 0
    And the player UI should display "scars" of 0

  @wip
  Scenario: Admin sees that a player has joined the game
    Given a game of distance 20 is created
    And a player joins the game on a separate window
    Then the spectator UI "player-count" should display that 1 player joined within 2 seconds
