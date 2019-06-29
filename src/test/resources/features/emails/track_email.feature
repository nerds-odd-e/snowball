Feature: Track Email

  Scenario: check navigation to email list page from list email button on home page
    Given Terry sends an email
    When Terry clicks on the email track link
    Then Terry can see the email list page

  Scenario: check sent email in the list
    Given Terry send an email with subject "hello"
    When  Terry clicks on the email track link
    Then  Terry should see the email with subject "hello" in the list with date

  @system
  Scenario: Email is never opened
    Given I send an email to "terry1@odd-e.com"
    When  "terry1@odd-e.com" does not open the email
    Then  I should see that "terry1@odd-e.com" has not opened the email

  @system
  Scenario: Email is opened
    Given I send an email to "terry2@odd-e.com"
    When  "terry2@odd-e.com" open the email
    Then  I should see that "terry2@odd-e.com" has opened the email

  @system
  Scenario: Email is sent to multiple recipients
    Given I send an email to "terry3@odd-e.com;terry4@odd-e.com"
    When  "terry3@odd-e.com" open the email
    But   "terry4@odd-e.com" does not open the email
    Then  I should see that "terry3@odd-e.com" has opened the email
    And   I should see that "terry4@odd-e.com" has not opened the email

  @system
  Scenario Outline: Multiple recipients
    Given I send an email to multiple recipients
    When  <opened recipients> opens their email
    And   <did not open recipients> did not open their email
    Then  I must see that <opened recipients> has opened their email

    Examples:
      | opened recipients | did not open recipients |
      | no recipient      | all recipients          |
      | all recipients    | no recipients           |
      | one recipient     | all other recipients    |