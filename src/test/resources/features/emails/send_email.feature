Feature: Send Email
  User should be able to send email
  if email addresses are valid

  Background:
    Given Visit Send Mail Page

  Scenario Outline: Verify sending email to people
    Given Default Email with Recipients "<email>"
    When I Click Send Email
    Then I should get an element with message "<success>"

   Examples:
      | email                                          | success                           |
      | gadget@mailinator.com                          | success : Email successfully sent |
      | inspector@mailinator.com;gadget@mailinator.com | success : Email successfully sent |

