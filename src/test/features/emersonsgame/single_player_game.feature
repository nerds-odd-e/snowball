
Feature: Single Player Game
  As an admin I want to able to control and see the game's progress on the spectator UI,
  and as a player, I want to be able to play the game through a player UI

  Background:
    Given a game of distance 20 is created
    And a player joins the game on a separate window

  @wip
  Scenario: Player makes a move
    Given a player joins the game on a separate window
    When the player makes a normal move
    Then the player UI should display the roll value and an updated player progress

  @wip
  Scenario: Admin advances the game round and views the game progress
    When
    And the admin clicks on "next" in the spectator view
    Then the position of the player should move from the last round

