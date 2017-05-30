Feature: Create Course
  As a admin I want to create courses

  @course @developing
  Scenario: Verify create new course
    Given I am on create new course page
    When Add a course with below details
      |coursename|duration|location | startdate |address|coursedetails|instructor|
      |CSD       |30      |Singapore|16-JUL-2017|odd-e  |CSD Training | Terry    |
    And I click the Create button
    Then Course should save and successfully saved message should appear


  @course @developing
  Scenario: Verify create new course
    Given I am on create new course page
    When Add a course with below details
      |coursename|duration|location | startdate |address|coursedetails|instructor|
      |          | 30     |Singapore|16-JUL-2017|odd-e  |CSD Training | Terry    |
      |          | 30     |Singapore|16-JUL-2017|       |CSD Training | Terry    |
      |CSD       | 30     |         |16-JUL-2017|odd-e  |CSD Training | Terry    |
      |CSD       | 30     |Singapore|           |odd-e  |CSD Training | Terry    |
      |CSD       | 30     |Singapore|16-JUL-2017|odd-e  |CSD Training | Terry    |
      |CSD       | 30     |Singapore|16-JUL-2017|       |CSD Training | Terry    |
      |CSD       | 30     |Singapore|16-JUL-2017|odd-e  |             | Terry    |
    And I click the Create button
    Then Course should not save and failure message should appears

  @course @developing
  Scenario: Verify create new course
    Given I am on create new course page
    When Add a course with below details
     |coursename|duration|location | startdate |address|coursedetails|instructor|
     |CSD       |10      |Singapore|16-JUN-2017|odd-e  |CSD Training |          |
    And I click the Create button
    Then Course should save and successfully saved message should appear



