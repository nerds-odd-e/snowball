Feature: Email Tracking
  User should be able to tracking email
  if email address sent are valid

  Background:
    Given Visit Send Mail Page

  @email @wip @now
  Scenario Outline: Verify email tracking to people
    Given I sent an email and store "<subject>"
    When Visit Email Tracking Page
    Then I should get an element "<subject>, <date>"

    Examples:
      | subject                                        | date                              |
      | gadget@mailinator.com                          | success : Email successfully sent |
      | inspector@mailinator.com;gadget@mailinator.com | success : Email successfully sent |