Feature: Course Detail
  Display enrolled participants

  Background:
    Given There is a course with below details
      | courseName    | CSD Tokyo    |
      | duration      | 30           |
      | country       | Japan        |
      | city          | Tokyo        |
      | startDate     | 2017-10-23   |
      | address       | odd-e        |
      | courseDetails | CSD training |
      | instructor    | Terry        |

  @now
  Scenario: Display course that has no participant
    When I visit "CSD Tokyo" detail page from course list page
    Then "CSD Tokyo" course detail page is shown

  Scenario: Enroll multiple participants to course from course detail page
    When I enroll participants to "CSD Tokyo" from course detail page
      | tom@example.com	Tom	Smith	CS	Singapore	Singapore	CNT-0001         |
      | john@example.com	John	Fisher	CS	Singapore	Singapore	CNT-0002 |
      | carry@	Carry	Fisher	CS	Singapore	Singapore	CNT-0003             |
    Then participant with correct information appears on "CSD Tokyo" course detail page
      | tom@example.com  | Tom  |
      | john@example.com | John |
    And participant with invalid information appears in the enroll form
      | carry@  Carry  Fisher  CS  Singapore  Singapore  CNT-0003 |
