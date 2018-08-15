Feature: User Register
  After admin add a new Contact,
  the new user could set initial password for login,
  to access the course information.

  Scenario: Contacts with duplicate email is not allowed
    Given "terry@odd-e.com" which in "China" and "Chengdu" is a contact already
    When Add A Contact "terry@odd-e.com" at "China" and "Chengdu"
    Then Page Should Contain "terry@odd-e.com"
    And Page Should Fail

  @developing
  Scenario: Initial password follow confirmation mail
    When Admin add a new contact "john" with email: "user1@odd-e.com"
    Then An confirmation email is sent to "user1@odd-e.com" from: "myodde@gmail.com"
    When "john" click the link in the email
    And "john" set password to "1234abcd"
    Then Show success page

  Scenario Outline: Invalid email address
    When Admin add a new contact "<name>" with invalid email: "<email>"
    Then Contact page show "error message"
    And  "<email>" was not contained at Contact List Page
    And  Mail was not sent

    Examples:
      | email           | name |
      | invalid@####    | John |
      | test@           | Mary |
      | test            | Test |

  Scenario Outline: Valid email address
    When Admin add a new contact "<name>" with valid email: "<email>"
    Then Contact list page show "<email>"

    Examples:
    | email              | name |
    | user1@odd-e.com    | John |
    | test@odd-e.com     | Mary |

  @developing
  Scenario: Invalid token
    Given Admin add a new contact Yang with email: "yang@odd-e.com"
    Then An confirmation email is sent to "yang@odd-e.com" from: "admin@odd-e.com"
    When Yang change the token in the url to "I_made_it_up" and access the new url
    Then "Invalid token" message is shown
    And Yang cannot set password

  Scenario: Upload CSV with Multiple Contacts
    Given There are the following contacts in the CSV file that do not exist in the system
      | email,firstname,lastname,company,country,city                |
      | balakg@gmail.com,Bala,GovindRaj,CS,Singapore,Singapore       |
      | forshailesh@gmail.com,Shailesh,Thakur,CS,Singapore,Singapore |
    When I upload the CSV file
    Then There must be two more contacts added
      | balakg@gmail.com      |
      | forshailesh@gmail.com |
