Feature: Send Email
  User should be able to send email
  if email addresses are valid

  Background:
    Given Visit Send Mail Page

  @email
  Scenario Outline: Verify sending email to people
    Given Default Email with Recipients "<email>"
    When I Click Send Email
    Then I should get an element with message "<success>"

   Examples:
      | email                                          | success                           |
      | gadget@mailinator.com                          | success : Email successfully sent |
      | inspector@mailinator.com;gadget@mailinator.com | success : Email successfully sent |

  @developing # TODO: should implement "Send to the same contact again when there is a new course" tasks
  Scenario: Send to the same contact again when there is a new course
    When We have below number of contacts at each location:
      |location | number of contacts|
      |Singapore|                  1|
    And We have below number of events at each location:
      |location | number of events  |
      |Singapore|                  1|
    When I click send button
    And We have below number of events at each location:
      |location | number of events  |
      |Singapore|                  1|
    When I click send button
    Then It should send out emails:
      | location | number of emails | number of events in the email |
      | Combined |                1 |                             2 |
    