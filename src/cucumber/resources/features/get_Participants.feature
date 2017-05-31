Feature: Get Participant
  As the admin I want to get the participant list,
  so that I can send email to all the participants using the template

  @participant @developing
  Scenario: Get the list of participants
    When the course "CSD_NEW" is selected
    Then course participant should contain "prakash.kaul.16@gmail.com"