Feature: Edit Contact
  As the admin I want to edit contacts,
  so that I can sent newsletters to them later.


  @contact @wip
  Scenario: Edit Location Information of Contact
    Given "terry@odd-e.com" which in "China" and "Chengdu" is a contact already
    When I change the location information of contact to be "China" and "Chengdu"
    Then contact "terry@odd-e.com"'s locations should be "China/Chengdu"


