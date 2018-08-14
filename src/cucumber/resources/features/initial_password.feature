Feature: Initial Password

  @developing
  Scenario Outline: Send mail for initial password
    When Admin add a new contact <name> with email: "<email>"
    Then An confirmation email is sent to "<email>"
    When "<name>" click the link in the email
    And "<name>" set password to <password>
    Then "<name>" can login with "<email>" and "<password>"

    Examples:
      | email           | name | password |
      | user1@odd-e.com | john | 1234abcd |

  @developing
  Scenario Outline: invalid email address
    When Admin add a new contact "<name>" with invalid email: "<email>"
    Then Contact page show "error message"
    And  Contact was not created
    And  Mail was not sent

    Examples:
      | email           | name |
      | invalid@####    | John |
      | test@           | Mary |

   @developing
   Scenario: invalid token
     Given Admin add a new contact Yang with email: yang@odd-e.com
     When Yang access invalid url
     Then show not found page
     And Yang cannot set password
