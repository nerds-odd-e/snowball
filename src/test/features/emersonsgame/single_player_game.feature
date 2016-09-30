
Feature: View Game
  As an admin I want to see the player's progress when he plays the game

  @wip
  Scenario: View Game
    Given a game is started
    And a player joins the game on a separate window
    When the player roll the die
    Then the player's move should be displayed on the spectator view

