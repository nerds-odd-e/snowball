Feature: Create Course
  As a admin I want to create courses

  Scenario: Creating course with unknown location
    Given I am on create new course page
    When Add a course with below details
      |coursename|duration|country|city   |startdate  |address|coursedetails|instructor|
      |    CSD-1 | 30     |China  |Foobar |2017-10-23 |odd-e  |CSD Training | Terry    |
    And I click the Create button
    Then Course should not save and show error messagea

  Scenario: Creating course with known location
    Given I am on create new course page
    When Add a course with below details
      |coursename|duration|country|city   |startdate  |address|coursedetails|instructor|
      |    CSD-1 | 30     |China  |Chengdu|2017-10-23 |odd-e  |CSD Training | Terry    |
    And I click the Create button
    Then Course should save and successfully saved message should appear


