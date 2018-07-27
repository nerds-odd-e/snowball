Feature: Contacts
  As the admin I want to maintain contacts,
  so that I can sent newsletters to them later.

  Scenario: Add consentid of exists Contact
    Given "terry@odd-e.com" which with no consent id contact already
    When I add consent id "43"
    Then contact "terry@odd-e.com"'s consent id should be "43"
    When I open edit contact page for contact "terry@odd-e.com"
    Then I should see "43" in consent id textbox

  Scenario: Contacts with duplicate email is not allowed
    Given "terry@odd-e.com" which in "China" and "Chengdu" is a contact already
    When Add A Contact "terry@odd-e.com" at "China" and "Chengdu"
    Then Page Should Contain "terry@odd-e.com"
    And Page Should Fail

  Scenario Outline: Verify Add New Contact To Contact List with Country And City and consent id
    When Add A Contact "<email>" at "<country>" and "<city>" for "<name>", "<lastName>" from "<company>" with "<consentId>"
    Then Page Should Contain "<email>"
    And  Page Should Contain "<country>"
    And  Page Should Contain "<city>"
    And  Page Should Contain "<name>"
    And  Page Should Contain "<lastName>"
    And  Page Should Contain "<company>"
    And  Page Should Contain "<consentId>"
    And Page Should Success

    Examples:
      | email           | city    | country     | name  | lastName | company | consentId |
      | user1@odd-e.com | Chengdu | China       | jaohn | smith    | odd-e   | 123       |
      | user2@odd-e.com | Aigle   | Switzerland | jane  | doe      | odd-e   | abc       |
      | user4@odd-e.com | Dubna   | Russia      | mark  | smith    | odd-e   | def       |

  @developing
  Scenario Outline: Verify Add New Contact To Contact List with ConsentId
    When Add A Contact "<email>" at "<country>" and "<city>" with "<consentId>"
    Then I should get an element with message email sent: "<email sent?>"
    Examples:
      | email           | city  | country | consentId | email sent? |
      | user5@odd-e.com | Dubna | Russia  | 1234      | yes         |
      | user6@odd-e.com | Dubna | Russia  |           | no          |


  Scenario:
    Given Add A Contact "user5@odd-e.com" at "Russia" and "Dubna" with "1234"
    When Add A Contact "ANOTHER@EMAil.com" at "Singapore" and "Singapore" with "1234"
    Then Page Should Fail

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

  @developing
  Scenario Outline: Update contact information if already exists in the system
    Given Contact for "<email>" exists in the system
    When I upload a valid CSV file with "<email>"
    Then the contact should be updated with "<name>"
    And the contact should be updated with "<lastname>"
    And the contact should be updated with "<company>"
    And the contact should be updated with "<location>"
    And the contact should be updated with "<consentId>"

    Examples:
      | email           | name | lastname | company | location          | consentId                        |
      | user1@odd-e.com | john | smith    | odd-e   | Chengdu/China     | ef98e3b803ab2326dbadf8fa8ed1d1ca |
      | user2@odd-e.com | jane | doe      | odd-e   | Aigle/Switzerland | 19a70418bdb440c2c0f97ddab8fa486d |
      | user3@odd-e.com | mark | smith    | odd-e   | Dubna/Russia      | 7b2ea5378b29a1f607198980f55b0615 |


  @developing
  Scenario Outline: Verify Edit Existing Contact
    Given Contact for "<email>" exists in the system
    When Edit A Contact "<country>" and "<city>" for "<name>", "<lastName>" from "<company>" and "<consentId>"
    Then Page Should Contain "<consentId>"
    And  Page Should Contain "<country>"
    And  Page Should Contain "<city>"
    And  Page Should Contain "<name>"
    And  Page Should Contain "<lastName>"
    And  Page Should Contain "<company>"
    And Page Should Success

    Examples:
      | email           | city    | country     | name  | lastName | company | consentId |
      | user1@odd-e.com | Chengdu | China       | jaohn | smith    | odd-e   | 1234      |
      | user2@odd-e.com | Aigle   | Switzerland | jane  | doe      | odd-e   | 1235      |
      | user4@odd-e.com | Dubna   | Russia      | mark  | smith    | odd-e   |           |

  @developing
  Scenario Outline: Add new contact without consentId
    Given Contact for "<email>" which is not existing in the system
    When I add the contact whose name is "<firstname>" and lastname is "<lastname>" and company is "<company>" and email is "<email>" and location is "<country>" and "<city>"
    Then Contact "<email>" record is created
    And consent email is sent with a automatically generated unique consent ID in the mail text

  Examples:
  | email                   | city    | country     | firstname   | lastname | company |
  | terry20180724@odd-e.com | Chengdu | China       | jaaahn      | smith    | odd-e   |