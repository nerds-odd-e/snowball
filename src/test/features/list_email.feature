Feature: List email with email subject details
  As a user I want to be able to see my email sent
  so that I can see my email in the sent emails list

  @wip
  Scenario: check navigation to email list page from list email button on home page
    Given Terry is logged in with "Terry@gmail.com"
    And Terry sends an email
    When Terry is on the home page
    And Terry clicks on the email track link
    Then Terry can see the email list page

  @wip
  Scenario: see warning message if no email sent
    Given Terry is logged in with "Terry@gmail.com"
    And Terry does not send and email
    When Terry is on the home page
    And Terry clicks on the email track link
    Then Terry can see the email list page
    And Terry sees the message "No email sent"






