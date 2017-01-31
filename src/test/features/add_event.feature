Feature: Add Event
  As the admin I want to add events
  so that I can send newsletters of events to contacts later

  Scenario: Verify Add New Event To Event List
    When Add an event "CSD training"
    Then I should get an alert dialog with message "Add event successfully"
    And Event list page should contain "CSD training"

  Scenario: Verify Add Existing Event To Event List
    Given "CSD training"is an event already
    When Add an event "CSD training"
    Then I should get an alert dialog with message "Event 'CSD training' is already exist"
    And Event list page should contain one event with title "CSD training"

  Scenario: Verify Event Title Is Empty
    When Add an event without Title
    Then I should get an alert dialog with message "Event title is mandatory"
    And Event list page should not show an event with empty title