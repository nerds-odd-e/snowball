Feature: send pre-course mail
  As a user I want to send preview mail to admin

  @now
  Scenario Outline: previewing and sending precourse email
    Given there is a course starting from "2017-05-17"
    And there is a student with email "<student emails>" loaded for this course
    When i send <action> email for this course
    Then "<who should receive the email>" should receive the pre-course email
    Then "<who should not receive any email>" shouldn't receive any email

    Examples:
    |student emails       |action    |who should receive the email  | who should not receive any email |
    |                     |preview   |admin@odd-e.com               | student@odd-e.com                |
    |student@odd-e.com    |preview   |admin@odd-e.com               | student@odd-e.com                |
    #|student@odd-e.com    |precourse |student@odd-e.com             | admin@odd-e.com                  |
