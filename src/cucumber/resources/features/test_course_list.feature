Feature: Get list of avalibale course.
  I want to see all list of course

  @AllCourseListTest
  Scenario: User requests courseList should return all courses
    When user requests courseList
    Then list all courses
