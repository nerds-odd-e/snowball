Feature: Initial Password
  After admin add a new Contact,
  the new user could set initial password for login,
  to access the course information.

  @developing
  Scenario Outline: Initial password follow confirmation mail
    When Admin add a new contact "<name>" with email: "<email>"
    Then An confirmation email is sent to "<email>" from: "myodde@gmail.com"
    When "<name>" click the link in the email
    And "<name>" set password to "<password>"
    Then Show success page

    Examples:
      | email           | name | password |
      | user1@odd-e.com | john | 1234abcd |

  @developing
  Scenario Outline: Invalid email address
    When Admin add a new contact "<name>" with invalid email: "<email>"
    Then Contact page show "error message"
    And  Contact was not created
    And  Mail was not sent

    Examples:
      | email           | name |
      | invalid@####    | John |
      | test@           | Mary |
      | test            | Test |

   @developing
   Scenario: Invalid token
     Given Admin add a new contact Yang with email: "yang@odd-e.com"
     Then An confirmation email is sent to "yang@odd-e.com" from: "admin@odd-e.com"
     When Yang change the token in the url to "I_made_it_up" and access the new url
     Then "Invlid token" message is shown
     And Yang cannot set password
