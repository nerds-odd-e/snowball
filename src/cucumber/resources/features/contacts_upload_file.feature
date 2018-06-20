Feature: Add Contact by Batch
  User should be able to see the Add Contact Batch

@developing
  Scenario: Verify Add Contact Batch menu
    Given I am on the Add a contact page by batch and click on Upload Button
    When I upload a CSV file containing the contacts list
    Then System shows Add Contact Batch option

@developing
  Scenario: Update contact information if already exists in the system and all columns in DB is empty except email
    Given Contact for "<email>" exists in the system (Email only)
    When I upload a valid CSV file with "<email>"
    Then the contact should be updated with "<name>"
    And the contact should be updated with "<last name>"
    And the contact should be updated with "<company>"
    And the contact should be updated with "<country>"
    And the contact should be updated with "<city>"

    Examples:
      | email           | name | last name | company |   city   | country     |
      | user1@odd-e.com | john |    smith  | odd-e   |  Chengdu | China       |
      | user2@odd-e.com | jane |    doe    | odd-e   |  Aigle   | Switzerland |
      | user3@odd-e.com | mark |    smith  | odd-e   |  Dubna   | Russia      |

@developing
  Scenario: Update contact information if already exists in the system and all columns in DB is not empty and have different first name
    Given Contact for "<email>" exists in the system (Email only)
    When I upload a valid CSV file with "<email>"
    Then the system should display "<email>" is having conflicts with the first name "<john>"

    Examples:
      | email           | name |
      | user1@odd-e.com | john |
