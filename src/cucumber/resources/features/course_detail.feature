Feature: Course Detail
  Display enrolled participants

  Background:
    Given there is a course named "CSD Tokyo"

  @developing
  Scenario: Display course that has no participant
    When I visit "CSD Tokyo" detail page
    Then No participant is displayed in enrolled participant list

  @developing
  Scenario: Display course that has participants
    Given Two paricipants are enrolled to "CSD Tokyo"
      | user1@odd-e.com | john | smith |
      | user2@odd-e.com | jane | doe   |
    When I visit "CSD Tokyo" detail page
    Then Two participants are displayed in enrolled participant list
      | user1@odd-e.com | john | smith |
      | user2@odd-e.com | jane | doe   |
