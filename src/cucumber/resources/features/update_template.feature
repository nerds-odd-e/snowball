Feature: Update Template
  User should be able to update the email template
  with contents entered by user

  @updateTemplate
  Scenario: Verify updating the template
    Given Visit Edit Template Page
    When I update the contents of template
      |templateList      |content  |
      |Default Template 1|Test Template|
    And I click on update button
    Then Template contents should be update and I should get an element with message "<success>"
