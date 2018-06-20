@trigger
Feature: GDPR
  User should send email to every user who have not received consent mail ever

  Scenario: Verify GDPR menu exist
    When I open the main page
    Then System shows GDPR menu on left panel

  Scenario: Verify showing GDPR page with trigger button
    Given Visit Front page
    When I click on GDPR in menu
    Then GDPR page shows in right side of page
    And Trigger button should exist in page
