Feature: Multiplayer game
  As the game host I want to be able to host multiple players
  and uniquely identify each player

  Background:
    Given a game of distance 20 is created

  @wip
  Scenario: More than one player join the game with different emails
    Given a player joins the game
    When another player joins the game on a separate window
    Then The spectator should see two cars on the screen

  @wip
  Scenario: More than one player join the game with the same email
    Given a player joins the game
    When another player with the same email joins the game
    Then The spectator should see two cars on the screen

  @wip
  Scenario: Two players play the game in the same time
    Given two players are in the game
    When the host starts the next round
    And player2 chooses normal round
    Then The spectator should see that the 2nd car have moved
    And The spectator should see that the 1st car is still stationary

  @wip
  Scenario: The host decide to move to the next round without waiting for one of the players
    Given two players are in the game
    And the host starts the next round
    And player2 chooses normal round
    But player1 did not choose a move
    When the host starts the next round
    Then player1 can move
    And player2 can move

  @wip
  Scenario: When there are too many cars that cannot fit in one screen
    Given the screen is very small
    And twenty players are in the game
    When five players moved
    Then The spectator should see only top five cars in distance travelled in order