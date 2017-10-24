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

  Scenario: send email only one event
    Given There is a contact "abc@odd-e.com" at Tokyo
    And there are two courses at Tokyo
    When I trigger the sending twice
    Then I should get an element with message "abc@odd-e.com is already sent"

  Scenario: add new contact
    Given There is a contact "abc@odd-e.com" at Tokyo
    And there are two courses at Tokyo
    And I trigger the sending once
    And add contact "xyz@odd-e.com" at Tokyo
    When I trigger the sending once
    Then I should get an element with message "Email successfully sent"
    But "abc@odd-e.com" shouldn't receive

  Scenario: Even though there are multiple courses, one user receive only 1 email
    Given: there is a contact "abc@odd-e.com" at Tokyo, Seoul
    And: there are two courses at Tokyo, Souel
    When: I trigger the sending twice
    Then: contact "abc@odd-e.com" should receive only 1 email

  Scenario: Report Page Includes
    Given There is a contact "abc@odd-e.com" at Tokyo
    And there is a course at Tokyo
    And I trigger the sending once
    When Report page Includes
      | email         | course | SendDate |
      | abc@odd-e.com | 1      | *        |


