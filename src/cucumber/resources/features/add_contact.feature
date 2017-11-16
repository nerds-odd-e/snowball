Feature: Add Contact
  As the admin I want to add contacts,
  so that I can sent newsletters to them later.

  Scenario: Verify Add Existing Contact To Contact List
    Given "terry@odd-e.com" which in "China" and "Chengdu" is a contact already
    When Add A Contact "terry@odd-e.com" at "China" and "Chengdu"
    Then Page Should Contain "terry@odd-e.com"
    And Page Should Fail

    @sudip
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
      | user3@odd-e.com | Bern    | Switzerland |
      | user4@odd-e.com | Dubna   | Russia      |