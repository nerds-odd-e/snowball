Feature: Track Email
  
  Scenario: Email is never opened
    Given I send an email to Terry
    When  Terry does not open the email
    Then  I should see that Terry has not opened the email