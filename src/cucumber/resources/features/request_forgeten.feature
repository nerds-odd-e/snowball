@developing
Feature: The right to be forgoten
    Scenario: Delete contact upon a receiving request to be deleted
    Given Contact ivan@odde.com exists
    When Contact ivan@odde.com requests to be forgoten by email
    Then Contact ivan@odde.com is marked as forgoten

Scenario: Admin can not send email to forgotten contact
    Given Contact ivan@odde.com is forgotten
    When Admin tries to send an email to ivan@odde.com
    Then An error is displayed
    And The email is not sent

Scenario: Forgotten contact is displayed as red at the list below
    Given Contact ivan@odde.com is forgotten
    When Enter contact list page
    Then ivan@odde.com is displayed as red at the list below

