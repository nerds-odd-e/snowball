Feature: Course Detail
  Display enrolled participants

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

  Scenario: Display course that has no participant
    When I visit "CSD Tokyo" detail page from course list page
    Then "CSD Tokyo" course detail page is shown

  @developing
  Scenario: Enroll multiple participants to course from course detail page
    When I enroll participants to "CSD Tokyo" from course detail page
      | tom@example.com\tTom\tSmith\tCS\tSingapore\tSingapore    |
      | john@example.com\tJohn\tFisher\tCS\tSingapore\tSingapore |
      | carry@\tCarry\tFisher\tCS\tSingapore\tSingapore          |
    Then participant with correct information appears on "CSD Tokyo" course detail page
      | tom@example.com  | Tom  | Smith |
      | john@example.com | john | smith |
    And Carry appears in the enroll form
      | carry@ |

