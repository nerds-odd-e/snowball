Feature: Contacts
  As the admin I want to maintain contacts,
  so that I can sent newsletters to them later.

  Scenario Outline: Adding contact with invalid email address
    When Admin add a new contact "<name>" with invalid email: "<email>"
    Then Contact page show "email is invalid"
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

  Scenario: Contacts with duplicate email is not allowed
    Given "terry@odd-e.com" which in "China" and "Chengdu" is a contact already
    When Add A Contact "terry@odd-e.com" at "China" and "Chengdu"
    Then Page Should Contain "terry@odd-e.com"
    And Page Should Fail

  Scenario: Edit Location Information of Contact
    Given "terry@odd-e.com" which in "China" and "Chengdu" is a contact already
    When I change the location information of contact to be "China" and "Chengdu"
    Then contact "terry@odd-e.com"'s locations should be "China/Chengdu"

  Scenario: Upload CSV with Multiple Contacts
    Given There are the following contacts in the CSV file that do not exist in the system
      | email,firstname,lastname,company,country,city                |
      | balakg@gmail.com,Bala,GovindRaj,CS,Singapore,Singapore       |
      | forshailesh@gmail.com,Shailesh,Thakur,CS,Singapore,Singapore |
    When I upload the CSV file
    Then There must be two more contacts added
      | balakg@gmail.com      |
      | forshailesh@gmail.com |
