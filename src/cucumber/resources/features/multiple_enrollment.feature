Feature: batch enroll students
  As a user I want to enroll multiple students to course

  Background:
    Given I am on create new course page
    And Add a course with below details
      | coursename    | CSD Tokyo    |
      | duration      | 30           |
      | country       | Japan        |
      | city          | Tokyo        |
      | startdate     | 2017-10-23   |
      | address       | odd-e        |
      | coursedetails | CSD training |
      | instructor    | Terry        |
    And I click the Create button

  @developing
  Scenario: Enroll multiple participants to course
    When I enroll participants to "CSD Tokyo"
      | tom@example.com\tTom\tSmith\tCS\tSingapore\tSingapore    |
      | john@example.com\tJohn\tFisher\tCS\tSingapore\tSingapore |
      | carry@\tCarry\tFisher\tCS\tSingapore\tSingapore          |
    Then participant with correct information is enrolled to "CSD Tokyo"
      | tom@example.com   | enrolled          |
      | john@example.com  | enrolled          |
      | carry@            | not enrolled      |
    Then all participants below should receive the pre-course email
      | tom@example.com   |
      | john@example.com  |
    Then "carry@" shouldn't receive any email

  @developing
  Scenario Outline: Enroll a participant to course
    When I enroll "<participant information>" to "CSD Tokyo"
    Then "<participant email>" is <enrollment result> to "CSD Tokyo"

    Examples:
      | participant email | participant information                               | enrollment result |
      | tom@example.com   | tom@example.com\tTom\tSmith\tCS\tSingapore\tSingapore | enrolled          |
      | carry@            | carry@\tCarry\tFisher\tCS\tSingapore\tSingapore       | not enrolled      |
