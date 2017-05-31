Feature: Create Course
  As a admin I want to create courses

  @course
  Scenario: Verify create new course
    Given I am on create new course page
    When Add a course with below details
      |coursename|duration|location |startdate  |address|coursedetails|instructor|
      |    CSD-1 | 30     |Singapore|16-JUL-2017|odd-e  |CSD Training | Terry    |
    And I click the Create button
    Then Course should save and successfully saved message should appear



