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

  Scenario Outline: send email only one event
    Given There is a contact "<email>" at Tokyo
    And there are two courses at Tokyo
    When I trigger the sending twice
    Then "<email>" shouldn't receive email

   Examples:
    | email |
    | ivan@odd-e.com |

  Scenario Outline: add new contact
    Given There is a contact "<email1>" at Tokyo
    And there are two courses at Tokyo
    And I trigger the sending once
    And add contact "<email2>" at Tokyo
    When I trigger the sending once
    Then "<email2>" should receive email
    But "<email1>" shouldn't receive email

   Examples:
    | email1               | email2                |
    | ivan@odd-e.com       | terry@odd-e.com       |

  Scenario Outline: Even though there are multiple courses, one user receive only 1 email
    Given there is a contact "<email>" at Tokyo, Seoul
    And there are two courses at Tokyo, Souel
    When I trigger the sending twice
    Then contact "<email>" should receive only 1 email

   Examples:
    | email                |
    | ivan@odd-e.com       |

  Scenario: Report Page Includes
    Given There is a contact "abc@odd-e.com" at Tokyo
    And there is a course at Tokyo
    And I trigger the sending once
    When Report page Includes
      | email         | course | SendDate |
      | abc@odd-e.com | 1      | *        |

  Scenario: Send to the same contact again when there is a new course
    Given There is a contact "abc@odd-e.com" at Tokyo
    And Add a course with below details
      |coursename|duration|country|city   |startdate  |address|coursedetails|instructor|
      |    CSD-1 | 30     |Japan  |Tokyo  |2017-10-01 |odd-e  |CSD Training | Terry    |
    And I click send button
    And Add a course with below details
      |coursename|duration|country|city   |startdate  |address|coursedetails|instructor|
      |    CSD-2 | 30     |Japan  |Tokyo  |2017-10-23 |odd-e  |CSD Training | Terry    |
    When I click send button
    Then It should send out emails:
      | location | number of emails | number of events in the email |
      | Combined |                1 |                             2 |
