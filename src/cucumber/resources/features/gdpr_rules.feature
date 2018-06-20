@developing
Feature: Obtain consent by email

  Scenario: User accepts the consent
    When Administrator wants to send consent email
    And Constant email is created and sent

    Then User accept consent
    And User is marked consent recived


  Scenario: User do automatic reply
    When Administrator wants to send consent email
    And Constant email is created and sent

    And User does automatic reply
    Then User is not marked consent received

