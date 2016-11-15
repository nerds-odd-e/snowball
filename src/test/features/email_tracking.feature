Feature: Email Tracking
  User should be able to tracking email
  if email address sent are valid


  @email @wip @now
  Scenario: Archive sent email
    Given I send an email with subject "hello" to "terry@odd-e.com"
    When Visit Email Tracking Page
    Then I should see the email with subject "hello" in the list with the sent date
    When I click the email subject "hello"
    Then I should see a list of recipients including "terry@odd-e.com"