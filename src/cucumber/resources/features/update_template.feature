Feature: Update Template
  User should be update the email template
  with contents entered by user

  @updateTemplate
  Scenario: Verify updating the template
    Given Visit Send Mail Page
    When I update the contents of template
      |templateName     |content  |
      |Pre-CSD Template |Test Template|
    And I click on update button
    Then Template contents should be update and I should get an element with message "<success>"
