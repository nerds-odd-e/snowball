Feature: Create Course
  As the admin I want to create course
  so that I can send notifications of course to contacts later

  @event @developing
  Scenario: Verify Create new Course To Course List
    Given I am on Add Event page
    When Create course "CSD training" in "Singapore"
    And I click the save button
    Then Course save page should display "successful message"
