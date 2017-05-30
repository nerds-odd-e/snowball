Feature: send preview mail
  As a user I want to send preview mail to admin

  Scenario Outline: send precourse email
    Given there is a course starting from "17.05.2017"
    And there are students with email <student emails> loaded for this course
    When i send <action> email for this course
    Then <who should receive the email> should receive the preview email

    Examples:
    |student emails       |action    |who should receive the email  |content             |
    |                     |preview   |admin@odd-e.com               |17.05.2017          |
    |student@odd-e.com    |preview   |admin@odd-e.com               |17.05.2017          |