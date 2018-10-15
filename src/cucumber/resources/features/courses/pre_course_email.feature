Feature: send pre-course mail
  As a user I want to send preview mail to admin

  Background:
    Given "student@odd-e.com" which in "China" and "Chengdu" is a contact already

  Scenario Outline: previewing and sending precourse email
    Given there is a course starting from "2017-05-17"
    And there is a student with information "<student information>" loaded for this course
    When i send <action> email for this course
    Then "<who should receive the email>" should receive the pre-course email
    Then "<who should not receive any email>" shouldn't receive any email

    Examples:
      | student information                                   | action    | who should receive the email | who should not receive any email |
      |                                                       | preview   | admin@odd-e.com              | tom@example.com                  |
      | tom@example.com\tTom\tSmith\tCS\tSingapore\tSingapore | preview   | admin@odd-e.com              | tom@example.com                  |
      | tom@example.com\tTom\tSmith\tCS\tSingapore\tSingapore | precourse | tom@example.com              | admin@odd-e.com                  |
