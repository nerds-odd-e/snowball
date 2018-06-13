Feature: send preview mail
  As a user I want to send preview mail to admin

  @now
  Scenario Outline: send precourse email
    Given there is a course starting from "2017-05-17"
    And there are students with email "<student emails>" loaded for this course
    When i send <action> email for this course
    Then "<who should receive the email>" should receive the pre-course email

    Examples:
    |student emails       |action    |who should receive the email  |
    |                     |preview   |admin@odd-e.com               |
    |student@odd-e.com    |preview   |admin@odd-e.com               |
    |student@odd-e.com    |precourse |student@odd-e.com             |
