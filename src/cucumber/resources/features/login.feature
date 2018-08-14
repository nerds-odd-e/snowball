Feature: Login
  login.

  Background:
    Given Visit Login Page

  @developing
  Scenario: Login success
    Given Fill form with "mary@example.com" and "abcd1234"
    When I click login button
    Then Show course list of current user

  @developing
  Scenario Outline: Login fail
    Given Fill form with "<email>" and "<password>"
    When I click login button
    Then I should move to page with url "<url>" and message "<message>"

    Examples:
      | email               | password       | url       | message      |
      | mary@example.com    | hogehoge       | login.jsp | login failed |
      | unknown@example.com | hogehoge       | login.jsp | login failed |
      | uninit@example.com  | uninitpassword | login.jsp | login failed |
