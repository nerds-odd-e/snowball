Feature: Get Participant
  As the admin I want to get the participant list,
  so that I can send email to all the participants using the template

  Scenario: Get the list of participants
    When the course "CSD_PRE" is selected
    And Page Should Contain "terry@odd-e.com"