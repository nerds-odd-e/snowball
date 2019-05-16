Feature: User Confirmation
  After admin add a new Contact, a new user confirmation is created.
  The new user could set initial password for login,
  to access the course information.

  Scenario: Initial password follow confirmation mail
    When Admin add a new contact "john" with email: "user1@odd-e.com"
    Then An confirmation email is sent to "user1@odd-e.com" from: "myodde@gmail.com"
    When "john" click the link in the email
    And set password to "1234abcd", password confirmation to "1234abcd"
    Then Show valid information
    And I login with "user1@odd-e.com" and "1234abcd"
    And Show tokkun top

  Scenario: Invalid token
    Given Admin add a new contact "Yang" with email: "yang@odd-e.com"
    Then An confirmation email is sent to "yang@odd-e.com" from: "myodde@gmail.com"
    When "Yang" change the token in the url to "I_made_it_up" and access the new url
    Then "Invalid token" message is shown

  Scenario Outline: Check password and password_confirm
    Given Admin add a new contact "john" with email: "john@odd-e.com"
    Then An confirmation email is sent to "john@odd-e.com" from: "myodde@gmail.com"
    When "john" click the link in the email
    And set password to "<password>", password confirmation to "<password_confirm>"
    Then Show <result> information

    Examples:
    | password    | password_confirm | result  |
    | 123123ab    | 123123ab         | valid   |
    | 1asaasdf123 | aljsdflkjasf     | invalid |

  Scenario Outline: If initial password setting failed then retry
    Given Admin add a new contact "john" with email: "john@odd-e.com"
    Then An confirmation email is sent to "john@odd-e.com" from: "myodde@gmail.com"
    When "john" click the link in the email
    And set password to "<password>", password confirmation to "<fail_password>"
    Then Show invalid information
    And set password to "<success_password>", password confirmation to "<success_password>"
    Then Show valid information

    Examples:
    | password    | fail_password  | success_password |
    | 123123ab    | 1234dflkjasf   | 1234qwer         |
    |             |                | 1234qwer         |