Feature: Track Email
  
  Scenario: Email is never opened
    Given I send an email to "terry1@odd-e.com"
    When  "terry1@odd-e.com" does not open the email
    Then  I should see that "terry1@odd-e.com" has not opened the email
    
  Scenario: Email is opened
    Given I send an email to "terry2@odd-e.com"
    When  "terry2@odd-e.com" open the email
    Then  I should see that "terry2@odd-e.com" has opened the email

  Scenario: Email is sent to multiple recipients
    Given I send an email to "terry3@odd-e.com;terry4@odd-e.com"
    When  "terry3@odd-e.com" open the email
    But   "terry4@odd-e.com" does not open the email
    Then  I should see that "terry3@odd-e.com" has opened the email
    And   I should see that "terry4@odd-e.com" has not opened the email

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