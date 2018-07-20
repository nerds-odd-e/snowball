Feature: Contacts
  As the admin I want to maintain contacts,
  so that I can sent newsletters to them later.

  Scenario: Contacts with duplicate email is not allowed
    Given "terry@odd-e.com" which in "China" and "Chengdu" is a contact already
    When Add A Contact "terry@odd-e.com" at "China" and "Chengdu"
    Then Page Should Contain "terry@odd-e.com"
    And Page Should Fail

  Scenario Outline: Verify Add New Contact To Contact List with Country And City
    When Add A Contact "<email>" at "<country>" and "<city>" for "<name>", "<lastName>" from "<company>"
    Then Page Should Contain "<email>"
    And  Page Should Contain "<country>"
    And  Page Should Contain "<city>"
    And  Page Should Contain "<name>"
    And  Page Should Contain "<lastName>"
    And  Page Should Contain "<company>"
    And Page Should Success

    Examples:
      | email           | city    | country     | name  | lastName | company |
      | user1@odd-e.com | Chengdu | China       | jaohn | smith    | odd-e   |
      | user2@odd-e.com | Aigle   | Switzerland | jane  | doe      | odd-e   |
      | user4@odd-e.com | Dubna   | Russia      | mark  | smith    | odd-e   |

  Scenario: Edit Location Information of Contact
    Given "terry@odd-e.com" which in "China" and "Chengdu" is a contact already
    When I change the location information of contact to be "China" and "Chengdu"
    Then contact "terry@odd-e.com"'s locations should be "China/Chengdu"

  @now
  Scenario: Upload CSV with Multiple Contacts
    Given There are the following contacts in the CSV file that do not exist in the system
      | email,firstname,lastname,company,country,city                |
      | balakg@gmail.com,Bala,GovindRaj,CS,Singapore,Singapore       |
      | forshailesh@gmail.com,Shailesh,Thakur,CS,Singapore,Singapore |
    When I upload the CSV file
    Then There must be two more contacts added
      | balakg@gmail.com      |
      | forshailesh@gmail.com |

  Scenario Outline: Update contact information if already exists in the system
    Given Contact for "<email>" exists in the system
    When I upload a valid CSV file with "<email>"
    Then the contact should be updated with "<name>"
    And the contact should be updated with "<lastname>"
    And the contact should be updated with "<company>"
    And the contact should be updated with "<location>"

    Examples:
      | email           | name | lastname | company | location          |
      | user1@odd-e.com | john | smith    | odd-e   | Chengdu/China     |
      | user2@odd-e.com | jane | doe      | odd-e   | Aigle/Switzerland |
      | user3@odd-e.com | mark | smith    | odd-e   | Dubna/Russia      |