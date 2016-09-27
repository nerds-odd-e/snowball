Feature: Game login
  As the player I want to be able to input my email address
  so that I can play the game.

  Scenario Outline: Checking invalid email
    When Login with email "<email>"
    Then Page Should Contain "Invalid email provided!"

    Examples:
    | abc@        |
    | "@@"        |
    | "abc@abc"   |

  Scenario: Ensure valid new email is saved before redirecting to game page
    When Login with email "new_terry@odd-e.com"
    Then Page should be redirected to "game_player"
    Then Contacts page should contain "new_terry@odd-e.com"


  Scenario: Ensure valid existing email redirects to game page
    Given "terry@odd-e.com" is a contact already
    When Login with email "terry@odd-e.com"
    Then Page should be redirected to "game_player"
    And Contacts page should contain "terry@odd-e.com"
