Feature: Add Contact
  As the admin I want to add contacts,
  so that I can sent newsletters to them later.


  Scenario: Verify Add New Contact To Contact List
    When Add A Contact "terry@odd-e.com"
    Then I should get an alert dialog with message "Add contact successfully"
    And Page Should Contain "terry@odd-e.com"

  Scenario: Verify Add Existing Contact To Contact List
    Given "terry@odd-e.com" is a contact already
    When Add A Contact "terry@odd-e.com"
    Then I should get an alert dialog with message "Email terry@odd-e.com is already exist"
    And Page Should Contain "terry222@odd-e.com"

