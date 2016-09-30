Feature: Create Game
  As the admin I want create a game

  Background:
    Given no players has been added

  Scenario: Create New Game
    Given I am at Emerson's landing page
    When I submit a distance of 20
    Then Page should be redirected to "gameSpectator"
    And Distance should be 20

  Scenario: Player joins a created game
    Given a game of distance 20 is created
    When 1 player(s) joins the game on a separate window
    Then the player UI should display "currentDistance" of 0
    And the player UI should display "scars" of 0

  Scenario: Admin sees that a player has joined the game
    Given a game of distance 20 is created
    And 1 player(s) joins the game on a separate window
    Then the spectator UI "player-count" should display that 1 player joined within 2 seconds

@wip
  Scenario: Admin sees that 3 players join the game
    Given a game of distance 50 is created
    And 50 player(s) joins the game on a separate window
    Then the spectator UI "player-count" should display that 50 player joined within 75 seconds
