Feature: Game login
  As the player I want to be able to input my email address
  so that I can play the game.

  // when invalid email, redirect back to same page with error
  // when valid new email, contact should exist on contacts page, then direct to game page
  // when valid old email, contact should not be duplicated on contacts page, then direct to game page

  Scenario: Ensure invalid email input is redirected to login page with error
    When Login with email "@@"
    Then Page Should Contain "Invalid email provided!"

  Scenario: Ensure valid new email is saved before redirecting to game page
    When Login with email "new_terry@odd-e.com"
    Then Page should be redirected to "game_page"
    Then Contacts page should contain "new_terry@odd-e.com"

  Scenario: Ensure valid existing email redirects to game page
    Given "terry@odd-e.com" is a contact already
    When Login with email "terry@odd-e.com"
    Then Page should be redirected to "game_page"
    Then Contacts page should contain "terry@odd-e.com"
