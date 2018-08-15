Feature: Contacts
  As the admin I want to maintain contacts,
  so that I can sent newsletters to them later.

  Scenario: Edit Location Information of Contact
    Given "terry@odd-e.com" which in "China" and "Chengdu" is a contact already
    When I change the location information of contact to be "China" and "Chengdu"
    Then contact "terry@odd-e.com"'s locations should be "China/Chengdu"

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
