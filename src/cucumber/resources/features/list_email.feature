Feature: List email with email subject details
  As a user I want to be able to see my email sent
  so that I can see my email in the sent emails list


  @developing
  Scenario: check navigation to email list page from list email button on home page
    Given Terry sends an email
    When Terry clicks on the email track link
    Then Terry can see the email list page

    @developing
  Scenario: check sent email in the list
    Given Terry send an email with subject "hello"
    When  Terry clicks on the email track link
    Then  Terry should see the email with subject "hello" in the list with date





