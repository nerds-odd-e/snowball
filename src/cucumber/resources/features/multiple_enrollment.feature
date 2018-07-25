Feature: batch enroll students
  As a user I want to enroll multiple students to course

  Background:
    Given There is a course "CSD Tokyo" already registered

  @developing
  Scenario: Enroll multiple participants to course
    When I enroll participants to "CSD Tokyo"
      | participants information                                 |
      | tom@example.com\tTom\tSmith\tCS\tSingapore\tSingapore    |
      | john@example.com\tJohn\tFisher\tCS\tSingapore\tSingapore |
      | carry@\tCarry\tFisher\tCS\tSingapore\tSingapore          |
    Then participant with correct information is enrolled to "CSD Tokyo"
      | participant email | enrollment result |
      | tom@example.com   | enrolled          |
      | john@example.com  | enrolled          |
      | carry@            | not enrolled      |

  @developing
  Scenario Outline: Enroll a participant to course
    When I enroll "<participant information>" to "CSD Tokyo"
    Then "<participant email>" is <enrollment result> to "CSD Tokyo"

    Examples:
      | participant email | participant information                               | enrollment result |
      | tom@example.com   | tom@example.com\tTom\tSmith\tCS\tSingapore\tSingapore | enrolled          |
      | carry@            | carry@\tCarry\tFisher\tCS\tSingapore\tSingapore       | not enrolled      |
