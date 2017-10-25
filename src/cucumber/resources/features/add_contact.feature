Feature: Add Contact
  As the admin I want to add contacts,
  so that I can sent newsletters to them later.

  @contact
  Scenario: Verify Add New Contact To Contact List
    When Add A Contact "terry@odd-e.com" at "Singapore"
    Then Page Should Contain "terry@odd-e.com"
    And Page Should Success

  @contact
  Scenario: Verify Add Existing Contact To Contact List
    Given "terry@odd-e.com" which in "Singapore" is a contact already
    When Add A Contact "terry@odd-e.com" at "Singapore"
    Then Page Should Contain "terry@odd-e.com"
    And Page Should Fail

  @contact @developing
  Scenario: Verify Add New Contact To Contact List with Country And City
    When Add A Contact "terry@odd-e.com" at "China" and "Chengdu"
    Then Page Should Contain "terry@odd-e.com"
    And  Page Should Contain "China"
    And  Page Should Contain "Chengdu"
    And Page Should Success
