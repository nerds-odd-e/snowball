Feature: Add Contact
  As the admin I want to add contacts,
  so that I can sent newsletters to them later.


  Scenario: Verify Add New Contact To Contact List
    When Add A Contact "terry@odd-e.com"
    And Page Should Contain "terry@odd-e.com"

  Scenario: Verify Add Existing Contact To Contact List
    Given "terry@odd-e.com" is a contact already
    When Add A Contact "terry@odd-e.com"
    And Page Should Contain "terry@odd-e.com"

