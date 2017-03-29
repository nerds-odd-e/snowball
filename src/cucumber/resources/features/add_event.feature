Feature: Add Event
  As the admin I want to add events
  so that I can send notifications of events to contacts later

  Scenario: Verify Add New Event To Event List
    Given I am on Add Event page
    When Add an event "CSD training" at "Singapore"
    Then Event list page should contain "CSD training" and "Singapore"
