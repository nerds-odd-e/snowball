Feature: Add Participant
  As the admin I want to add participants,
  so that I can enroll existing contacts for the upcoming courses

  Scenario: Add an existing contact as participant
    When Add A Contact "terry@odd-e.com" at "Singapore"
    And Page Should Contain "terry@odd-e.com"

  Scenario: Add Existing Contact To Contact List
    Given "terry@odd-e.com" which in "Singapore" is a contact already
    When Add A Contact "terry@odd-e.com" at "Singapore"
    And Page Should Contain "terry@odd-e.com"
