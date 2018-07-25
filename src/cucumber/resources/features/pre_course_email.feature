Feature: send pre-course mail
  As a user I want to send preview mail to admin

  Background:
    Given "student@odd-e.com" which in "China" and "Chengdu" is a contact already

  @developing
  Scenario Outline: previewing and sending precourse email
    Given there is a course starting from "2017-05-17"
    And there is a student in system "<student information>" loaded for this course
    When i send <action> email for this course
    Then "<who should receive the email>" should receive the pre-course email
    Then "<who should not receive any email>" shouldn't receive any email

    Examples:
      | student information                            | action    | who should receive the email | who should not receive any email |
      |                                                | preview   | admin@odd-e.com              | student@odd-e.com                |
      | Student\tHoge\tstudent@odd-e.com\tJapan\tTokyo | preview   | admin@odd-e.com              | student@odd-e.com                |
      | Student\tHoge\tstudent@odd-e.com\tJapan\tTokyo | precourse | student@odd-e.com            | admin@odd-e.com                  |
