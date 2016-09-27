Feature: Send Email
  User should be able to send email
  if email addresses are valid
  and subject and content are not empty.

  Background:
    Given Visit Send Mail Page

  Scenario: Verify sending email to 1 person
    Given Add Email Recipient "alvinweibin@gmail.com"
    And Email Subject is "spam"
    And Email Content is "spam mail for you"
    When I Click Send Email
    Then I should get an element with message "success : Email successfully sent"