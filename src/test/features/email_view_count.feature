Feature: Email view count
  As the admin I want to monitor of email views count

  Scenario: Email is not open by recipient
    Given I send an email to "terry@odd-e.com"
    When the email is never opened
    Then I should see 0 for "terry@odd-e.com" view count



