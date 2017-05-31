Feature: Test Participants


@TestCourseContacts
Scenario Outline: When user select the course it should return participants
When  user select course name "<courseName>"
Then Course should return contact with "<emailID>"
  Examples:
    | courseName | emailID |
  |CSD|greg@gmail.com|
