Feature: Edit Contact
  As the admin I want to edit contacts,
  so that I can sent newsletters to them later.


  @contact
  Scenario: Edit Location Information of Contact
    Given "terry@odd-e.com" which in "Singapore" is a contact already
    When I change the location information of contact to be "Tokyo"
    Then contact "terry@odd-e.com"'s locations should be "Tokyo"


