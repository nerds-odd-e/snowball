Feature: Contacts
  As the admin I want to maintain contacts,
  so that I can sent newsletters to them later.

  Scenario: Contacts with duplicate email is not allowed
    Given "terry@odd-e.com" which in "China" and "Chengdu" is a contact already
    When Add A Contact "terry@odd-e.com" at "China" and "Chengdu"
    Then Page Should Contain "terry@odd-e.com"
    And Page Should Fail

  Scenario Outline: Verify Add New Contact To Contact List with Country And City
    When Add A Contact "<email>" at "<country>" and "<city>"
    Then Page Should Contain "<email>"
    And  Page Should Contain "<country>"
    And  Page Should Contain "<city>"
    And Page Should Success

    Examples:
      | email           | city    | country     |
      | user1@odd-e.com | Chengdu | China       |
      | user2@odd-e.com | Aigle   | Switzerland |
      | user4@odd-e.com | Dubna   | Russia      |

  Scenario: Edit Location Information of Contact
    Given "terry@odd-e.com" which in "China" and "Chengdu" is a contact already
    When I change the location information of contact to be "China" and "Chengdu"
    Then contact "terry@odd-e.com"'s locations should be "China/Chengdu"

  @developing
  Scenario: Verify Add Contact Batch menu
    Given I am on the Add a contact page by batch and click on Upload Button
    When I upload a CSV file containing the contacts list
    Then System shows Add Contact Batch option

  @developing
  Scenario Outline: Update contact information if already exists in the system and all columns in DB is empty except email
    Given Contact for "<email>" exists in the system (Email only)
    When I upload a valid CSV file with "<email>"
    Then the contact should be updated with "<name>"
    And the contact should be updated with "<lastname>"
    And the contact should be updated with "<company>"
    And the contact should be updated with "<city>"
    And the contact should be updated with "<country>"

    Examples:
      | email           | name |  lastname | company |   city   | country     |
      | user1@odd-e.com | john |    smith  | odd-e   |  Chengdu | China       |
      | user2@odd-e.com | jane |    doe    | odd-e   |  Aigle   | Switzerland |
      | user3@odd-e.com | mark |    smith  | odd-e   |  Dubna   | Russia      |

  @developing
  Scenario Outline: Update contact information if already exists in the system and all columns in DB is not empty and have different first name
    Given Contact for "<email>" exists in the system (Email only)
    When I upload a valid CSV file with "<email>"
    Then the system should display "<email>" is having conflicts with the first name "<john>"

    Examples:
      | email           | name |
      | user1@odd-e.com | john |
